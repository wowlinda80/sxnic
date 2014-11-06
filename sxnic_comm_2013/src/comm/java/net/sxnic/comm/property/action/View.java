package net.sxnic.comm.property.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class View extends PropertyAction {

	private static Log log = LogFactory.getLog(View.class);

	private String id;

	public String execute() throws Exception {
		property = propertyManager.get(id);
		return SUCCESS;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
