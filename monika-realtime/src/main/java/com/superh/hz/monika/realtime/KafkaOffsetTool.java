package com.superh.hz.monika.realtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.TreeMap;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;

/**
 * @Describe:kafka偏移量读取工具
 */
public class KafkaOffsetTool {
	public static void main(String[] args) {
		Map<String, String> kafkaParams = new HashMap<String, String>();
		String topic = "meta";
		kafkaParams.put("metadata.broker.list","node1:9092,node2:9092,node3:9092,node4:9092");

		//Map<TopicAndPartition, Long> map = getLastestOffset(kafkaParams,topic);
		Map<TopicAndPartition, Long> map = getSmallestOffset(kafkaParams,topic);
		for(TopicAndPartition tp: map.keySet()){
			System.out.println(tp.topic() + "-" + tp.partition() + ":" + map.get(tp));
		}
		
	}
	
	private static Map<TopicAndPartition, Long> getOffsetBeforeTimestamp(Map<String, String> kafkaParams, String topic, long whichTime) {

		Map<TopicAndPartition, Long> mapTpls = new HashMap<TopicAndPartition, Long>();
		String brokerList = kafkaParams.get("metadata.broker.list");
		List<String> brokers = new ArrayList<String>();
		int port = 9092;

		for (String s : brokerList.split(",")) {
			brokers.add(s.split(":")[0]);
			port = Integer.parseInt(s.split(":")[1]);
		}

		TreeMap<Integer, PartitionMetadata> metadatas = findLeaderBrokerList(brokers, port, topic);

		for (Entry<Integer, PartitionMetadata> entry : metadatas.entrySet()) {
			int partition = entry.getKey();
			String leadBroker = entry.getValue().leader().host();
			String clientName = "Client_" + topic + "_" + partition;
			SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
			long readOffset = getLastestOffset(consumer, topic, partition, whichTime,
					clientName);

			TopicAndPartition tp = new TopicAndPartition(topic, partition);
			
			mapTpls.put(tp, readOffset);

			if (consumer != null)
				consumer.close();
		}

		return mapTpls;
	}

	public static Map<TopicAndPartition, Long> getLastestOffset(Map<String, String> kafkaParams, String topic) {

		return getOffsetBeforeTimestamp(kafkaParams,topic,kafka.api.OffsetRequest.LatestTime());
	
	}
	
	public static Map<TopicAndPartition, Long> getSmallestOffset(Map<String, String> kafkaParams, String topic) {

		return getOffsetBeforeTimestamp(kafkaParams,topic,kafka.api.OffsetRequest.EarliestTime());
	
	}

	// private List<String> m_replicaBrokers = new ArrayList<String>();
	private static long getLastestOffset(SimpleConsumer consumer, String topic, int partition, long whichTime,
			String clientName) {

		TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
		requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
		OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
		OffsetResponse response = consumer.getOffsetsBefore(request);

		if (response.hasError()) {
			System.out.println(
					"Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
			return 0;
		}
		long[] offsets = response.offsets(topic, partition);
		// long[] offsets2 = response.offsets(topic, 3);
		return offsets[0];
	}

	/**
	* 只需要一个broker，就可以发现所有的节点，
	* 通过TopicMetadataRequest发现所有的TopicMetadata，进而发现所有的PartitionMetadata
	*/
	private static TreeMap<Integer, PartitionMetadata> findLeaderBrokerList(List<String> a_seedBrokers, int a_port,
			String a_topic) {

		TreeMap<Integer, PartitionMetadata> map = new TreeMap<Integer, PartitionMetadata>();
		for (String seed : a_seedBrokers) {
			SimpleConsumer consumer = null;
			try {
				consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup" + new Date().getTime());
				List<String> topics = Collections.singletonList(a_topic);
				TopicMetadataRequest req = new TopicMetadataRequest(topics);
				kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);

				List<TopicMetadata> metaData = resp.topicsMetadata();
				for (TopicMetadata item : metaData) {
					for (PartitionMetadata part : item.partitionsMetadata()) {
						map.put(part.partitionId(), part);

						// if (part.partitionId() == a_partition) {
						// returnMetaData = part;
						// break loop;
						// }

					}
				}
			} catch (Exception e) {
				System.out.println("Error communicating with Broker [" + seed + "] to find Leader for [" + a_topic
						+ ", ] Reason: " + e);
			} finally {
				if (consumer != null)
					consumer.close();
			}
		}

		// if (returnMetaData != null) {
		// m_replicaBrokers.clear();
		// for (kafka.cluster.Broker replica : returnMetaData.replicas()) {
		// m_replicaBrokers.add(replica.host());
		// }
		// }
		return map;
	}

	public static void test() {
		// 读取kafka最新数据
		// Properties props = new Properties();
		// props.put("zookeeper.connect",
		// "192.168.6.18:2181,192.168.6.20:2181,192.168.6.44:2181,192.168.6.237:2181,192.168.6.238:2181/kafka-zk");
		// props.put("zk.connectiontimeout.ms", "1000000");
		// props.put("group.id", "dirk_group");
		//
		// ConsumerConfig consumerConfig = new ConsumerConfig(props);
		// ConsumerConnector connector =
		// Consumer.createJavaConsumerConnector(consumerConfig);

		String topic = "meta";
		String seed = "node1";
		int port = 9092;
		List<String> seeds = new ArrayList<String>();
		seeds.add(seed);
		KafkaOffsetTool kot = new KafkaOffsetTool();

		TreeMap<Integer, PartitionMetadata> metadatas = kot.findLeaderBrokerList(seeds, port, topic);

		int sum = 0;

		for (Entry<Integer, PartitionMetadata> entry : metadatas.entrySet()) {
			int partition = entry.getKey();
			String leadBroker = entry.getValue().leader().host();
			String clientName = "Client_" + topic + "_" + partition;
			SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
			long readOffset = getLastestOffset(consumer, topic, partition, kafka.api.OffsetRequest.LatestTime(),
					clientName);
			sum += readOffset;
			System.out.println(partition + ":" + readOffset);
			if (consumer != null)
				consumer.close();
		}
		System.out.println("总和：" + sum);
	}

}
