package net.sxnic.comm.hsz.action;

@SuppressWarnings("serial")
public class Delete extends HszAction {

	public String execute() {

		beanManager.recover(id);

		// 日志
		addLog("===从回收站恢复数据成功===" + id);

		return SUCCESS;
	}

}
