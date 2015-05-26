package net.sxnic.comm.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.attachment.Attachment;
import net.sxnic.comm.attachment.AttachmentManager;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件下载action 根据附件Id查找附件位置，并把文件流发送到客户端
 * @author 孙宇飞
 *
 */
@SuppressWarnings("serial")
public class DownloadAction extends CommActionSupport {

	@Autowired
	private AttachmentManager attachmentManager;

	private String downloadFileName;
	private File downFile;

	/**
	 * 获取文件流
	 * @return 文件流
	 * @throws Exception
	 */
	public InputStream getInputStream() throws Exception {		
		return FileUtils.openInputStream(downFile);
	}

	/**
	 * 保存的时候要显示的文件名
	 * @return 显示时的文件名
	 */
	public String getDownloadFileName() {
		String downFileName = "";

		try {
			downFileName = new String(downloadFileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return downFileName;
	}

	public String execute() throws Exception {

		Attachment a = attachmentManager.get(id);
		String basePath = "";
		
		if (CommUtils.isWindowsOs()) {
			basePath = (String) CommConstant.PROPERTY_MAP
					.get("upload.file.path");
			if (basePath.startsWith("DIR:")) {
				basePath = StringUtils.remove(basePath, "DIR:");
			}
			
			if(!basePath.endsWith("\\")){
				basePath = basePath+"\\";
			}
			
			if(a.getFilePath().startsWith("\\")){
				a.setFilePath(StringUtils.removeStart(a.getFilePath(), "\\"));
			}
			
		}else if (CommUtils.isLinuxOs()) {
			basePath = (String) CommConstant.PROPERTY_MAP
					.get("upload.file.path.linux");
			if (basePath.startsWith("DIR:")) {
				basePath = StringUtils.remove(basePath, "DIR:");
			}
			
			if(!basePath.endsWith("/")){
				basePath = basePath+"/";
			}
			
			if(a.getFilePath().startsWith("/")){
				a.setFilePath(StringUtils.removeStart(a.getFilePath(), "/"));
			}
		}
		
		downFile = new File(basePath+a.getFilePath());
		downloadFileName = a.getOriName();

		if (!downFile.isFile()) {
			return INPUT;
		}

		return SUCCESS;
	}

	public File getDownFile() {
		return downFile;
	}

	public void setDownFile(File downFile) {
		this.downFile = downFile;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
}