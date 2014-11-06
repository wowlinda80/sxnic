package net.sxnic.comm.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 页面EL  如果在限制内字符串不显示tip，如果超过字数则显示tip
 * @author 孙宇飞
 *
 */
public class SxnicFunctionStringSplitTip extends BodyTagSupport {

	private static final long serialVersionUID = -7531610337624371333L;

	private static Logger logger = LoggerFactory
			.getLogger(SxnicFunctionStringSplitTip.class);

	private String str;

	private int length;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {
			if(length == 0 || StringUtils.isBlank(str)){
				out.print(str);
			}
			
			if(str.length()>length){
				out.print(StringUtils.left(str, length)+"...");
			}else{
				out.print(str);
			}
			
		} catch (Exception e) {
			logger.error(""+e.toString());
		}

		return EVAL_PAGE;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
