package net.sxnic.comm.basecode.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import net.sxnic.comm.basecode.BaseCodeUtils;

/**
 * 基本码表的标签类，在jsp页面中用sortCde 和infoCode做参数 直接显示infoName
 * @author 孙宇飞
 * create date : 2009-3-15
 */
public class BaseCodeTag extends BodyTagSupport {

	private static final long serialVersionUID = 2576305984125166739L;

	private String sortCode;
	private String infoCode;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		try {
			if(!StringUtils.contains(infoCode, ",")){
			out.print(BaseCodeUtils.getInfoName(sortCode, infoCode));
			}else{
				String[] infoCodes = StringUtils.split(infoCode, ",");
				String temp ="";
				for(String ic:infoCodes){
					temp = temp+  BaseCodeUtils.getInfoName(sortCode, ic.trim())+",";
				}
				
				out.print(StringUtils.removeEnd(temp, ","));
			}

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

	public String getInfoCode() {
		return infoCode;
	}

	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}
}
