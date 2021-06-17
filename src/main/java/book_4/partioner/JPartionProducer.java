package book_4.partioner;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;

/**
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 * 生产者客户端使用自定义分区策略
 */
public class JPartionProducer extends Thread {

    private final Logger LOG = LoggerFactory.getLogger(JPartionProducer.class);

    /**
     * 配置Kafka连接信息.
     */
    public Properties configure() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka1:9092,kafka2:9092,kafka3:9092");// 指定Kafka集群地址
        props.put("acks", "1"); // 设置应答模式, 1表示有一个Kafka代理节点返回结果
        props.put("retries", 0); // 重试次数
        props.put("batch.size", 16384); // 批量提交大小
        props.put("linger.ms", 1); // 延时提交
        props.put("buffer.memory", 33554432); // 缓冲大小
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // 序列化主键
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");// 序列化值

        props.put("partitioner.class", "book_4.partioner.JPartitioner"); //指定自定义分区类
        return props;
    }

    public static void main(String[] args) {
        JPartionProducer producer = new JPartionProducer();
        producer.start();
    }

    /**
     * 实现一个单线程生产者客户端.
     */
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(configure());
        // 发送100条JSON格式的数据
        for (int i = 0; i < 100; i++) {
            // 封装JSON格式
            JSONObject json = new JSONObject();
            json.put("id", i);
            json.put("count", i);
            json.put("date", new Date().toString());
            String k = "key" + i;
            // 异步发送
            producer.send(new ProducerRecord<String, String>("test_partition", k, json.toJSONString()), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        LOG.error("Send error, msg is " + e.getMessage());
                    } else {
                        LOG.info("The offset of the record we just sent is: " + metadata.offset() + "the partion is: " + metadata.partition());
                    }
                }
            });
        }
        try {
            sleep(3000);// 间隔3秒
        } catch (InterruptedException e) {
            LOG.error("Interrupted thread error, msg is " + e.getMessage());
        }

        producer.close();// 关闭生产者对象
    }

}
