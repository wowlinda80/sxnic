package net.sxnic.comm.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串截取Tag，一般用JSTL代替
 * @author 孙宇飞
 *
 */
public class SxnicFunctionStringSplit extends BodyTagSupport {

	private static final long serialVersionUID = -5869860883333312097L;

	private static Logger logger = LoggerFactory
			.getLogger(SxnicFunctionStringSplit.class);

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
