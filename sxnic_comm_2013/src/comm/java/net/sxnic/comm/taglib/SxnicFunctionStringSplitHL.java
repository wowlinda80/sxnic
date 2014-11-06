package net.sxnic.comm.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockrunner.util.common.StringUtil;

/**
 * 截取字符串，并高亮显示某关键字，关键字可以用分号分隔，一般用JSTL代替
 * @author 孙宇飞
 *
 */
public class SxnicFunctionStringSplitHL extends BodyTagSupport {

	private static final long serialVersionUID = -5869860883333312097L;

	private static Logger logger = LoggerFactory
			.getLogger(SxnicFunctionStringSplitHL.class);

	private String str;

	private String key;
	
	private int length;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {
			if(StringUtils.isBlank(str)){
				out.print("");
				return EVAL_PAGE;
			}
			
			if(StringUtils.isNotBlank(key)){
				String[] keys= StringUtils.split(key, ",");
				for(String k:keys){
					str = StringUtil.replaceAll(str, k, "<font color='red'>"+k+"</font>");
				}
			}
			
			if(length == 0 || StringUtils.isNotBlank(str)){
				out.print(str);
				return EVAL_PAGE;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
