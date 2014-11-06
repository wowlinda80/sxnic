/*
 * $RCSfile: BaseAction.java,v $

 * $Revision: 1.1  $
 * $Date: 2009-1-29  $
 *
 * Copyright (C) 2005 J2X, Inc. All rights reserved.
 *
 * This software is the proprietary information of J2X, Inc.
 * Use is subject to license terms.
 */
package net.sxnic.comm.action;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import javax.mail.internet.MimeUtility;

/**
 * <p>
 * Title: BaseAction
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * @author chenyankui
 * @version 1.0
 * @param <T>
 */
@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport implements Preparable {
	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";
	public static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";
	public static final String CONTENT_TYPE_TEXT = "text/plain; charset=UTF-8";
	public static final String CONTENT_TYPE_JSON = "text/plain; charset=UTF-8";
	public static final String CONTENT_TYPE_DOWNLOAD = "application/x-msdownload; charset=UTF-8";
	public static final String CONTENT_TYPE_FLASH = "application/x-shockwave-flash; charset=UTF-8";

	private static final Log log = LogFactory.getLog(BaseAction.class);
	private static final boolean DEBUG = log.isDebugEnabled();
	
	/** ajax */
	private Boolean ajax = false;
	/**返回地址*/
	private String backurl;
	
	public String getBackurl() {
		if(StringUtils.isEmpty(this.backurl)){
			this.backurl=String.format("list.action?ajax=%s&useLastFilter=true", ajax);
		}
		return backurl;
	}
	
	public String getHrefBackurl(){
		if (this.getBackurl().startsWith("/")){
			return this.getRequest().getContextPath()+this.getBackurl();
		}
		return this.getBackurl();
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}
	
	public Boolean getAjax() {
		return ajax;
	}

	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
	}
	
	

	/**
	 * @return com.opensymphony.xwork2.ActionSupport.NONE
	 */
	public String nothing() {
		return NONE;
	}

	public String success() {
		return SUCCESS;
	}

	/**
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		if (DEBUG) {
			log.debug("Enter");
			Runtime r = java.lang.Runtime.getRuntime();
			log.debug("Free:        " + (r.freeMemory() / 1024) + " KB");
			log.debug("MaxMemory:   " + (r.maxMemory() / 1024) + " KB");
			log.debug("TotalMemory: " + (r.totalMemory() / 1024) + " KB");
		}
	}

	/**
	 * @return
	 * @author chenyankui
	 */
	public ServletContext getServletContext() {
		// ServletContext servletContext =
		// (ServletContext)ActionContext.getContext().get(StrutsStatics.SERVLET_CONTEXT);
		// ServletContext servletContext =
		// (ServletContext)ActionContext.getContext().get(ServletActionContext.SERVLET_CONTEXT);
		return ServletActionContext.getServletContext();
	}

	/**
	 * @return
	 * @author chenyankui
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * @return
	 * @author chenyankui
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * @return
	 * @author chenyankui
	 */
	public String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	/**
	 * @return
	 * @author chenyankui
	 */
	public String getContentType() {
		return ServletActionContext.getRequest().getContentType();
	}

	/**
	 * @return
	 * @author chenyankui
	 */
	public int getContentLength() {
		return ServletActionContext.getRequest().getContentLength();
	}

	/**
	 * @param key
	 * @return
	 * @author chenyankui
	 */
	public String getParameter(String name) {
		String value = ServletActionContext.getRequest().getParameter(name);

		return (value != null ? value : "");
	}

	/**
	 * @param key
	 * @return
	 * @author chenyankui
	 */
	public String getParameter(String name, String defaultValue) {
		String value = ServletActionContext.getRequest().getParameter(name);

		return (value != null && value.trim().length() > 0 ? value.trim()
				: defaultValue);
	}

	/**
	 * @param name
	 * @return
	 * @author chenyankui
	 */
	public String[] getParameterValues(String name) {
		return ServletActionContext.getRequest().getParameterValues(name);
	}


	/**
	 * @param key
	 * @return
	 * @author chenyankui
	 */
	public Object getAttribute(String key) {
		return ServletActionContext.getRequest().getAttribute(key);
	}

	/**
	 * @param key
	 * @param value
	 * @author chenyankui
	 */
	public void setAttribute(String key, Object value) {
		ServletActionContext.getRequest().setAttribute(key, value);
	}

	/**
	 * @param text
	 * @param contentType
	 * @return
	 * @author chenyankui
	 */
	public String write(Object object, String contentType) {
		String s = contentType;

		if (contentType == null || (s = contentType.trim()).length() < 1) {
			s = CONTENT_TYPE_HTML;
		}

		String text = null;

		if (object != null) {
			text = object.toString();
		} else {
			text = StringUtils.EMPTY;
		}

		try {
			HttpServletResponse response = this.getResponse();

			response.setContentType(s);

			response.getWriter().write(text);

			response.flushBuffer();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return Action.NONE;
	}

	/**
	 * @param object
	 * @return
	 * @author chenyankui
	 */
	public String write(Object object) {
		return this.write(object, CONTENT_TYPE_TEXT);
	}

	/**
	 * @param object
	 * @return
	 * @author chenyankui
	 */
	public String writeXml(String xml) {
		return this.write(xml, CONTENT_TYPE_XML);
	}

	/**
	 * @param object
	 * @return
	 * @author chenyankui
	 */
	public String writeHtml(Object object) {
		return this.write(object, CONTENT_TYPE_HTML);
	}

	/**
	 * @param object
	 * @return
	 * @author chenyankui
	 */
	public String writeText(Object object) {
		return this.write(object, CONTENT_TYPE_TEXT);
	}

	/**
	 * @param object
	 * @return
	 * @author chenyankui
	 */
	public String writeJson(Object object) {
		String objectStr = StringUtils.EMPTY;
		try {
			objectStr = JSONUtil.serialize(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.write(objectStr, CONTENT_TYPE_JSON);
	}

	/**
	 * 返回下载的文件内容
	 * 
	 * @param content
	 * @param fileName
	 * @return
	 */
	public String writeFileContent(String content, String fileName) {
		// 转换为下载文件名
		fileName = convertDownloadFileName(fileName);
		// 设置文件名
		HttpServletResponse response = this.getResponse();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		return this.write(content, CONTENT_TYPE_DOWNLOAD);
	}

	/**
	 * 转换下载显示的文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public String convertDownloadFileName(String fileName) {
		String agent = this.getRequest().getHeader("USER-AGENT");
		// IE采用url编码
		if (!StringUtils.isEmpty(agent) && -1 != agent.indexOf("MSIE")) {
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	/**
	 * writeJson("result",0,"message","您输入有误");
	 * writeJson("result",1,"message","您输入有误");
	 * 
	 * @param object
	 * @return
	 */
	public String writeJsonKey(Object... object) {
		if (object.length % 2 != 0) {
			throw new RuntimeException("writeJson(Object... object)参数需要是2的倍数 ");
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < object.length / 2; i++) {
			map.put(object[2 * i], object[2 * i + 1]);
		}
		return this.writeJson(map);
	}

	/**
	 * 返回错误信息
	 * 
	 * @param message
	 * @return
	 */
	public String writeJsonError(String message, Object... object) {
		List<Object> list = new ArrayList<Object>(Arrays.asList(object));
		list.add(0, "result");
		list.add(1, 0);
		list.add(2, "message");
		list.add(3, message);
		return this.writeJsonKey(list.toArray());
	}

	/**
	 * 返回成功信息
	 * 
	 * @param object
	 * @return
	 */
	public String writeJsonSuccess(Object... object) {
		List<Object> list = new ArrayList<Object>(Arrays.asList(object));
		list.add(0, "result");
		list.add(1, 1);
		return this.writeJsonKey(list.toArray());
	}

	/**
	 * 写文件不带Cache
	 * @param file
	 * @return
	 */
	public String writeFile(File file, String fileName, Boolean isOnLine){
		//清理 response
		this.getResponse().reset();
		return writeFileCommon(file, fileName, isOnLine);
	}
	/**
	 * 通用的文件下载
	 * @param file
	 * @param fileName
	 * @param isOnLine
	 * @return
	 */
	public String writeFileCommon(File file, String fileName, Boolean isOnLine) {
		fileName = StringUtils.isEmpty(fileName) ? file.getName() : fileName;
		// 转换为下载文件名
		fileName = convertDownloadFileName(fileName);
		try {
			HttpServletResponse response = this.getResponse();
			response.setHeader("p3p", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
			FileInputStream stream = new FileInputStream(file);
			try {
				BufferedInputStream br = new BufferedInputStream(stream);
				byte[] buf = new byte[br.available() > 102400 ? 102400 : br
						.available()];// 1024*100
				int len = 0;
				// 下载文件
				if (isOnLine) { // 在线打开方式
					URL u = file.toURI().toURL();
					response.setContentType(u.openConnection().getContentType());
					response.setHeader("Content-Disposition",
							"inline; filename=\"" + fileName + "\"");
					// 文件名应该编码成UTF-8
				} else if(file.getName().endsWith(".swf")) {
					response.setContentType("application/x-shockwave-flash");
					response.setHeader("Content-Disposition",
							"inline; filename=\"" + fileName + "\"");
				}else{
					// 纯下载方式
					response.setContentType("application/x-msdownload");
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + fileName + "\"");
				}				
				response.setHeader("Content-Length", Integer.toString(br.available()));					
				OutputStream out = response.getOutputStream();
				while ((len = br.read(buf)) > 0) {
					out.write(buf, 0, len);
					out.flush();
				}
				br.close();
				out.close();
			} finally {
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return Action.NONE;
	}
}
