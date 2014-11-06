package net.sxnic.comm.property.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.CommConstant;
/**
 * 根据属性表的Key在页面显示Value的值
 * @author 孙宇飞
 *
 */
public class PropertyTag extends BodyTagSupport {

	private static final long serialVersionUID = 2576305984125166739L;

	private String name;
	

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print(CommConstant.PROPERTY_MAP.get(name));

		} catch (Exception e) {
			
		}

		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}