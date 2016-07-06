package com.superh.hz.monika.realtime.constant;

public interface MonikaMonitorConstant {
	
	public static final String PROPERTIES_PATH = "./conf/real-time-monitor-job.properties";
	public static final String LOG4J_PROPERTIES__PATH = "./conf/log4j.properties";
	public static final String CLASSPATH_PROPERTIES_FILE = "real-time-monitor-job.properties";
	public static final String CHAR_SET_DEFUALT = "UTF-8";
	
	public static final String KAFKA_ZOOKEEPER_CONNECT = "kafka.zookeeper.connect";
	public static final String KAFKA_GROUP_ID = "kafka.group.id";
	public static final String KAFKA_BROKERS_LIST = "kafka.brokers.list";
	public static final String KAFKA_AUTO_OFFSET_RESET = "kafka.auto.offset.reset";
	public static final String KAFKA_CONSUMER_TOPIC = "kafka.consumer.topic";
	public static final String KAFKA_CONSUMER_NUM = "kafka.consumer.num";
	public static final String KAFKA_REDIS_OFFSET_RESET = "kafka.redis.offset.reset";
	
	public static final String REDIS_NODE_HOSTNAME = "redis.node.hostname";
	public static final String REDIS_NODE_PORT = "redis.node.port";
	public static final String REDIS_MONITOR_TASK_KEY = "redis.monitor.task.key";
	public static final String REDIS_MONITOR_RESULT_KEY = "redis.monitor.result.key";
	public static final String REDIS_KAFKA_OFFSET_KEY = "redis.kafka.offset.key";
	
	public static final String HBASE_ROOTDIR = "hbase.rootdir";
	public static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	
	public static final String JOB_SPARK_STREAMING_BLOCKINTERVAL = "spark.streaming.blockInterval";
	public static final String JOB_SPARK_STREAMING_BATCHINTERVAL = "spark.streaming.batchInterval";
	public static final String JOB_SPARK_STREAMING_RECEIVER_MAXRATE = "spark.streaming.receiver.maxRate";
	public static final String JOB_SPARK_STREAMING_MAXRATEPERPARTITION = "spark.streaming.kafka.maxRatePerPartition";
	public static final String JOB_RUN_PARAM_GROUPBYKEY_NUM = "job.run.param.groupbykey.num";
	
	public static final String MONIKA_MONITOR_TASK_FLAG = "monika.monitor.task.flag";
	public static final String MONIKA_MONITOR_TASK_CLASS = "monika.monitor.task.class";
	public static final String MONIKA_MONITOR_TASK_PARSER_CLASS = "monika.monitor.task.parser.class";
	
	
}
