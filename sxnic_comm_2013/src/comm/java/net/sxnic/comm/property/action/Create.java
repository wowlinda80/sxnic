package net.sxnic.comm.property.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public class Create extends PropertyAction {
	
	private static Logger logger = LoggerFactory.getLogger(Create.class);
	

	public String execute() throws Exception {
		try {						
			propertyManager.create(property);
			
			//重建propertyMap
			CommConstant.PROPERTY_MAP = propertyManager.init();
			
			msg="createSuccess";
		
			// log
			logger.debug("创建property成功！id:" + property.getId());
			addLog(Log.LOG_OPERATION_CREATE, request.getSession().getAttribute(
					CommConstant.APPCONTEXT_USERNAME)
					+ " add property success! id=" + property.getId());	 
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}

}
