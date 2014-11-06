package net.sxnic.comm.basecode.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.basecode.BaseCodeUtils;

/**
 * 用来直接生成Html的select Html代码
 * 
 * @author 孙宇飞 create date : 2009-3-15
 */
public class BaseCodeHtmlTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// 类别编码
	private String sortCode;
	// 对应的css样式
	private String css;
	// onchange事件
	private String onChange;
	//在jsp面上显示的名字
	private String name;
	//预设值 用于回显
	private String value;	
	// 默认第一个的key值
	private String headerKey;
	// 默认第一个的value值
	private String headerValue;
	
	private String childId;
	
	private String childDataPath;
	//是否自动适应宽度
	private String autoWidth;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print(BaseCodeUtils.createSelectHtml(name, sortCode, css,
					onChange,value,headerKey,headerValue, childId, childDataPath, autoWidth));

		} catch (IOException e) {

		}

		return EVAL_PAGE;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
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

	public String getHeaderKey() {
		return headerKey;
	}

	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getChildDataPath() {
		return childDataPath;
	}

	public void setChildDataPath(String childDataPath) {
		this.childDataPath = childDataPath;
	}

	public String getAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(String autoWidth) {
		this.autoWidth = autoWidth;
	}

}
