package net.sxnic.comm.taglib;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Comments: 信息类跟随 new图标显示
 * <p>
 * Author：孙宇飞
 * <p>
 * Create Date：2010-9-25
 * <p>
 * 
 * 
 */
@SuppressWarnings("serial")
public class NewTipTag extends BodyTagSupport {

	private static Logger logger = LoggerFactory.getLogger(NewTipTag.class);
	
	private Date publicDate;
	
	private String spublicDate;
	
	private String requestPath;

	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		
		if(StringUtils.isBlank(requestPath)){
			requestPath = CommConstant.SYSTEM_CONTEXTPATH;
		}
		
		try {
			String day_s=CommConstant.PROPERTY_MAP.get("notice.newtip.time");
			if(StringUtils.isBlank(day_s)){
				day_s="5";
			}
			int day = Integer.parseInt(day_s);
			if(publicDate != null){				
				if(CommUtils.calTwoDate(new Date(), publicDate)<=day){
					out.print("<img src='"+requestPath+"/"+CommConstant.PROPERTY_MAP.get("notice.newtip.path")+"' />");
					
				}
				return EVAL_PAGE;
			}else if(StringUtils.isNotBlank(spublicDate)){
				if(CommUtils.calTwoDate(new Date(), CommUtils.FormatStringToDate(spublicDate, "yyyy-MM-dd"))<=day){
					out.print("<img src='"+requestPath+"/"+CommConstant.PROPERTY_MAP.get("notice.newtip.path")+"' />");
				}
				return EVAL_PAGE;
			}else{
				out.print("0");
			}			
			
		} catch (IOException e) {	
			logger.error("===ERROR FILESIZETAG====" + e.toString());
		}

		return EVAL_PAGE;
	}

	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public String getSpublicDate() {
		return spublicDate;
	}

	public void setSpublicDate(String spublicDate) {
		this.spublicDate = spublicDate;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
}
