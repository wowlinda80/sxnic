package net.sxnic.comm.intercepter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 调用顶层action 的addMsg(String msg) 的方法，用来在不同action之间传递参数，尤其是提示信息
 * 
 * @author 孙宇飞 create date : 2009-4-5
 */
public class MsgInterceptor extends AbstractInterceptor implements Interceptor {

	private static Log log = LogFactory.getLog(MsgInterceptor.class);

	private static final long serialVersionUID = -7241511708436927933L;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = request.getParameter("msg");
		
		if (StringUtils.isNotBlank(msg)) {
			try {
				Method method = invocation.getAction().getClass().getMethod(
						"addMsg","String".getClass());
				method.setAccessible(true);
				
				method.invoke(invocation.getAction(),new Object[]{msg}
						);
			} catch (RuntimeException e) {
				log.error(e.toString());
			}
		}

		return invocation.invoke();
	}

}
