package book_4.serialization;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * 自定义序列化实现.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class JSalarySeralizer implements Serializer<JSalarySerial> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	/** 实现自定义序列化. */
	@Override
	public byte[] serialize(String topic, JSalarySerial data) {
		return SerializeUtils.serialize(data);
	}

	@Override
	public void close() {

	}

}
