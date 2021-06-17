package book_4.partioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 实现一个自定义分区类.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class JPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {

	}

	/** 实现Kafka主题分区索引算法. */
	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		int partition = 0;
		String k = (String) key;
		partition = Math.abs(k.hashCode()) % cluster.partitionCountForTopic(topic);
		return partition;
	}

	@Override
	public void close() {

	}

}
