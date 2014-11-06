package net.sxnic.comm.utils;

import java.io.File;

import net.sxnic.comm.CommConstant;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对属性表的定义及操作
 * 
 * @author 孙宇飞
 * 
 */
public class PropertyUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertyUtil.class);

	public static final String upload_file_path = "upload.file.path";

	public static final String temp_file_path = "temp.file.path";

	public static final String log_file_path = "log.file.path";

	public static final String _LINUX = ".linux";

	public static final String MAIL_HOST = "mail.host";
	public static final String MAIL_USERNAME = "mail.username";
	public static final String MAIL_PASSWORD = "mail.password";

	public static final String UPLOAD_FILE_TYPE = "upload.file.type";
	public static final String UPLOAD_FILE_SIZE = "upload.file.size";

	/**
	 * 下载方式
	 */
	public static final String DOWNLOAD_FILE_TYPE = "doc.download.type";

	/**
	 * tomcat下载方式
	 */
	public static final String DOWNLOAD_FILE_TYPE_TOMCAT = "tomcat";

	/**
	 * struts2下载方式
	 */
	public static final String DOWNLOAD_FILE_TYPE_STRUTS = "struts";

	/**
	 * apache下载方式
	 */
	public static final String DOWNLOAD_FILE_TYPE_APACHE = "apache";

	public static String getProperty(String propertyName) {
		return CommConstant.PROPERTY_MAP.get(propertyName);
	}

	/**
	 * 获取上传路径，默认为d:\\temp\\
	 * 
	 * @return
	 */
	public static String getUploadPath() {
		String path = "d:\\temp\\";
		String temp = null;

		if (CommUtils.isLinuxOs()) {
			temp = CommConstant.PROPERTY_MAP.get("upload.file.path.linux");

			if (StringUtils.isBlank(temp)) {
				temp = "/tmp/";
				return temp;
			}
		} else if (CommUtils.isWindowsOs()) {
			temp = CommConstant.PROPERTY_MAP.get("upload.file.path");
			if (StringUtils.isBlank(temp)) {
				temp = "d:\\temp\\";
				return temp;
			}
		}

		// 绝对路径
		if (temp.trim().startsWith(CommConstant.ABSOLUTE_PATH_PREFIX)) {
			path = StringUtils.remove(temp, CommConstant.ABSOLUTE_PATH_PREFIX);
			if (!path.endsWith(File.separator)) {
				path = path + File.separator;
			}
		} else {
			// TODO 相对路径的话，需要前面加WebRoot路径
		}

		return path;
	}

	/**
	 * 获取临时文件路径，默认为d:\\temp\\ 或 /tmp/
	 * 
	 * @return
	 */
	public static String getTempPaht() {
		String path = "d:\\temp\\";
		String temp = null;

		if (CommUtils.isLinuxOs()) {
			temp = CommConstant.PROPERTY_MAP.get("temp.file.path.linux");

			if (StringUtils.isBlank(temp)) {
				temp = "/tmp/";
				return temp;
			}
		} else if (CommUtils.isWindowsOs()) {
			temp = CommConstant.PROPERTY_MAP.get("temp.file.path");
			if (StringUtils.isBlank(temp)) {
				temp = "d:\\temp\\";
				return temp;
			}
		}

		// 绝对路径
		if (temp.trim().startsWith(CommConstant.ABSOLUTE_PATH_PREFIX)) {
			path = StringUtils.remove(temp, CommConstant.ABSOLUTE_PATH_PREFIX);
			if (!path.endsWith(File.separator)) {
				path = path + File.separator;
			}
		} else {
			// TODO 相对路径的话，需要前面加WebRoot路径
		}

		return path;
	}

	/**
	 * 获取log文件路径，默认为d:\\temp\\ 或 /tmp/
	 * 
	 * @return
	 */
	public static String getLogFilePaht() {
		String path = "d:\\temp\\";
		String temp = null;

		if (CommUtils.isLinuxOs()) {
			temp = CommConstant.PROPERTY_MAP.get("log.file.path.linux");

			if (StringUtils.isBlank(temp)) {
				temp = "/tmp/";
				return temp;
			}
		} else if (CommUtils.isWindowsOs()) {
			temp = CommConstant.PROPERTY_MAP.get("log.file.path");
			if (StringUtils.isBlank(temp)) {
				temp = "d:\\temp\\";
				return temp;
			}
		}

		// 绝对路径
		if (temp.trim().startsWith(CommConstant.ABSOLUTE_PATH_PREFIX)) {
			path = StringUtils.remove(temp, CommConstant.ABSOLUTE_PATH_PREFIX);
			if (!path.endsWith(File.separator)) {
				path = path + File.separator;
			}
		} else {
			// TODO 相对路径的话，需要前面加WebRoot路径
		}

		return path;
	}

	

	/**
	 * 获取category1对应的状态编码，如006在BaseCode中状态编码为102
	 * 
	 * @param category1
	 *            对应的大类别编码
	 * @return
	 */
	public static String getStatusBaseCode(String category1) {
		if (StringUtils.isBlank(category1)) {
			return "";
		}

		String tmp = CommConstant.PROPERTY_MAP.get("status." + category1);

		if (StringUtils.isBlank(tmp)) {
			return "";
		} else {
			return tmp;
		}
	}

	/**
	 * 从property表中查询项目每页显示数量，如果没有定义则显示默认条数。
	 * 
	 * @param type
	 * @return
	 */
	public static int findTableSize(String type) {

		String temp = CommConstant.PROPERTY_MAP.get("default.tabsize");
		int defaultNum = 0;

		if (StringUtils.isBlank(temp)) {
			defaultNum = 20;
		} else {
			defaultNum = Integer.parseInt(temp);
		}

		String c = CommConstant.PROPERTY_MAP.get(type);

		if (StringUtils.isBlank(c)) {
			return defaultNum;
		} else {
			return Integer.parseInt(c);
		}
	}
}
