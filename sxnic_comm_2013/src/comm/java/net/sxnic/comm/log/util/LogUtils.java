package net.sxnic.comm.log.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LogUtils {

	/**
	 * 把java 对象转化为二进制流
	 * 
	 * @param obj
	 *            对象
	 * @return 二进制数据流
	 * @throws IOException
	 */
	public static byte[] treatObjecttoBytes(Object obj) throws IOException {

		if (obj == null)
			return null;

		byte[] bytes = new byte[1024];

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(obj);

		bytes = bo.toByteArray();

		bo.close();
		oo.close();

		return bytes;
	}

	/**
	 * 把二进制流转化为Object
	 * 
	 * @param bts
	 * @return 实体类
	 * @throws Exception
	 */
	public static Object treatBytestoObject(byte[] bts) throws Exception {

		if (bts == null)
			return null;

		java.lang.Object obj = new java.lang.Object();

		ByteArrayInputStream bi = new ByteArrayInputStream(bts);
		ObjectInputStream oi = new ObjectInputStream(bi);

		obj = oi.readObject();

		bi.close();
		oi.close();

		return obj;

	}

}
