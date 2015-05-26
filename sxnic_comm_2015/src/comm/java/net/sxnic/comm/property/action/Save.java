package net.sxnic.comm.property.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Save extends PropertyAction {

	private static Logger logger = LoggerFactory.getLogger(Save.class);

	public void prepare() throws Exception {
		property = propertyManager.get(id);
	}

	public String execute() throws Exception {
		try {
			propertyManager.update(property);

			// 重建propertyMap
			CommConstant.PROPERTY_MAP = propertyManager.init();

			msg = "saveSuccess";

			// log
			logger.debug("update property success. id = " + id);
			addLog(Log.LOG_OPERATION_UPDATE, request.getSession().getAttribute(
					CommConstant.APPCONTEXT_USERNAME)
					+ " update property success! id=" + id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

}
