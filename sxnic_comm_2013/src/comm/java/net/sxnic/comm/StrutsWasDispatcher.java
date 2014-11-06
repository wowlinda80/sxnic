package net.sxnic.comm;

import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.struts2.dispatcher.Dispatcher;
/**
 * 
 * Comments: websphere6.1 struts <p>
 * Author：孙宇飞<p> 
 * Create Date：2011-9-15<p>
 *
 *
 */
@SuppressWarnings("serial")
public class StrutsWasDispatcher extends HttpServlet implements Servlet {

	public StrutsWasDispatcher() {
		super();
	}

	public void init(ServletConfig arg0) throws ServletException {

		// this works around a bug in the websphere classloader.
		super.init(arg0);
		Dispatcher d = new Dispatcher(getServletContext(),
				new HashMap<String, String>());
		
	}

}
