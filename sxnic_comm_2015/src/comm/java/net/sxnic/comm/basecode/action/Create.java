package net.sxnic.comm.basecode.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.util.BaseCodeImportUtils;
import net.sxnic.comm.log.Log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Create extends BaseCodeAction {

	private static Logger logger = LoggerFactory.getLogger(Create.class);
	
	private String txtMuti;

	public String execute() throws Exception {

		try {
			if(StringUtils.isBlank(txtMuti)){
				basecodeManager.save(basecode);
			}else{
				//批量导入
				String[] bcs = StringUtils.split(txtMuti);
				BaseCodeImportUtils.importBaseCode(basecodeManager, bcs);
			}			

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
