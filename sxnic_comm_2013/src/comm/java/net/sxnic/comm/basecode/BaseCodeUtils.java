package net.sxnic.comm.basecode;

import java.util.Iterator;
import java.util.Map;

import net.sxnic.comm.CommConstant;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * BaseCode工具类
 * @author 孙宇飞
 *
 */
@SuppressWarnings("unchecked")
public class BaseCodeUtils {

	private static Log log = LogFactory.getLog(BaseCodeUtils.class);

	/**
	 * get infoCode from basecode Map
	 * 
	 * @param sortCode
	 * @param infoCode
	 * @return
	 */
	public static String getInfoName(String sortCode, String infoCode) {

		Map<String, Map<String, String>> map = CommConstant.BASECODE_MAP;
		if (sortCode == null || "".equals(sortCode)) {
			return "error sortCode";
		}

		if (infoCode == null || "".equals(infoCode)) {
			return "";
		}

		if (map == null) {
			return "error map";
		}

		try {
			Map<String, String> bmap = (Map<String, String>) map.get(sortCode);

			if (bmap == null) {
				log.warn("sortCode error! the map relevanted to " + sortCode
						+ " is null");
				return "errorSortCode";
			}

			String infoName = bmap.get(infoCode);
			if (StringUtils.isBlank(infoName)) {
				log.warn("infoCode error! the infoName relevanted to "
						+ infoCode + " has not found");
				return "errorInfoName";
			}

			return infoName;
		} catch (RuntimeException e) {
			return "errorInfoName";
		}
	}

	/**
	 * 创建 下拉框的Html代码
	 * 
	 * @param sortCode
	 *            类别编码
	 * @param css
	 *            对应css样式
	 * @param onChange
	 *            事件
	 * @return
	 */
	public static String createSelectHtml(String name, String sortCode,
			String css, String onChange, String value, String headerKey,
			String headerValue, String childId, String childDataPath,
			String autoWidth) {

		Map map = CommConstant.BASECODE_MAP;
		if (map == null) {
			return "error map";
		}

		if (name == null || "".equals(name)) {
			return "error name";
		}

		if (sortCode == null || "".equals(sortCode)) {
			return "error sortCode";
		}

		css = "default";

		Map<String, String> bmap = (Map<String, String>) map.get(sortCode);
		StringBuffer sb = new StringBuffer();

		if (bmap == null) {
			log.warn("sortCode error! the map relevanted to " + sortCode
					+ " is null");
			return "error sortcode";
		}

		sb.append("<select id='" + name + "_id' name='" + name + "'");

		if (StringUtils.isNotBlank(onChange)) {
			sb.append(" onchange=\"" + onChange + "\" ");
		}

		if (StringUtils.isNotBlank(css)) {
			sb.append(" class='" + css + "' ");
		}

		if (StringUtils.isNotBlank(childId)) {
			sb.append(" childId='" + childId + "' ");
		}

		if (StringUtils.isNotBlank(childDataPath)) {
			sb.append(" childDataPath='" + childDataPath + "' ");
		}

		if (StringUtils.isNotBlank(autoWidth)) {
			sb.append(" autoWidth='" + autoWidth + "' ");
		}

		sb.append(" >");

		if (StringUtils.isNotBlank(headerKey)
				|| StringUtils.isNotBlank(headerValue)) {
			sb.append("  <option value='" + headerKey
					+ "' selected='selected'>" + headerValue + "</option>");
		}

		Iterator iter = bmap.keySet().iterator();
		while (iter.hasNext()) {
			String infoCode = (String) iter.next();
			if (StringUtils.isNotBlank(value) && infoCode.equals(value)) {
				sb.append("<option value='" + infoCode + "'  selected >"
						+ bmap.get(infoCode) + "</option>");
			} else {
				sb.append("<option value='" + infoCode + "'>"
						+ bmap.get(infoCode) + "</option>");
			}
		}

		sb.append("</select>");

		return sb.toString();

	}

	/**
	 * 根据sortCode和infoName 查询infoCode
	 * 
	 * @param sortCode
	 *            类别编码
	 * @param infoName
	 * @return
	 */
	public static String getInfoCode(String sortCode, String infoName) {

		if (StringUtils.isBlank(sortCode) || StringUtils.isBlank(infoName)) {
			return "";
		}

		Map<String, String> map = CommConstant.BASECODE_MAP.get(sortCode);

		Iterator iter = map.keySet().iterator();

		while (iter.hasNext()) {

			String key = (String) iter.next();

			if (infoName.equals(map.get(key))) {
				return key;
			}
		}

		return "";
	}

}
