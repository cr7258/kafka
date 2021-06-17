package book_4.serialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 实现一个序列化的类，将对象序列号写入文件
 * @author 程治玮
 * @since 2021/3/7 3:38 下午
 */
public class JObjectSerializer implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(JObjectSerializer.class);

	/**
	 * 序列化版本ID.
	 */
	private static final long serialVersionUID = 1L;

	public byte id = 1; // 用户ID
	public byte money = 100; // 充值金额

	/** 实例化入口函数. */
	public static void main(String[] args) {
		try {
			FileOutputStream fos = new FileOutputStream("/tmp/salary.out"); // 实例化一个输出流对象
			ObjectOutputStream oos = new ObjectOutputStream(fos);// 实例化一个对象输出流
			JObjectSerializer jos = new JObjectSerializer(); // 实例化序列化类
			oos.writeObject(jos); // 写入对象
			oos.flush(); // 刷新数据流
			oos.close();// 关闭连接
		} catch (Exception e) {
			LOG.error("Serial has error, msg is " + e.getMessage());// 打印异常信息
		}
	}

}
/*
vim /tmp/salary.out文件， 可以看到 ^@^Esr^@^Tbook_4.JObjectSerial^@^@^@^@^@^@^@^A^B^@^BB^@^BidB^@^Emoneyxp^Ad
输入:%!xxd查看十六进制内容
00000000: aced 0005 7372 0014 626f 6f6b 5f34 2e4a  ....sr..book_4.J
00000010: 4f62 6a65 6374 5365 7269 616c 0000 0000  ObjectSerial....
00000020: 0000 0001 0200 0242 0002 6964 4200 056d  .......B..idB..m
00000030: 6f6e 6579 7870 0164 0a                   oneyxp.d.
*/