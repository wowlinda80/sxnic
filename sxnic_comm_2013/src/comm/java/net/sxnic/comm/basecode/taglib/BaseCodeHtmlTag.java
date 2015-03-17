package net.sxnic.comm.basecode.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.CommConstant;

import org.apache.commons.lang3.StringUtils;

/**
 * 用来直接生成Html的select Html代码
 * 
 * @author 孙宇飞 create date : 2009-3-15
 */
public class BaseCodeHtmlTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// 类别编码
	private String sortCode;
	private String year;
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
			Map<String, String> map = new LinkedHashMap<String, String>();

			// 20150317 孙宇飞加年份判断
			if (CommConstant.BASECODE_YEAR_MAP.containsKey(sortCode)) {
				if (CommConstant.BASECODE_YEAR_MAP.get(sortCode).containsKey(year)) {
					map = CommConstant.BASECODE_YEAR_MAP.get(sortCode).get(year);
				} else {
					map = CommConstant.BASECODE_YEAR_MAP.get(sortCode).get("Y");
				}
			} else {
				out.print("error sortCode");
				return EVAL_PAGE;
			}

			if (map == null) {
				out.print("error map");
				return EVAL_PAGE;
			}

			if (StringUtils.isBlank(name)) {
				out.print("error name");
				return EVAL_PAGE;
			}

			if (StringUtils.isBlank(sortCode)) {
				out.print("error sortCode");
				return EVAL_PAGE;
			}

			if (StringUtils.isBlank(css)) {
				css = "default";
			}

			StringBuffer sb = new StringBuffer();
			sb.append("<select id='" + name + "_id' name='" + name + "'");

			if (StringUtils.isNotBlank(onChange)) {
				sb.append(" onchange=\"" + onChange + "\" ");
			}

			if (StringUtils.isNotBlank(css)) {
				sb.append(" class='" + css + "' ");
			}

			if (StringUtils.isNotBlank(childId)) {
				sb.append(" childId='" + childId + "' ");
			}

			if (StringUtils.isNotBlank(childDataPath)) {
				sb.append(" childDataPath='" + childDataPath + "' ");
			}

			if (StringUtils.isNotBlank(autoWidth)) {
				sb.append(" autoWidth='" + autoWidth + "' ");
			}

			sb.append(" >");

			if (StringUtils.isNotBlank(headerKey) || StringUtils.isNotBlank(headerValue)) {
				sb.append("  <option value='" + headerKey + "' selected='selected'>" + headerValue + "</option>");
			}

			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String infoCode = (String) iter.next();
				if (StringUtils.isNotBlank(value) && infoCode.equals(value)) {
					sb.append("<option value='" + infoCode + "'  selected >" + map.get(infoCode) + "</option>");
				} else {
					sb.append("<option value='" + infoCode + "'>" + map.get(infoCode) + "</option>");
				}
			}

			sb.append("</select>");
			
			out.print(sb.toString());

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
