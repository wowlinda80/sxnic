package net.sxnic.comm.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.attachment.Attachment;
import net.sxnic.comm.attachment.util.AttachmentUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 附件下载Tag类，在页面上显示附件的现在URL
 * @author 孙宇飞
 *
 */
@SuppressWarnings("serial")
public class DownloadTag extends BodyTagSupport {

	private static Logger logger = LoggerFactory.getLogger(DownloadTag.class);

	/**
	 * 别名，一般是实体类的小写
	 */
	private String alias;
	/**
	 * 附件类
	 */
	private Attachment attach;
	
	/**
	 * 在页面上显示的文件名，可以为空
	 */
	private String fileName;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {

			String contextPath = CommConstant.PROPERTY_MAP.get("system.contextPath");
	        
			String url = AttachmentUtils.getDownloadPath(
					alias, attach);

			out.print("<a href='" + url + "'>");	

			out.print("<img src=\"/" + contextPath + "/style/classic/images/folderimg/" 
					+ attach.getSuffix().toLowerCase() + ".gif\" />");
			
			if(StringUtils.isNotBlank(fileName)){				
				out.print(fileName);
			}else{				
				out.print(attach.getOriName());
			}
			out.print("</a>");

		} catch (Exception e) {
			logger.error("DownloadTag parameter: {} and {} eror: {} ",
					new Object[] { alias, attach.getId(), e.toString() });
		}

		return EVAL_PAGE;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Attachment getAttach() {
		return attach;
	}

	public void setAttach(Attachment attach) {
		this.attach = attach;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
