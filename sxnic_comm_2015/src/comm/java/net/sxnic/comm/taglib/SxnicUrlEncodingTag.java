package net.sxnic.comm.taglib;

import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码URL Tag类，未完成
 * @author 孙宇飞
 *
 */
@SuppressWarnings("serial")
public class SxnicUrlEncodingTag extends BodyTagSupport {

	private static Logger logger = LoggerFactory.getLogger(SxnicUrlEncodingTag.class);
	
	private String url;
	
	private String encoding;

	public int doStartTag() throws JspException {
		
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		
		try {
			JspWriter out = pageContext.getOut();
			
			if(StringUtils.isNotBlank(encoding)){
				encoding = "utf-8";
			}			
			
			out.print(URLEncoder.encode(url,encoding));
		} catch (Exception e) {
			logger.error("===url encode error ==url=={}==e==",url,e.toString());
		}
				
		return EVAL_PAGE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
