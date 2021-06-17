package book_5.deserialize;

import book_4.serialization.JObjectSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * 反序列化一个类，将字节序列数据恢复成对象.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class JObjectDeserializer {

	/** 创建一个日志对象实例. */
	private static Logger LOG = LoggerFactory.getLogger(JObjectSerializer.class);

	/** 实例化入口函数. */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("/tmp/salary.out"); // 实例化一个输入流对象
			JObjectSerializer jos = (JObjectSerializer) new ObjectInputStream(fis).readObject();// 反序列化还原对象

			// 打印反序列化还原后的对象属性
			//18:42:01.581 [main] INFO  book_4.serialization.JObjectSerial - ID : 1 , Money : 100
			LOG.info("ID : " + jos.id + " , Money : " + jos.money);
		} catch (Exception e) {
			LOG.error("Deserial has error, msg is " + e.getMessage());// 打印异常信息
		}
	}

}
