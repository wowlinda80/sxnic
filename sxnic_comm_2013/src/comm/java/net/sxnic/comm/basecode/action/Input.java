package net.sxnic.comm.basecode.action;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;

import net.sxnic.comm.basecode.BaseCode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.ServletContextAware;

/**
 * @author 孙宇飞
 * @version 2009-3-4
 *          <p>
 *          Introduction ：导入基本码信息，上传文件，或直接文本
 */
@SuppressWarnings("unchecked")
public class Input extends BaseCodeAction implements ServletContextAware {

	private Log log = LogFactory.getLog(Input.class);

	private static final long serialVersionUID = 1L;

	private String txtBaseCode;

	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private ServletContext servletContext;

	
	public String execute() throws Exception {

		boolean isSuccess = false;
		String log ="";

		if (StringUtils.isBlank(getUploadFileName())
				&& StringUtils.isBlank(txtBaseCode)) {
			// 如果上传文件和文本信息为空，则返回提示信息
			this.addActionMessage("请选择上传文件或填写文本信息");
			return INPUT;
		}

		if (StringUtils.isNotBlank(getUploadFileName())
				&& StringUtils.isNotBlank(txtBaseCode)) {
			// 如果上传文件和文本信息都不空，则返回
			this.addActionMessage("请选择上传文件或填写文本信息其中的一种方式进行导入操作");
			return INPUT;
		}

		try {
			
			// 以上传文件形式导入
			if (StringUtils.isNotBlank(getUploadFileName())) {

				String fileName = getUploadFileName();

				Calendar c = Calendar.getInstance();

				File saveFile = new File(servletContext.getRealPath("/")
						+ "/files/" + c.get(Calendar.YEAR) + "/" + fileName);
				if (!saveFile.getParentFile().isDirectory()) {
					saveFile.getParentFile().mkdirs();
				}

				// copy 临时上传文件到正式文件目录
				org.apache.commons.io.FileUtils.copyFile(upload, saveFile);

				String filePath = "/files/" + c.get(Calendar.YEAR) + "/"
						+ fileName;

				// chengeFileEncoding(upload,"gbk");

				List<String> lines = FileUtils.readLines(upload, "gbk");

				int size = lines.size();

				isSuccess = inputBaseCode((String[]) lines
						.toArray(new String[size]));

				addActionMessage("上传文件成功：" + uploadFileName);

				 log = "用户导入BaseCode数据文件成功，文件：" + filePath;				
			}

			// 输入方式导入
			if (StringUtils.isNotBlank(txtBaseCode)) {

				String[] bcs = StringUtils.split(txtBaseCode, '\n');
				isSuccess = inputBaseCode(bcs);

				 log = "用户导入BaseCode数据文件成功，内容：" + txtBaseCode;
				
			}
		} catch (RuntimeException e) {
			 log = "用户导入BaseCode数据失败";			

			return INPUT;
		}

		if (isSuccess) {
			addActionMessage("导入数据成功");
			return SUCCESS;
		} else {
			addActionMessage("导入数据失败，请查看数据格式是否正确！");
			return INPUT;
		}
	}

	private boolean inputBaseCode(String[] basecodes) {

		for (String line : basecodes) {
			if (StringUtils.isBlank(line)) {
				continue;
			}

			try {
				String[] m = line.split(",");

				BaseCode bc = new BaseCode();
				bc.setSortCode(m[0].trim());
				bc.setSortName(m[1].trim());
				bc.setInfoCode(m[2].trim());
				bc.setInfoName(m[3].trim());
				bc.setInfoIndex(m[4].trim());
				bc.setCyear(String.valueOf(Calendar.getInstance().get(
						Calendar.YEAR)));

				basecodeManager.save(bc);
			} catch (RuntimeException e) {
				return false;
			}
		}

		return true;

	}

	private void chengeFileEncoding(File file, String encoding)
			throws IOException {
		if (!file.isFile()) {
			throw new IOException("file:" + file.getPath() + " is not exist!");
		}

		if (encoding == null || "".equals(encoding)) {
			throw new NullPointerException("encoding:" + encoding + " is null");
		}

		String contents = FileUtils.readFileToString(file);

		FileUtils.writeStringToFile(file, contents, encoding);

		log.debug(file.getPath() + "'encoding has been chenged to " + encoding);
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getTxtBaseCode() {
		return txtBaseCode;
	}

	public void setTxtBaseCode(String txtBaseCode) {
		this.txtBaseCode = txtBaseCode;
	}

	public void setServletContext(ServletContext sc) {
		this.servletContext = sc;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

}
