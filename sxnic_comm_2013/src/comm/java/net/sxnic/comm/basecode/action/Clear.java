package net.sxnic.comm.basecode.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Clear extends BaseCodeAction {

	private static final long serialVersionUID = 6336621084939277825L;

	private static final Log log = LogFactory.getLog(Clear.class);

	public String execute() throws Exception {

		basecodeManager.clear();

		log.debug("Table date of basecode has been cleared!");

		return SUCCESS;
	}

}
