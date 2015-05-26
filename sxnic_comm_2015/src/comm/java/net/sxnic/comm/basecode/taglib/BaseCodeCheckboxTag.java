package net.sxnic.comm.basecode.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.CommConstant;

import org.apache.commons.lang.StringUtils;

/**
 * 用来直接生成Html的select Html代码
 * 
 * @author 孙宇飞 create date : 2011-4-18
 */
public class BaseCodeCheckboxTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * 类别码
	 */
	private String sortCode;
	/**
	 * html的标签的Name
	 */
	private String name;
	/**
	 * 浏览器显示的值
	 */
	private String value;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		Map<String, String> map = CommConstant.BASECODE_MAP.get(sortCode);
		try {

			if (map == null || map.size() <= 0) {
				out.print("null map");
				return EVAL_PAGE;
			}

			if (StringUtils.isBlank(name)) {
				out.print("error select name");
				return EVAL_PAGE;
			}
		 
			StringBuffer sb = new StringBuffer();
			for (String code : map.keySet()) {
				if (StringUtils.contains(value, code)) {
					sb.append("<input type='checkbox' name='" + name
							+ "' value='" + code + "' checked='true' />&nbsp;"
							+ map.get(code) + "&nbsp;");
				} else {
					sb.append("<input type='checkbox' name='" + name
							+ "' value='" + code + "' />&nbsp;" + map.get(code)
							+ "&nbsp;");
				}
			}
			 

			out.print(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
