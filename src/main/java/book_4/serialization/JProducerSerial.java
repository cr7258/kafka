package book_4.serialization;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 自定义序列化, 发送消息给Kafka.
 */
public class JProducerSerial extends Thread {

	private static Logger LOG = LoggerFactory.getLogger(JProducerSerial.class);

	/** 配置Kafka连接信息. */
	public Properties configure() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "kafka1:9092,kafka2:9092,kafka3:9092");// 指定Kafka集群地址
		props.put("acks", "1"); // 设置应答模式, 1表示有一个Kafka代理节点返回结果
		props.put("retries", 0); // 重试次数
		props.put("batch.size", 16384); // 批量提交大小
		props.put("linger.ms", 1); // 延时提交
		props.put("buffer.memory", 33554432); // 缓冲大小
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // 序列化主键
		props.put("value.serializer", "book_4.serialization.JSalarySeralizer");// 自定义序列化值

		return props;
	}

	public static void main(String[] args) {
		JProducerSerial producer = new JProducerSerial();
		producer.start();
	}

	/** 实现一个单线程生产者客户端. */
	public void run() {
		Producer<String, JSalarySerial> producer = new KafkaProducer<>(configure());
		JSalarySerial jss = new JSalarySerial();
		jss.setId("2021");
		jss.setSalary("100");

		producer.send(new ProducerRecord<String, JSalarySerial>("test_topic_ser_des", "key", jss), new Callback() {
			public void onCompletion(RecordMetadata metadata, Exception e) {
				if (e != null) {
					LOG.error("Send error, msg is " + e.getMessage());
				} else {
					LOG.info("The offset of the record we just sent is: " + metadata.offset());
				}
			}
		});

		try {
			sleep(3000);// 间隔3秒
		} catch (InterruptedException e) {
			LOG.error("Interrupted thread error, msg is " + e.getMessage());
		}

		producer.close();// 关闭生产者对象
	}
}
