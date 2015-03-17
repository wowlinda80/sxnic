package net.sxnic.comm.basecode.action;

import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.basecode.BaseCode;
import net.sxnic.comm.basecode.BaseCodeManager;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public abstract class BaseCodeAction extends CommActionSupport {

	protected BaseCode basecode;
	
	protected String cyear;

	@Autowired
	protected BaseCodeManager basecodeManager;
	
	protected String sortCode;

	public BaseCodeManager getBasecodeManager() {
		return basecodeManager;
	}

	public void setBasecodeManager(BaseCodeManager basecodeManager) {
		this.basecodeManager = basecodeManager;
	}

	public BaseCode getBasecode() {
		return basecode;
	}

	public void setBasecode(BaseCode basecode) {
		this.basecode = basecode;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getCyear() {
		return cyear;
	}

	public void setCyear(String cyear) {
		this.cyear = cyear;
	}
	
	
	
}
