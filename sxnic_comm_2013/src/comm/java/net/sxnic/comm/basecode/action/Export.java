package net.sxnic.comm.basecode.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.Action;

/**
 * @author 孙宇飞
 * @version 2009-3-4
 *          <p>
 *          Introduction ：导入基本码信息，上传文件，或直接文本
 */

public class Export extends BaseCodeAction implements Action {

	private static final long serialVersionUID = 1L;

	/**
	 * 获取文件流
	 * @return 文件流
	 * @throws Exception
	 */
	public InputStream getInputStream() throws Exception {
		
		String fileName = "c:\\temp\\测试计划.png";
		
		return FileUtils.openInputStream(new File(fileName));
	}

	/**
	 * 保存的时候要显示的文件名
	 * @return 文件名
	 */
	public String getDownloadFileName() {
		String downFileName = "测试计划.png";

		try {
			downFileName = new String(downFileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return downFileName;
	}

	public String execute() throws Exception {

		return SUCCESS;
	}

}
