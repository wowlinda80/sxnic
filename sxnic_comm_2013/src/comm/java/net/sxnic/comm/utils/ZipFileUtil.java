package net.sxnic.comm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Introduction：支持中文的 压缩及解压缩 工具类
 * 
 * @author 孙宇飞（參考网上代码） create date : 2009-4-27
 */
public class ZipFileUtil {

	private static Log log = LogFactory.getLog(ZipFileUtil.class);

	/**
	 * java 压缩文件方法
	 * 
	 * @param out 目标文件流
	 * @param srcFile 要压缩的普通文件
	 * @param base 为空即可
	 * @param first 一般为true
	 * @throws Exception
	 */
	public static void zip(org.apache.tools.zip.ZipOutputStream out,
			File srcFile, String base, boolean first) throws Exception {		
		if (first) {
			if (srcFile.isDirectory()) {
				out.putNextEntry(new org.apache.tools.zip.ZipEntry("/"));
				base = base + srcFile.getName();
				first = false;
			} else
				base = srcFile.getName();
		}
		if (srcFile.isDirectory()) {
			File[] fl = srcFile.listFiles();
			base = base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName(), first);
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(srcFile);
			int b;

			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	/*
	 * 解压文件 unZip为解压路径
	 */
	public static void unZipFileByOpache(org.apache.tools.zip.ZipFile zipFile,
			String unZipRoot) throws Exception, IOException {
		java.util.Enumeration e = zipFile.getEntries();
		org.apache.tools.zip.ZipEntry zipEntry;
		while (e.hasMoreElements()) {
			zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
			InputStream fis = zipFile.getInputStream(zipEntry);
			if (zipEntry.isDirectory()) {
			} else {
				File file = new File(unZipRoot + File.separator
						+ zipEntry.getName());
				File parentFile = file.getParentFile();
				parentFile.mkdirs();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int len;
				while ((len = fis.read(b, 0, b.length)) != -1) {
					fos.write(b, 0, len);
				}
				fos.close();
				fis.close();
			}
		}
	}

	/**
	 * 进行压缩操作
	 * 
	 * @param destFile 要生成的目标zip文件
	 * @param srcFile 要进行压缩的普通文件
	 * @throws Exception
	 */
	public static void ZipFile(File destFile, File srcFile) throws Exception {
		//如果目标文件及目标文件夹不存在，则先写一个临时文件，创建文件目录，然后zip会覆盖之
		if(!destFile.isDirectory()){
			FileUtils.writeStringToFile(destFile, "data");
		}
		org.apache.tools.zip.ZipOutputStream zos = new org.apache.tools.zip.ZipOutputStream(
				new FileOutputStream(destFile));		
		zip(zos, srcFile, "", true);
		zos.close();
	}

	/**
	 * 进行压缩操作
	 * 
	 * @param zipFileName 要生成的目标zip文件名
	 * @param inputFileName 要进行压缩的普通文件名
	 * @throws Exception
	 */
	public static void ZipFile(String zipFileName, String inputFileName)
			throws Exception {
		org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(
				new FileOutputStream(zipFileName));
		File inputFile = new File(inputFileName);
		zip(out, inputFile, "", true);
		out.close();
	}

	/**
	 * 进行解压缩操作
	 * @param zipFile 要解压缩的zip文件
	 * @param unZipPath 解压目录
	 * @throws Exception
	 */
	public static void unZipFile(File zipFile, String unZipPath)
			throws Exception {
		unZipFileByOpache(new org.apache.tools.zip.ZipFile(zipFile), unZipPath);

	}

	/**
	 * 进行解压缩操作
	 * @param zipFile 要解压缩的zip文件名
	 * @param unZipPath 解压目录
	 * @throws Exception
	 */
	public static void unZipFile(String unZipFileName, String unZipPath)
			throws Exception {
		org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
				unZipFileName);
		unZipFileByOpache(zipFile, unZipPath);

	}

}
