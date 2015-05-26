package net.sxnic.comm.hsz.action;

import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.hsz.Hsz;
import net.sxnic.comm.hsz.HszManager;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class HszAction extends CommActionSupport {

	protected Hsz bean;

	@Autowired
	protected HszManager beanManager;

	public Hsz getBean() {
		return bean;
	}

	public void setBean(Hsz bean) {
		this.bean = bean;
	}

}
