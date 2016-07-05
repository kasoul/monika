package com.superh.hz.monika.realtime.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.HasOffsetRanges;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.apache.spark.streaming.kafka.OffsetRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.superh.hz.monika.realtime.KafkaOffsetTool;
import com.superh.hz.monika.realtime.PropertiesHelper;
import com.superh.hz.monika.realtime.constant.MonikaMonitorConstant;
import com.superh.hz.monika.realtime.result.MonitorResult;
import com.superh.hz.monika.realtime.task.MonitorTask;
import com.superh.hz.monika.realtime.task.MonitorTaskBuilder;

import kafka.common.TopicAndPartition;
import kafka.message.MessageAndMetadata;
import kafka.serializer.DefaultDecoder;
import kafka.serializer.StringDecoder;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * @Describe:车辆布控作业
 */
public class MonitorDirectJob {

	
	private static final Logger logger = LoggerFactory.getLogger(MonitorDirectJob.class);
	//private static DateFormat df_yyyymmdd = new SimpleDateFormat("yyyymmdd");
	private static PropertiesHelper propertiesHelper = null;
	private static SparkConf conf = null;
	private static JavaStreamingContext jssc = null;
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure(MonikaMonitorConstant.LOG4J_PROPERTIES__PATH);
		
		buildStream();
	
	}

	public static void buildStream() {
		propertiesHelper = new PropertiesHelper(MonikaMonitorConstant.PROPERTIES_PATH,
				MonikaMonitorConstant.CHAR_SET_DEFUALT);
		
		conf = new SparkConf().setAppName("Real Time Monitor (Direct) Job");
		
		conf.setMaster("local[*]");
	
		/*conf.set("spark.streaming.blockInterval", 
				propertiesHelper.getProperty(VehicleMonitorConstant.JOB_SPARK_STREAMING_BLOCKINTERVAL,"1000"));*/
		conf.set("spark.streaming.kafka.maxRatePerPartition",
				propertiesHelper.getProperty(MonikaMonitorConstant.JOB_SPARK_STREAMING_MAXRATEPERPARTITION,"100"));
		
		int batchInterval = propertiesHelper.getInt(MonikaMonitorConstant.JOB_SPARK_STREAMING_BATCHINTERVAL,5000);
		jssc = new JavaStreamingContext(conf, Durations.milliseconds(batchInterval));
		Map<String, String> kafkaParams = new HashMap<String, String>();
		
		kafkaParams.put("metadata.broker.list", 
				propertiesHelper.getProperty(MonikaMonitorConstant.KAFKA_BROKERS_LIST));

		boolean redisOffsetReset = propertiesHelper.getBoolean(MonikaMonitorConstant.KAFKA_REDIS_OFFSET_RESET, false);
		
		//kafka默认偏移量是auto.offset.reset确定，客户端控制偏移量
		
		Map<TopicAndPartition,Long> lastOffset = null;
		if(redisOffsetReset){
			lastOffset = KafkaOffsetTool.getLastestOffset(kafkaParams, propertiesHelper.getProperty(MonikaMonitorConstant.KAFKA_CONSUMER_TOPIC));
		}else{
			lastOffset = getRedisKafkaOffset();
			if(lastOffset.size()==0){
				lastOffset = KafkaOffsetTool.getLastestOffset(kafkaParams, propertiesHelper.getProperty(MonikaMonitorConstant.KAFKA_CONSUMER_TOPIC));
			}
		}
		
		Function<MessageAndMetadata<String, byte[]>,byte[]> messageHandler = new Function<MessageAndMetadata<String, byte[]>,byte[]>(){
			@Override
			public byte[] call(MessageAndMetadata<String, byte[]> arg0) throws Exception {
				return arg0.message();
			}
			
		};
		
		JavaInputDStream<byte[]> kafkaStream = 
			     KafkaUtils.<String,byte[],StringDecoder,DefaultDecoder,byte[]>createDirectStream(
			    		 jssc,
			    		 String.class,
			    		 byte[].class,
			    		 StringDecoder.class,
			    		 DefaultDecoder.class,
			    		 byte[].class,
			    		 kafkaParams,
			    		 lastOffset,
			    		 messageHandler);
		
		AtomicReference<OffsetRange[]> offsetRanges = new AtomicReference<>();

		kafkaStream.transform(new Function<JavaRDD<byte[]>, JavaRDD<byte[]>>() {
			@Override
			public JavaRDD<byte[]> call(JavaRDD<byte[]> rdd) throws Exception {
				OffsetRange[] offsets = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
				offsetRanges.set(offsets);
				return rdd;
			}
		}
		).foreachRDD(new VoidFunction<JavaRDD<byte[]>>(){

			@Override
			public void call(JavaRDD<byte[]> rdd) throws Exception {
				
				Jedis jedis = new Jedis(propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_NODE_HOSTNAME), 
						propertiesHelper.getInt(MonikaMonitorConstant.REDIS_NODE_PORT,6379));
				//create redis client,get taskInfoMap
				//put it into broadcast
				Map<String,String> updateOffset = new HashMap<String,String>();
				logger.info("-----------------------------------------");
				logger.info("Time: [{}]ms",System.currentTimeMillis());
				for (OffsetRange o : offsetRanges.get()) {
					logger.info("topic:[{}], partition:[{}], fromOffset:[{}], toOffset:[{}]",
							o.topic(), o.partition(), o.fromOffset(), o.untilOffset()
							);
					updateOffset.put(o.topic() + ":" + o.partition(),String.valueOf(o.untilOffset()));
			    }
				
				jedis.hmset(propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_KAFKA_OFFSET_KEY), updateOffset);
				logger.info("update kafka offset completed,topic:meta.");
				logger.info("-----------------------------------------");
				
				Map<String,String> taskInfoMap = jedis.hgetAll(propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_MONITOR_TASK_KEY));
				List<MonitorTask> taskList = MonitorTaskBuilder.buildTaskList(taskInfoMap);
				logger.info("----------------当前布控任务总数量：[{}]" , taskList.size());
				
				
				Broadcast<List<MonitorTask>> bc_taskList = jssc.sparkContext().broadcast(taskList);
				Broadcast<String> bc_redisResultKey = jssc.sparkContext().broadcast(
						propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_MONITOR_RESULT_KEY));
				
				
				Broadcast<String> bc_redisServerHost = jssc.sparkContext().broadcast(
						propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_NODE_HOSTNAME));
				Broadcast<Integer> bc_redisServerPort = jssc.sparkContext().broadcast(
						propertiesHelper.getInt(MonikaMonitorConstant.REDIS_NODE_PORT,6379));
				
				Broadcast<String> bc_hbaseRootdir = jssc.sparkContext().broadcast(
						propertiesHelper.getProperty(MonikaMonitorConstant.HBASE_ROOTDIR));
				Broadcast<String> bc_hbaseZKQuorum = jssc.sparkContext().broadcast(
						propertiesHelper.getProperty(MonikaMonitorConstant.HBASE_ZOOKEEPER_QUORUM));
				
				
				//Broadcast<Configuration> bc_hadoopConf = jssc.sparkContext().broadcast(conf);
						
				Accumulator<Integer> acc_batchCount = jssc.sparkContext().accumulator(0);
				Accumulator<Integer> acc_monitorResultCount = jssc.sparkContext().accumulator(0);

				rdd.foreachPartition(new VoidFunction<Iterator<byte[]>>() {
					
					@Override
					public void call(Iterator<byte[]> iterator) throws Exception {
						//create redis client,get taskInfoMap
						
						List<MonitorTask> taskList = bc_taskList.getValue();
						
						Jedis jedis = new Jedis(bc_redisServerHost.getValue(), bc_redisServerPort.getValue());
						
						while(iterator.hasNext()){
							acc_batchCount.add(1);
							byte[] tupleValue = iterator.next();
							JSONObject jsonTuple = JSONObject.fromObject(new String(tupleValue));

							for(MonitorTask task:taskList){
								if(task.executeMonitor(jsonTuple)){
									JSONObject resultJson = JSONObject.fromObject(new MonitorResult(task.getTaskType(),task.getTaskId(),jsonTuple));
									logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++");
									logger.debug("任务类型：" + task.getTaskType() + ";" + "任务id："
											+ task.getTaskId());
									logger.debug("监控结果：" + resultJson);
									logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++");
									acc_monitorResultCount.add(1);
										
									jedis.lpush(bc_redisResultKey.getValue(), resultJson.toString());
								}
							}
						}
						
						jedis.close();
						
					}
					
				});
				
				long dealTime = System.currentTimeMillis();
				logger.info("----------------deal time=>[{}],batch count：[{}]", dealTime,acc_batchCount.value());
				logger.info("----------------deal time=>[{}],result count：[{}]",dealTime, acc_monitorResultCount.value());
				
			}
		});
		
		jssc.start(); // Start the computation
		jssc.awaitTermination(); // Wait for the computation to terminate
	}
	
	private static Map<TopicAndPartition,Long> getRedisKafkaOffset(){
		Map<TopicAndPartition,Long> lastOffset = new HashMap<TopicAndPartition,Long>();
		Jedis jedis = new Jedis(propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_NODE_HOSTNAME), 
				propertiesHelper.getInt(MonikaMonitorConstant.REDIS_NODE_PORT,6379));
		Map<String,String> map = jedis.hgetAll((propertiesHelper.getProperty(MonikaMonitorConstant.REDIS_KAFKA_OFFSET_KEY)));
		
		
		for(String key:map.keySet()){
			TopicAndPartition tp = new TopicAndPartition(key.split(":")[0],
					Integer.parseInt(key.split(":")[1]));
			lastOffset.put(tp, Long.parseLong(map.get(key)));
		}
		
		jedis.close();
		
		return lastOffset;
	}

}
