package net.sxnic.comm.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 舒强 2009-7-27
 * @version syf 添加了按文件下载的方法，并把方法改为static
 */
public class DownloadUtils {

	private static Log log = LogFactory.getLog(DownloadUtils.class);

	private static final String ENTER_ = "\n";

	private static final String TAB_ = "\t";

	/**
	 * 下载文件
	 * 
	 * @param response HttpServletResponse
	 * @param file
	 * @param fFileName 指定的文件名，用于下载时用户可见的名称
	 */
	public static void download(HttpServletResponse response, File file,
			String fFileName) {

		if (!file.isFile()) {
			return;
		}

		response.reset(); // 非常重要
		String fileName = file.getName();

		// 微软办公套件
		if (fileName.endsWith(".doc") || fileName.endsWith(".xls")
				|| fileName.endsWith(".ppt")) {
			response.setContentType("application/x-msdownload");
		}

		// 图片及PDF
		if (fileName.endsWith(".pdf") || fileName.endsWith(".jpeg")
				|| fileName.endsWith(".jpg")) {
			response.setContentType("image/jpeg");
		}

		response.setHeader("Content-Disposition", "attachment; filename="
				+ fFileName);
		OutputStream out;
		try {
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file));
			out.close();
		} catch (IOException e) {
			log.error("create execl error : " + e.toString());
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param response HttpServletResponse
	 * @param file
	 */
	public static void download(HttpServletResponse response, File file) {

		if (!file.isFile()) {
			return;
		}

		download(response, file, file.getName());
	}

	/**
	 * 下载
	 * 
	 * @param response
	 * @param sb StringBuffer
	 */
	public static void download(HttpServletResponse response, StringBuffer sb) {
		response.reset(); // 非常重要
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition",
				"attachment; filename=temp.xls");
		OutputStream out;
		try {
			out = response.getOutputStream();
			out.write(sb.toString().getBytes());
			out.close();
		} catch (IOException e) {
			log.error("create execl error : " + e.toString());
		}
	}

	/**
	 * 生成具体内容的相关文件
	 * 
	 * @param list 要生成的数据，这要注意，在封装字符串的时候，每列之间要加上TAB_，每行之间要加上ENTER_
	 * @param response
	 */
	public static void queryDownload(List<String> list,
			HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			sb.append(str);
		}
		DownloadUtils.download(response, sb);
	}

}
