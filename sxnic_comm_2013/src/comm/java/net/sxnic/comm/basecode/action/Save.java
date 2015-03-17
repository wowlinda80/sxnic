package net.sxnic.comm.basecode.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.BaseCode;
import net.sxnic.comm.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class Save extends BaseCodeAction implements Preparable {

	private static Logger logger = LoggerFactory.getLogger(Save.class);

	private BaseCode basecode;

	private String id;

	public void prepare() throws Exception {
		basecode = basecodeManager.get(id);

	}

	public String execute() throws Exception {

		try {
			basecodeManager.update(basecode);

			// 重建baseCodeMap
			basecodeManager.init();

			msg = "saveSuccess";

			// log
			logger.debug("保存basecode成功！id:" + basecode.getId());
			addLog(Log.LOG_OPERATION_UPDATE, request.getSession().getAttribute(CommConstant.APPCONTEXT_USERNAME)
					+ " update basecode success! id=" + id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public BaseCode getBasecode() {
		return basecode;
	}

	public void setBasecode(BaseCode basecode) {
		this.basecode = basecode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
