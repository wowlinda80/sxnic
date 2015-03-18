package net.sxnic.comm.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.attachment.Attachment;
import net.sxnic.comm.security.Base64Utils;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description: 为附件中心的下载专用Tag
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015年3月18日
 */
@SuppressWarnings("serial")
public class DownloadCenterTag extends BodyTagSupport {

	private static Logger logger = LoggerFactory.getLogger(DownloadCenterTag.class);

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

			if (!CommConstant.PROPERTY_MAP.containsKey("doc.download.path")) {
				out.print("no attachment.center.path");
				return EVAL_PAGE;
			}

			String path = CommConstant.PROPERTY_MAP.get("doc.download.path");
			String key = CommUtils.creCharOrNumRandom(8, 4);
			
			//把偏移量也传过去，file是根据偏移量加密过的entityId,即附件中心的附件实体ID
			path = path + "/download.php?file=" + Base64Utils.encode(attach.getEntityId(), key) +"&key="+key;			

			out.print("<a href='" + path + "'>");

			out.print("<img src=\"/" + path + "/style/classic/images/folderimg/" + attach.getSuffix().toLowerCase()
					+ ".gif\" />");

			if (StringUtils.isNotBlank(fileName)) {
				out.print(fileName);
			} else {
				out.print(attach.getOriName());
			}
			out.print("</a>");

		} catch (Exception e) {
			logger.error("DownloadTag parameter: {} error: {} ", new Object[] { attach.getId(), e.toString() });
		}

		return EVAL_PAGE;
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
