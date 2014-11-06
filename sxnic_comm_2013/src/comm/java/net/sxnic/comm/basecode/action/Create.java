package net.sxnic.comm.basecode.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Create extends BaseCodeAction {

	private static Logger logger = LoggerFactory.getLogger(Create.class);

	public String execute() throws Exception {

		try {

			basecodeManager.save(basecode);

			// 重建baseCodeMap
			CommConstant.BASECODE_MAP = basecodeManager.init();

			msg = "createSuccess";

			// log
			logger.debug("创建basecode成功！id:" + basecode.getId());
			addLog(Log.LOG_OPERATION_CREATE, request.getSession().getAttribute(
					CommConstant.APPCONTEXT_USERNAME)
					+ " add basecode success! id=" + basecode.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

}
