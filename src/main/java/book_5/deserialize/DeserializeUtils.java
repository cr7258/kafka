package book_5.deserialize;

/**
 * 封装一个序列化的工具类.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class DeserializeUtils {

	/** 实现反序列化. */
	public static <T> Object deserialize(byte[] bytes) {
		try {
			return new String(bytes, "UTF8");// 反序列化
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
