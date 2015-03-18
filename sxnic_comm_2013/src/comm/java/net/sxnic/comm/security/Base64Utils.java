package net.sxnic.comm.security;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class Base64Utils {

	public static final String ENCODE_KEY = "40623440";

	public static final String ENCODE_UTF_8 = "UTF-8";

	public static final String ENCODE_GBK = "GBK";

	private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * 采用指定偏移量加密
	 * 
	 * @param name
	 *            要加密的字符串
	 * @param key
	 *            偏移量，必须是8为数字或字母组合
	 * @return
	 * @throws DecodeException
	 */
	public static String encode(String name, String key) throws DecodeException {
		String temp = "";
		try {
			if (StringUtils.isBlank(key)) {
				temp = new String(encode(ENCODE_KEY, name.getBytes(ENCODE_GBK)).toString());
			} else {
				temp = new String(encode(key, name.getBytes(ENCODE_GBK)).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DecodeException();
		}

		return temp;
	}

	/**
	 * 采用指定偏移量解密
	 * 
	 * @param name
	 *            要解密的字符串
	 * @param key
	 *            偏移量，必须是8为数字或字母组合
	 * @return
	 * @throws DecodeException
	 */
	public static String dccode(String name, String key) throws DecodeException {
		String temp = "";
		try {
			if (StringUtils.isBlank(key)) {
				temp = new String(decode(ENCODE_KEY, Base64.decodeBase64(name.getBytes())), ENCODE_GBK);
			} else {
				temp = new String(decode(key, Base64.decodeBase64(name.getBytes())), ENCODE_GBK);
			}
		} catch (Exception e) {
			throw new DecodeException();
		}

		return temp;
	}

	/**
	 * 采用默认偏移量加密
	 * 
	 * @param name
	 * @return
	 * @throws DecodeException
	 */
	public static String encode(String name) throws DecodeException {
		String temp = "";
		try {
			temp = new String(encode(ENCODE_KEY, name.getBytes(ENCODE_GBK)).toString());
		} catch (Exception e) {
			throw new DecodeException();
		}

		return temp;
	}

	/**
	 * 采用默认偏移量解密
	 * 
	 * @param name
	 * @return
	 * @throws DecodeException
	 */
	public static String dccode(String name) throws DecodeException {
		String temp = "";
		try {
			temp = new String(decode(ENCODE_KEY, Base64.decodeBase64(name.getBytes())), ENCODE_GBK);
		} catch (Exception e) {
			throw new DecodeException();
		}

		return temp;
	}

	private static byte[] decode(String key, byte[] data) throws DecodeException {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key 的长度不能够小于 8 位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new DecodeException();
		}
	}

	private static String encode(String key, byte[] data) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());// 向量
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
		byte[] bytes = cipher.doFinal(data);
		return Base64.encodeBase64String(bytes);
	}

}
