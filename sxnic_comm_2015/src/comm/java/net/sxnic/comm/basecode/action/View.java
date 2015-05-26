package net.sxnic.comm.basecode.action;


@SuppressWarnings("serial")
public class View extends BaseCodeAction {


	private String id;

	public String execute() throws Exception {
		basecode = basecodeManager.getById(id);
		return SUCCESS;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
