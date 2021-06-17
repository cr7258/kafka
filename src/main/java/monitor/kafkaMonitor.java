package monitor;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * @author 程治玮
 * @since 2021/2/24 11:18 上午
 * JMX获取Kafka指标信息
 */
public class kafkaMonitor {

    //获取的Kafka指标数据
    private static final String MESSAGE_IN_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec";
    private static final String BYTES_IN_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec";
    private static final String BYTES_OUT_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=BytesOutPerSec";
    private static final String BYTES_REJECTED_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=BytesRejectedPerSec";
    private static final String FAILED_FETCH_REQUESTS_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=FailedFetchRequestsPerSec";
    private static final String FAILED_PRODUCE_REQUESTS_PER_SEC = "kafka.server:type=BrokerTopicMetrics,name=FailedProduceRequestsPerSec";
    private static final String PRODUCE_REQUEST_PER_SEC = "kafka.network:type=RequestMetrics,name=RequestsPerSec,request=Produce,version=8";

    //JXM连接信息
//    private static final String JXM_URL = "service:jmx:rmi:///jndi/rmi://192.168.1.87:9999/jmxrmi";
    private static final String JXM_URL = "service:jmx:rmi:///jndi/rmi://11.8.36.125:9999/jmxrmi";

    public void extractMonitorData() {
        try {
            MBeanServerConnection jmxConnection = this.getMBeanServerConnection(JXM_URL);

            ObjectName messageCountObj = new ObjectName(MESSAGE_IN_PER_SEC);
            ObjectName bytesInPerSecObj = new ObjectName(BYTES_IN_PER_SEC);
            ObjectName bytesOutPerSecObj = new ObjectName(BYTES_OUT_PER_SEC);
            ObjectName bytesRejectedPerSecObj = new ObjectName(BYTES_REJECTED_PER_SEC);
            ObjectName failedFetchRequestsPerSecObj = new ObjectName(FAILED_FETCH_REQUESTS_PER_SEC);
            ObjectName failedProduceRequestsPerSecObj = new ObjectName(FAILED_PRODUCE_REQUESTS_PER_SEC);
            ObjectName produceRequestPerSecObj = new ObjectName(PRODUCE_REQUEST_PER_SEC);

            printObjectNameDetails(messageCountObj, "Messages in /sec", jmxConnection);
            printObjectNameDetails(bytesInPerSecObj, "Bytes in /sec", jmxConnection);
            printObjectNameDetails(bytesOutPerSecObj, "Bytes out /sec", jmxConnection);
            printObjectNameDetails(bytesRejectedPerSecObj, "Bytes rejected /sec", jmxConnection);
            printObjectNameDetails(failedFetchRequestsPerSecObj, "Failed fetch request /sec", jmxConnection);
            printObjectNameDetails(failedProduceRequestsPerSecObj, "Failed produce request /sec", jmxConnection);
            printObjectNameDetails(produceRequestPerSecObj, "Produce request in /sec", jmxConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new kafkaMonitor().extractMonitorData();
    }

    /**
     * 获得 MBeanServer 的连接
     *
     * @param jmxUrl
     * @return
     * @throws IOException
     */
    private MBeanServerConnection getMBeanServerConnection(String jmxUrl) throws IOException {
        JMXServiceURL url = new JMXServiceURL(jmxUrl);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        return mbsc;
    }

    /**
     * 打印 ObjectName 对象详细信息
     * @param objectName
     * @param printTitle
     * @param jmxConnection
     */
    private void printObjectNameDetails(ObjectName objectName, String printTitle, MBeanServerConnection jmxConnection) {
        try {
            System.out.println("----------"+ printTitle +"----------");
            System.out.println("TotalCount: " + (Long) jmxConnection.getAttribute(objectName, "Count"));
            System.out.println("MeanRate: " + String.format("%.2f", (double) jmxConnection.getAttribute(objectName, "MeanRate")));
            System.out.println("OneMinuteRate: " + String.format("%.2f", (double) jmxConnection.getAttribute(objectName, "OneMinuteRate")));
            System.out.println("FiveMinuteRate: " + String.format("%.2f", (double) jmxConnection.getAttribute(objectName, "FiveMinuteRate")));
            System.out.println("FifteenMinuteRate: " + String.format("%.2f", (double) jmxConnection.getAttribute(objectName, "FifteenMinuteRate")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}