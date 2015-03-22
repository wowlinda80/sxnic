package net.sxnic.comm.utils;

import java.util.Map;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.security.Base64Utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @Description: JsonUtils.java 描述
 * @Author Lixd-remixty@163.com
 * @Version 1.0
 * @CreateDate:2014年12月3日
 */
public class JsonUtils {

	protected final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static boolean isBadJson(String json) {
		return !isGoodJson(json);
	}

	public static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			logger.error("bad json: " + json);
			return false;
		}
	}

	/**
	 * 简单创建一个成功的Json信息(200:成功)
	 * 
	 * @return
	 */
	public static String crtSuccessJson() {
		return "{\"status\":\"200\",\"message\":\"成功\"}";
	}

	/**
	 * 简单创建一个失败的Json信息(500:失败)
	 * 
	 * @return
	 */
	public static String crtErrorJson() {
		return "{\"status\":\"500\",\"message\":\"服务器内部异常\"}";
	}

	/**
	 * 根据Map创建一个成功Json
	 * 
	 * @param map
	 * @return
	 */
	public static String crtSuccessJson(Map<?, ?> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"status\":\"200\",");
		sb.append("\"message\":\"成功\",");
		Gson gson = new Gson();
		sb.append("\"data\":\"" + gson.toJson(map) + "\"}");
		return sb.toString();
	}

	/**
	 * 创建带加密参数的Json
	 * @param data 参数
	 * @param encrypt 是否需要加密
	 * @param vi 如需加密，vi表示偏移量
	 * @return
	 */
	public static String crtSuccessJson(String data, boolean encrypt, String vi) {
		if (encrypt) {
			if (StringUtils.isBlank(vi)) {
				data = Base64Utils.encode(data);
			} else {
				if (vi.length() != 8) {
					throw new RuntimeException("===加密偏移量必须是八位字符或数字===");
				}
				data = Base64Utils.encode(data, vi);
			}
		}

		if (data.startsWith("{") || data.startsWith("[")) {
			return "{\"status\":\"200\",\"message\":\"成功\",\"data\":" + data + "}";
		} else {
			return "{\"status\":\"200\",\"message\":\"成功\",\"data\":\"" + data + "\"}";
		}
	}

	/**
	 * 创建一个带data的成功信息
	 * 
	 * @param data
	 *            要回传的信息，必须符合Json格式
	 * @return
	 */
	public static String crtSuccessJson(String data) {
		if ((data.startsWith("{") || data.startsWith("[")) && isBadJson(data)) {
			throw new RuntimeException("===参数不是正确的JSON===");
		}

		if (data.startsWith("{") || data.startsWith("[")) {
			return "{\"status\":\"200\",\"message\":\"成功\",\"data\":" + data + "}";
		} else {
			return "{\"status\":\"200\",\"message\":\"成功\",\"data\":\"" + data + "\"}";
		}
	}

	/**
	 * 从带年份的字典表Map中获取errorCode
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String crtErrorYJson(String errorCode) {
		if (CommConstant.BASECODE_YEAR_MAP.get("010").get("Y").containsKey(errorCode)) {
			return "{\"status\":\"" + errorCode + "\",\"message\":\""
					+ CommConstant.BASECODE_YEAR_MAP.get("010").get("Y").get(errorCode) + "\"}";
		} else {
			return "{\"status\":\"" + errorCode + "\",\"message\":\"basecode error\"}";
		}

	}

	/**
	 * 从不带年份的字典表Map中获取errorCode
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String crtErrorJson(String errorCode) {
		if (CommConstant.BASECODE_MAP.get("010").containsKey(errorCode)) {
			return "{\"status\":\"" + errorCode + "\",\"message\":\""
					+ CommConstant.BASECODE_MAP.get("010").get(errorCode) + "\"}";
		} else {
			return "{\"status\":\"" + errorCode + "\",\"message\":\"basecode error\"}";
		}

	}

	/**
	 * 创建指定错误码和信息的Json
	 * 
	 * @param errorCode
	 *            错误码
	 * @param message
	 *            提示信息
	 * @return
	 */
	public static String crtErrorJson(String errorCode, String message) {
		return "{\"status\":\"" + errorCode + "\",\"message\":\"" + message + "\"}";
	}
}
