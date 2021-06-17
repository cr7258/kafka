package book_4.producer;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 * 多线程生产者客户端应用程序.
 */
public class JProducerThread extends Thread {

    // 创建一个日志对象
    private final Logger LOG = LoggerFactory.getLogger(JProducerThread.class);
    // 声明最大线程数
    private final static int MAX_THREAD_SIZE = 6;

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
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");// 序列化值
        props.put("partitioner.class", "org.smartloli.kafka.game.x.book_4.JPartitioner");// 指定自定义分区类

        return props;
    }

    public static void main(String[] args) {
        // 创建一个固定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_SIZE);
        // 提交任务
        executorService.submit(new JProducerThread());
        // 关闭线程池
        executorService.shutdown();
    }

    /** 实现一个多线程生产者客户端. */
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(configure());
        // 发送100条JSON格式的数据
        for (int i = 0; i < 10; i++) {
            // 封装JSON格式
            JSONObject json = new JSONObject();
            json.put("id", i);
            json.put("count", i);
            json.put("date", new Date().toString());
            String k = "key" + i;
            // 异步发送
            producer.send(new ProducerRecord<String, String>("ip_login_rt", k, json.toJSONString()), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        LOG.error("Send error, msg is " + e.getMessage());
                    } else {
                        LOG.info("The offset of the record we just sent is: " + metadata.offset());
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
