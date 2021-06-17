package book_4.serialization;

import java.io.Serializable;

/**
 * 声明一个序列化类.
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class JSalarySerial implements Serializable {

	/**
	 * 序列化版本ID.
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 用户ID
	private String salary;// 金额

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	// 打印对象属性值
	@Override
	public String toString() {
		return "JSalarySerial [id=" + id + ", salary=" + salary + "]";
	}

}
