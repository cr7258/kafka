package book_4.serialization;

/**
 * 封装一个序列化的工具类.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class SerializeUtils {

	/** 实现序列化. */
	public static byte[] serialize(Object object) {
		try {
			return object.toString().getBytes("UTF8");// 返回字节数组
		} catch (Exception e) {
			e.printStackTrace(); // 抛出异常信息
		}
		return null;
	}
}
