package net.sxnic.comm.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @Description: JsonUtils.java 描述
 * @Author Lixd-remixty@163.com
 * @Version 1.0
 * @CreateDate:2014年12月3日
 */
public class JsonUtils {

		protected final static Log logger = LogFactory.getLog(JsonUtils.class);

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
}
