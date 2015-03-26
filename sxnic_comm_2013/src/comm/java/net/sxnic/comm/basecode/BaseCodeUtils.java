package net.sxnic.comm.basecode;

import java.util.HashMap;
import java.util.Map;

import net.sxnic.comm.CommConstant;

import org.apache.commons.lang.StringUtils;

/**
 * BaseCode工具类
 * 
 * @author 孙宇飞
 * @update 孙宇飞 20150317 去掉了生成select的方法，其他方法都加入了年的参数
 */
public class BaseCodeUtils {

	/**
	 * get infoCode from basecode Map
	 * 
	 * @param sortCode
	 *            不能为空
	 * @param cyear
	 *            不能为空
	 * @param infoCode
	 *            不能为空
	 * @return
	 */
	public static String getInfoName(String sortCode, String cyear, String infoCode) {

		// 如果年为空 则直接取默认年
		if (StringUtils.isBlank(cyear)) {
			if (CommConstant.BASECODE_MAP.containsKey(sortCode)) {
				return CommConstant.BASECODE_MAP.get(sortCode).get(infoCode);
			} else if (CommConstant.BASECODE_YEAR_MAP.containsKey(sortCode)) {
				return CommConstant.BASECODE_YEAR_MAP.get(sortCode).get("Y").get(infoCode);
			} else {
				return "has no this sortCode";
			}
		}

		if (CommConstant.BASECODE_YEAR_MAP.get(sortCode).containsKey(cyear)) {
			return CommConstant.BASECODE_YEAR_MAP.get(sortCode).get(cyear).get(infoCode);
		} else if (CommConstant.BASECODE_YEAR_MAP.get(sortCode).containsKey("Y")) {
			return CommConstant.BASECODE_YEAR_MAP.get(sortCode).get("Y").get(infoCode);
		} else {
			return "error year";
		}

	}

	/**
	 * 根据sortCode和infoName 查询infoCode
	 * 
	 * @param sortCode
	 *            不能为空
	 * @param cyear
	 *            不能为空
	 * @param infoName
	 *            不能为空
	 * @return
	 */
	public static String getInfoCode(String sortCode, String cyear, String infoName) {

		Map<String, String> map = new HashMap<String, String>();

		if (CommConstant.BASECODE_YEAR_MAP.containsKey(sortCode)) {

			// 如果年为空 则直接取默认年
			if (StringUtils.isBlank(cyear)) {
				map = CommConstant.BASECODE_YEAR_MAP.get(sortCode).get("Y");
			}

			if (CommConstant.BASECODE_YEAR_MAP.get(sortCode).containsKey(cyear)) {
				map = CommConstant.BASECODE_YEAR_MAP.get(sortCode).get(cyear);
			} else if (CommConstant.BASECODE_YEAR_MAP.get(sortCode).containsKey("Y")) {
				map = CommConstant.BASECODE_YEAR_MAP.get(sortCode).get("Y");
			} else {
				return "error year";
			}

			if (map != null && map.size() > 0) {
				for (String key : map.keySet()) {
					if (infoName.endsWith(map.get(key))) {
						return key;
					}
				}

				return "has not code";
			} else {
				return "error year";
			}

		} else {
			return "error sortCode";
		}

	}

}
