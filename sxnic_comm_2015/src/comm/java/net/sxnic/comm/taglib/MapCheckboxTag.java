package net.sxnic.comm.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 用来直接生成Html的select Html代码
 * 
 * @author 孙宇飞 create date : 2011-4-18
 */
public class MapCheckboxTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// 类别编码
	private Map<String,String> map;
	// 在jsp面上显示的名字
	private String name;
	// 预设值 用于回显
	private String value;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		
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

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
