package net.sxnic.comm.attachment.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.attachment.Attachment;
import net.sxnic.comm.attachment.FileSizException;
import net.sxnic.comm.attachment.FileTypeException;
import net.sxnic.comm.utils.CommUtils;
import net.sxnic.comm.utils.PropertyUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 附件相关工具类
 * 
 * @author 孙宇飞
 * 
 */
public class AttachmentUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(AttachmentUtils.class);

	/**
	 * 验证附件大小及后缀名
	 * 
	 * @param file
	 *            附件
	 * @param fileName
	 *            附件名
	 * @param types
	 *            后缀名数组，为空则从属性表中提起默认配置
	 * @param fileSizeProperty
	 *            文件大小的属性文件配置名
	 * @return 返回错误信息，如果没有错误则返回空。
	 */
	public static String checkAttachment(File file, String fileName,
			String[] types, String fileSizeProperty) {

		if (file == null) {
			return "";
		}

		String sfiletype="";
		
		if (types == null || types.length <= 0) {
			types = StringUtils.split(
					CommConstant.PROPERTY_MAP.get("upload.file.type"), ",");
			sfiletype=CommConstant.PROPERTY_MAP.get("upload.file.type");
		}else{
			sfiletype= Arrays.toString(types);
		}

		long size = 0L;
		if (StringUtils.isBlank(fileSizeProperty)) {
			size = Long.parseLong(CommConstant.PROPERTY_MAP
					.get("upload.file.size.default")) * 1024 * 1024;
		} else if (StringUtils.isNotBlank(CommConstant.PROPERTY_MAP
				.get(fileSizeProperty))) {
			size = Long.parseLong(CommConstant.PROPERTY_MAP
					.get(fileSizeProperty)) * 1024 * 1024;
		}else{
			size = Long.parseLong(CommConstant.PROPERTY_MAP
					.get("upload.file.size.default")) * 1024 * 1024;
		}

		if (file.length() > size) {
			return "附件超过规定大小，请确认附件小于"
					+ size / (1024*1024) + "MB。";
		}

		if (StringUtils.isBlank(fileName)) {
			return "文件名错误";
		}

		if (!ArrayUtils.contains(types,
				StringUtils.substringAfterLast(fileName, ".").toLowerCase())) {
			return "附件类型错误，请确认附件类型为："
					+ sfiletype
					+ "中的一种。";
		}

		return "";
	}
	
	/**
	 * 验证附件大小及后缀名
	 * 
	 * @param file
	 *            附件
	 * @param fileName
	 *            附件名
	 * @param types
	 *            后缀名数组，为空则从属性表中提起默认配置
	 * @param fileSizeProperty
	 *            文件大小的属性文件配置名
	 * @return 返回错误信息，如果没有错误则返回空。
	 */
	public static String checkAttachmentMsg(File file, String fileName,
			String[] types, String fileSizeProperty) {

		if (file == null) {
			return "";
		}

		String sfiletype="";
		
		if (types == null || types.length <= 0) {
			types = StringUtils.split(
					CommConstant.PROPERTY_MAP.get("upload.file.type"), ",");
			sfiletype=CommConstant.PROPERTY_MAP.get("upload.file.type");
		}else{
			sfiletype= Arrays.toString(types);
		}

		long size = 0L;
		size = 10 * 1024 * 1024;

		if (file.length() > size) {
			return "cabinetUploadSizeError";
		}

		if (!ArrayUtils.contains(types,
				StringUtils.substringAfterLast(fileName, ".").toLowerCase())) {
			return "catinetUploadTypeError";
		}

		return "";
	}

	/**
	 * 验证附件的合法性 主要是文件类型和大小
	 * 
	 * @param file
	 *            要验证的文件
	 * @param types
	 *            类型数组，如doc,txt,pdf,png
	 * @param size
	 *            int型，如1*1024*1024=1M 。如果size=-1 则表示不受限制
	 * @return 验证正确返回true，否则返回false
	 * @throws FileSizException
	 *             文件大小异常
	 * @throws FileTypeException
	 *             文件类型异常
	 */
	public static boolean checkAttachment(File file, String fileName,
			String[] types, long size) throws FileSizException,
			FileTypeException {

		if (file == null) {
			return false;
		}

		if (types == null || types.length <= 0) {
			types = StringUtils.split(
					(String) CommConstant.PROPERTY_MAP.get("upload.file.type"),
					",");
		}

		if (size == 0) {
			size = Long.parseLong(CommConstant.PROPERTY_MAP
					.get("upload.file.maxsize")) * 1024;
		}

		if (file.length() > size) {

			throw new FileSizException();
		}

		if (!ArrayUtils.contains(types,
				StringUtils.substringAfterLast(fileName.toLowerCase(), "."))) {
			throw new FileTypeException();
		}

		return true;
	}

	/**
	 * 保存附件。根据系统设置里的附件设置路径，进行路径重组，然后再保存
	 * 
	 * @param file
	 *            源文件
	 * @param oriname
	 *            //aa.doc
	 * @param enityName
	 *            实体类的名字
	 * @return 附件对象
	 * @throws IOException
	 */
	public static Attachment saveAttachment(File file, String oriname,
			String enityName) throws IOException {

		Attachment attachment = new Attachment();

		if (StringUtils.isBlank(enityName)) {
			return null;
		}

		String fileName = CommUtils.crtRandomUUID() + "."
				+ StringUtils.substringAfterLast(oriname, ".");

		String yearMonth = CommUtils.getCurrYear() + "_"
				+ CommUtils.getCurrMonth();

		// 文件存放的全部路径
		String destFile = "";
		// 除基本路径之外的路径名
		String filePath = "";

		if (CommUtils.isWindowsOs()) {
			String tempPath = CommConstant.PROPERTY_MAP.get("upload.file.path");

			// 绝对路径
			if (tempPath.startsWith("DIR:")) {
				tempPath = StringUtils.remove(tempPath, "DIR:");
				filePath = enityName + File.separator + yearMonth
						+ File.separator + fileName;
				destFile = tempPath + filePath;
			} else {
				// 相对路径处理
				if (!tempPath.startsWith("\\")) {
					tempPath = File.separator + tempPath;
				}
				if (!tempPath.endsWith("\\")) {
					tempPath = tempPath + File.separator;
				}
				destFile = System.getProperty("webapp.root") + tempPath
						+ enityName + File.separator + yearMonth
						+ File.separator + fileName;
			}
		} else if (CommUtils.isLinuxOs()) {
			String tempPath = CommConstant.PROPERTY_MAP
					.get("upload.file.path.linux");

			tempPath = StringUtils.replace(tempPath, "\\", "/");
			// 绝对路径
			if (tempPath.startsWith("DIR:")) {
				tempPath = StringUtils.remove(tempPath, "DIR:");
				filePath = enityName + File.separator + yearMonth
						+ File.separator + fileName;
				destFile = tempPath + filePath;
			} else {
				// 相对路径处理
				if (!tempPath.startsWith("\\")) {
					tempPath = File.separator + tempPath;
				}
				if (!tempPath.endsWith("\\")) {
					tempPath = tempPath + File.separator;
				}
				destFile = System.getProperty("webapp.root") + tempPath
						+ enityName + File.separator + yearMonth
						+ File.separator + fileName;
			}
		}

		try {
			FileUtils.copyFile(file, new File(destFile));
			logger.debug("====copy file:" + file.getAbsolutePath() + " to "
					+ destFile + " success!====");
			attachment.setOriName(oriname);
			attachment.setFilePath(filePath);
			attachment.setSuffix(CommUtils.getSuffixByFileName(oriname));
		} catch (IOException e) {

			logger.error("===copy file:" + file.getAbsolutePath() + " to "
					+ destFile + " error!====" + e.toString());
			throw e;

		}
		return attachment;
	}

	/**
	 * 保存附件。根据系统设置里的附件设置路径，进行路径重组，然后再保存
	 * 
	 * @param file
	 *            源文件
	 * @param oriname
	 *            //aa.doc
	 * @param enityName
	 *            实体类的名字
	 * @param entityId
	 *            实体类的名字
	 * @param type
	 *            附件类型
	 * @return 附件对象
	 * @throws IOException
	 */
	public static Attachment saveAttachment(File file, String oriname,
			String enityName, String entityId, String type) throws IOException {

		Attachment attachment = new Attachment();

		if (StringUtils.isBlank(enityName)) {
			return null;
		}

		String fileName = CommUtils.crtRandomUUID() + "."
				+ StringUtils.substringAfterLast(oriname, ".");

		String yearMonth = CommUtils.getCurrYear() + "_"
				+ CommUtils.getCurrMonth();

		// 文件存放的全部路径
		String destFile = "";
		// 除基本路径之外的路径名
		String filePath = "";

		if (CommUtils.isWindowsOs()) {
			String tempPath = (String) CommConstant.PROPERTY_MAP
					.get("upload.file.path");

			// tempPath = StringUtils.replace(tempPath, "/", "\\");
			// 绝对路径
			if (tempPath.startsWith("DIR:")) {
				tempPath = StringUtils.remove(tempPath, "DIR:");
				filePath = enityName + File.separator + yearMonth
						+ File.separator + fileName;
				destFile = tempPath + filePath;
			} else {
				// 相对路径处理
				if (!tempPath.startsWith("\\")) {
					tempPath = File.separator + tempPath;
				}
				if (!tempPath.endsWith("\\")) {
					tempPath = tempPath + File.separator;
				}
				destFile = System.getProperty("webapp.root") + tempPath
						+ enityName + File.separator + yearMonth
						+ File.separator + fileName;
			}
		} else if (CommUtils.isLinuxOs()) {
			String tempPath = (String) CommConstant.PROPERTY_MAP
					.get("upload.file.path.linux");

			tempPath = StringUtils.replace(tempPath, "\\", "/");
			// 绝对路径
			if (tempPath.startsWith("DIR:")) {
				tempPath = StringUtils.remove(tempPath, "DIR:");
				filePath = enityName + File.separator + yearMonth
						+ File.separator + fileName;
				destFile = tempPath + filePath;
			} else {
				// 相对路径处理
				if (!tempPath.startsWith("\\")) {
					tempPath = File.separator + tempPath;
				}
				if (!tempPath.endsWith("\\")) {
					tempPath = tempPath + File.separator;
				}
				destFile = System.getProperty("webapp.root") + tempPath
						+ enityName + File.separator + yearMonth
						+ File.separator + fileName;
			}
		}

		try {
			FileUtils.copyFile(file, new File(destFile));
			logger.warn("====copy file:" + file.getAbsolutePath() + " to "
					+ destFile + " success!====");
			attachment.setOriName(oriname);
			attachment.setFilePath(filePath);
			attachment.setEntityId(entityId);
			attachment.setType(type);
			attachment.setSuffix(CommUtils.getSuffixByFileName(oriname));
		} catch (IOException e) {
			logger.error("===copy file:" + file.getAbsolutePath() + " to "
					+ destFile + " error!====" + e.toString());
			throw e;

		}
		return attachment;
	}

	/**
	 * 从属性文件中读取附件保存的基本路径
	 * 
	 * @return 附件保存的基本路径
	 */
	public static String getFileBasePath() {
		String basePath = "";

		if (CommUtils.isWindowsOs()) {
			basePath = (String) CommConstant.PROPERTY_MAP
					.get("upload.file.path");
			if (basePath.startsWith("DIR:")) {
				basePath = StringUtils.remove(basePath, "DIR:");
			}

			if (!basePath.endsWith("\\")) {
				basePath = basePath + "\\";
			}

		} else if (CommUtils.isLinuxOs()) {
			basePath = (String) CommConstant.PROPERTY_MAP
					.get("upload.file.path.linux");
			if (basePath.startsWith("DIR:")) {
				basePath = StringUtils.remove(basePath, "DIR:");
			}

			if (!basePath.endsWith("/")) {
				basePath = basePath + "/";
			}
		}

		return basePath;
	}

	/**
	 * 设置 下载路径
	 * 
	 * @param alias
	 *            所在文件实体的别名，包名。 notice,recievedoc,senddoc...
	 * @param attach
	 *            附件实体
	 * @return  下载路径
	 */
	public static String getDownloadPath(String alias, Attachment attach) {

		if (attach == null) {
			return "";
		}

		String url = "";

		if (PropertyUtil.DOWNLOAD_FILE_TYPE_TOMCAT
				.equals(CommConstant.PROPERTY_MAP
						.get(PropertyUtil.DOWNLOAD_FILE_TYPE))
				|| PropertyUtil.DOWNLOAD_FILE_TYPE_APACHE
						.equals(CommConstant.PROPERTY_MAP
								.get(PropertyUtil.DOWNLOAD_FILE_TYPE))) {
			// tomcat apache
			url = PropertyUtil.getProperty("doc.download.path");

			if (StringUtils.isBlank(url)) {
				url = "";
			} else {
				Calendar c = Calendar.getInstance();
				c.setTime(attach.getCreationDate());

				url = url + alias + "/" + String.valueOf(c.get(Calendar.YEAR))+"_"+String.valueOf(c.get(Calendar.MONTH)+1)
						+ "/" + StringUtils.substringAfterLast(attach.getFilePath(), "\\");
			}

		} else if (PropertyUtil.DOWNLOAD_FILE_TYPE_STRUTS
				.equals(CommConstant.PROPERTY_MAP
						.get(PropertyUtil.DOWNLOAD_FILE_TYPE))) {
			// struts
			url = CommConstant.SYSTEM_CONTEXTPATH + "/comm/download.jspa?id="
					+ attach.getId();

		} else {
			url = "get attachment url error";
		}

		return url;
	}

	/**
	 * 删除附件
	 * 
	 * @param aList
	 *            附件列表
	 * @throws IOException
	 */
	public static void deleteAttachments(List<Attachment> aList)
			throws IOException {
		if (aList == null || aList.size() == 0) {
			return;
		}

		for (Attachment a : aList) {
			deleteAttachment(a);
		}
	}

	/**
	 * 删除附件
	 * 
	 * @param aset
	 *            附件列表
	 * @throws IOException
	 */
	public static void deleteAttachments(Set<Attachment> aset)
			throws IOException {
		if (aset == null || aset.size() == 0) {
			return;
		}

		for (Attachment a : aset) {
			deleteAttachment(a);
		}
	}

	/**
	 * 删除附件
	 * 
	 * @param a
	 *            附件
	 * @throws IOException
	 */
	public static void deleteAttachment(Attachment a) throws IOException {
		if (a == null) {
			return;
		}

		String base_upload_path = "";
		if (CommUtils.isWindowsOs()) {
			base_upload_path = CommConstant.PROPERTY_MAP
					.get("upload.file.path");
		} else {
			base_upload_path = CommConstant.PROPERTY_MAP
					.get("upload.file.path.linux");
		}

		base_upload_path = StringUtils.startsWith(base_upload_path, "DIR:") ? StringUtils
				.removeStart(base_upload_path, "DIR:") : base_upload_path;

		String srcpath = base_upload_path + a.getFilePath();

		// 转储
		delete_backup(base_upload_path, a);

		// 删除
		FileUtils.deleteQuietly(new File(srcpath));
	}

	/**
	 * 上传文件时，文件转存。
	 * 
	 * @param base_upload_path
	 *            基础文件上传路径
	 * @param a
	 *            附件对象
	 * @throws IOException
	 */
	public static void syn_upload(String base_upload_path, Attachment a)
			throws IOException {
		if ("true".equals(CommConstant.PROPERTY_MAP.get("attachment.syn"))) {

			if (StringUtils.isBlank(base_upload_path)) {
				if (CommUtils.isWindowsOs()) {
					base_upload_path = CommConstant.PROPERTY_MAP
							.get("upload.file.path");
				} else {
					base_upload_path = CommConstant.PROPERTY_MAP
							.get("upload.file.path.linux");
				}

				base_upload_path = StringUtils.startsWith(base_upload_path,
						"DIR:") ? StringUtils.removeStart(base_upload_path,
						"DIR:") : base_upload_path;
			}

			String destpath = "";

			if (CommUtils.isWindowsOs()) {
				destpath = CommConstant.PROPERTY_MAP
						.get("attachment.syn.path.windows");
			} else {
				destpath = CommConstant.PROPERTY_MAP
						.get("attachment.syn.path.linux");
			}

			destpath = StringUtils.startsWith(destpath, "DIR:") ? StringUtils
					.removeStart(destpath, "DIR:") : destpath;

			destpath = destpath + a.getFilePath();

			FileUtils.copyFile(new File(base_upload_path + a.getFilePath()),
					new File(destpath));
		}
	}

	/**
	 * 删除附件时 ，转储文件
	 * 
	 * @param base_upload_path
	 *            基础文件上传路径
	 * @param a
	 *            附件对象
	 * @throws IOException
	 */
	public static void delete_backup(String base_upload_path, Attachment a)
			throws IOException {
		if ("true".equals(CommConstant.PROPERTY_MAP
				.get("attachment.delete.backup"))) {

			if (StringUtils.isBlank(base_upload_path)) {
				if (CommUtils.isWindowsOs()) {
					base_upload_path = CommConstant.PROPERTY_MAP
							.get("upload.file.path");
				} else {
					base_upload_path = CommConstant.PROPERTY_MAP
							.get("upload.file.path.linux");
				}

				base_upload_path = StringUtils.startsWith(base_upload_path,
						"DIR:") ? StringUtils.removeStart(base_upload_path,
						"DIR:") : base_upload_path;
			}

			String destpath = "";

			if (CommUtils.isWindowsOs()) {
				destpath = CommConstant.PROPERTY_MAP
						.get("attachment.delete.backup.windows");
			} else {
				destpath = CommConstant.PROPERTY_MAP
						.get("attachment.delete.backup.linux");
			}

			destpath = StringUtils.startsWith(destpath, "DIR:") ? StringUtils
					.removeStart(destpath, "DIR:") : destpath;

			destpath = destpath + a.getFilePath();

			FileUtils.copyFile(new File(base_upload_path + a.getFilePath()),
					new File(destpath));
		}
	}

}
