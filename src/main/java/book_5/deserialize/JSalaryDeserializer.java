package book_5.deserialize;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * 实现自定义反序列化.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class JSalaryDeserializer implements Deserializer<Object> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	/** 自定义反序列逻辑. */
	@Override
	public Object deserialize(String topic, byte[] data) {
		return DeserializeUtils.deserialize(data);
	}

	@Override
	public void close() {

	}

}
