package net.sxnic.comm.log.action;


import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.log.Log;
import net.sxnic.comm.log.LogManager;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class LogAction extends CommActionSupport{

	protected Log log ;
	
	@Autowired
	protected LogManager logManager;

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}
	

}
