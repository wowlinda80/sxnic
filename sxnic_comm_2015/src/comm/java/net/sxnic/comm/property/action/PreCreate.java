package net.sxnic.comm.property.action;

import net.sxnic.comm.property.Property;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PreCreate extends PropertyAction {

	private static Log log = LogFactory.getLog(PreCreate.class);
	

	public String execute() throws Exception {
		property = new Property();
		return SUCCESS;
	}

}
