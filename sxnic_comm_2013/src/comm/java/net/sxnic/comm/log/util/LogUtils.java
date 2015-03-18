package net.sxnic.comm.log.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import net.sxnic.comm.log.Log;
import net.sxnic.comm.utils.JsonUtils;

import com.google.gson.Gson;

public class LogUtils {
	
	/**
	 * 主要是为了在WebService传输中使用json格式
	 * @param website
	 * @param className
	 * @param exception
	 * @return
	 */
	public static String LogToExceptionJson(String operator,String website,String className,String exception){
		StringBuffer sb = new StringBuffer();
		sb.append("{\"website\":\""+website+"\",");
		sb.append("\"operator\":\""+operator+"\",");
		sb.append("\"className\":\""+className+"\",");
		sb.append("\"exception\":\""+exception+"\"}");
		
		return sb.toString();
	}
	
	public static Log ExceptionJsonToLog(String json){
		Gson gson = new Gson();
		if(JsonUtils.isBadJson(json)){
			return null;
		}
		Log log = new Log();

		Map<String,String> map = gson.fromJson(json, Map.class);
		
		log.setWebsite(map.get("website"));
		log.setOperator(map.get("operator"));
		log.setClassName(map.get("className"));
		log.setCate(Log.LOG_IMPORTANT_CATE_003);
		log.setDetails(map.get("exception"));
		
		return log;
	}
	
	/**
	 * 根据给定的websiteMap，把WebService接口中的参数转化为log实体
	 * @param json
	 * @param websitMap
	 * @return
	 */
	public static Log ExceptionJsonToLog(String json,Map<String,String> websitMap){
		Gson gson = new Gson();
		if(JsonUtils.isBadJson(json)){
			return null;
		}
		Log log = new Log();

		Map<String,String> map = gson.fromJson(json, Map.class);
		
		if(websitMap!=null && websitMap.containsKey(map.get("website"))){
			log.setWebsite(websitMap.get(map.get("website")));
		}else{
			log.setWebsite(map.get("website"));
		}
		log.setOperator(map.get("operator"));
		log.setClassName(map.get("className"));
		log.setCate(Log.LOG_IMPORTANT_CATE_003);
		log.setDetails(map.get("exception"));
		
		return log;
	}

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
