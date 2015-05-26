package net.sxnic.comm.basecode.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delete extends BaseCodeAction {

	private static final long serialVersionUID = -3008240868443537008L;

	private static final Logger logger = LoggerFactory.getLogger(Delete.class);

	public String execute() {

		try {

			if ((id == null) || ("".equals(id))) {
				//addActionMessage("请指定要删除条目的ID");
				return INPUT;
			}

			basecodeManager.deleteById(id);

			msg = "deleteSuccess";

			// log
			logger.debug("delete basecode success. id = " + id);
			addLog(Log.LOG_OPERATION_DLETE, request.getSession().getAttribute(
					CommConstant.APPCONTEXT_USERNAME)
					+ " delete basecode success! id =" + id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
