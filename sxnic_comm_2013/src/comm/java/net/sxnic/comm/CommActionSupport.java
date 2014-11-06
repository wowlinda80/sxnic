package net.sxnic.comm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sxnic.comm.log.Log;
import net.sxnic.comm.log.LogManager;
import net.sxnic.comm.log.util.LogUtils;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * <p>基础ActionSupport实现，所有业务Action都应该继承此类.
 * @author 孙宇飞
 * @version v1.0.0
 * @creationDate 2012-7-23
 * @moidfyRecords 修改记录
 */
public class CommActionSupport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1537063104410719867L;

	private static Logger logger = LoggerFactory
			.getLogger(CommActionSupport.class);

	/**
	 * 转入无授权页面
	 */
	protected static String NOTAUTHORIZED = "notAuthorized";
	protected static String EDIT = "edit";
	protected static String VERIFY = "verify";
	protected static String LIST = "list";

	protected HttpServletRequest request;

	protected HttpServletResponse response;
	
	/**
	 * 浏览页面的查询短语
	 */
	protected String txtQuery;

	/**
	 * 本系统内部的日志管理类
	 */
	@Autowired
	protected LogManager logManager;

	/**
	 * 用来action间传递信息用
	 */
	protected String msg;

	protected String id;

	/**
	 * 页面风格定义
	 */
	protected String cssStyle;

	/**
	 * 请求页面的URL,便于返回的时候使用。注意对URL进行编码
	 */
	protected String reqUrl;

	/**
	 * 当前页面的URL，便于其他页面返回此页面时使用。
	 */
	protected String currentUrl;

	/**
	 * 操作前的对象存留
	 */
	protected Object oldObj;

	/**
	 * 页码
	 */
	protected String page;

	/**
	 * 菜单编码
	 */
	protected String menuCode;
	
	/**
	 * 当前位置，与menuCode一起使用
	 */
	protected String currLocation;

	/**
	 * 用于在action之间传递提示信息。此方法主要用于Intercepter 的方法调用
	 * 
	 * @param msg 信息内容的编码，具体定义在属性文件中，比如application.properties文件。
	 */
	public void addMsg(String msg) {

		if (StringUtils.isNotBlank(msg)) {
			// msg = this.getText(msg);
			if (msg.endsWith("Error")) {
				addActionError(getText(msg));
			} else {
				addActionMessage(getText(msg));
			}
		}

		this.msg = null;
	}

	/**
	 * 添加系统日志
	 * 
	 * @param details
	 *            操作内容
	 */
	protected void addLog(String details) {
		if (StringUtils.isBlank(details))
			return;

		Log log = new Log();
		log.setOperation(Log.LOG_OPERATION_UPDATE);
		log.setOperator((String)request.getSession().getAttribute(
				CommConstant.APPCONTEXT_USERNAME));
		log.setIpAddress(CommUtils.findIP(request));
		log.setDetails(details);

		logManager.save(log);

		logger.debug("=====add===log===" + details);
	}

	/**
	 * 添加系统日志
	 * 
	 * @param operation
	 *            操作类型，从Log实体中取静态变量。默认为LOG_OPERATION_UPDATE
	 * @param details
	 *            操作细节，自行组织，尽量具体
	 */
	protected void addLog(String operation, String details) {
		if (StringUtils.isBlank(details)) {
			return;
		}
		Log log = new Log();

		if (StringUtils.isBlank(operation)) {
			log.setOperation(Log.LOG_OPERATION_UPDATE);
		}

		log.setIpAddress(CommUtils.findIP(request));
		log.setOperator((String)request.getSession().getAttribute(
				CommConstant.APPCONTEXT_USERNAME));
		log.setDetails(details);

		logManager.save(log);

		logger.debug("=====add===log===" + details);
	}

	/**
	 * 添加系统日志
	 * 
	 * @param operation
	 *            操作类型，从Log实体中取静态变量。默认为LOG_OPERATION_MODIFY
	 * @param className
	 *            类名
	 * @param oldObj
	 *            如果修改数据，则代表旧有实体类
	 * @param newObj
	 *            修改后的实体类
	 * @throws Exception
	 */
	protected void addLog(String operation, String className, Object oldObj,
			Object newObj) throws Exception {
		Log log = new Log();

		if (StringUtils.isBlank(operation)) {
			log.setOperation(Log.LOG_OPERATION_UPDATE);
		}

		log.setOperator((String)request.getSession().getAttribute(
				CommConstant.APPCONTEXT_USERNAME));
		log.setIpAddress(CommUtils.findIP(request));
		log.setClassName(className);
		log.setOldObj(LogUtils.treatObjecttoBytes(oldObj));
		log.setNewObj(LogUtils.treatObjecttoBytes(newObj));
		log.setDetails(" modify " + className + " from " + oldObj + " to "
				+ newObj);

		logManager.save(log);

		logger.debug("=====add===log===" + log.getDetails());
	}
	
	/**
	 * 添加重要日志，一般不转储
	 * 
	 * @param operation
	 *            操作类型，从Log实体中取静态变量。默认为LOG_OPERATION_MODIFY
	 * @param className
	 *            类名
	 * @param oldObj
	 *            如果修改数据，则代表旧有实体类
	 * @param newObj
	 *            修改后的实体类
	 * @throws Exception
	 */
	protected void addImportantLog(String operation, String className, Object oldObj,
			Object newObj) throws Exception {
		Log log = new Log();
		//设置为重要
		log.setCate(Log.LOG_IMPORTANT_CATE_002);
		
		if (StringUtils.isBlank(operation)) {
			log.setOperation(Log.LOG_OPERATION_UPDATE);
		}

		log.setOperator((String)request.getSession().getAttribute(
				CommConstant.APPCONTEXT_USERNAME));
		log.setIpAddress(CommUtils.findIP(request));
		log.setClassName(className);
		log.setOldObj(LogUtils.treatObjecttoBytes(oldObj));
		log.setNewObj(LogUtils.treatObjecttoBytes(newObj));
		log.setDetails(" modify " + className + " from " + oldObj + " to "
				+ newObj);

		logManager.save(log);

		logger.debug("=====add===log===" + log.getDetails());
	}

	public void setServletRequest(HttpServletRequest req) {
		request = req;
	}

	public void setServletResponse(HttpServletResponse res) {
		response = res;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getCurrLocation() {
		return currLocation;
	}

	public void setCurrLocation(String currLocation) {
		this.currLocation = currLocation;
	}

	public String getTxtQuery() {
		return txtQuery;
	}

	public void setTxtQuery(String txtQuery) {
		this.txtQuery = txtQuery;
	}

	
}
