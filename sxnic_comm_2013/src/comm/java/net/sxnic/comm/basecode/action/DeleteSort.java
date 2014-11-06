package net.sxnic.comm.basecode.action;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DeleteSort extends BaseCodeAction {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(DeleteSort.class);

	public String execute() {

		if (StringUtils.isBlank(sortCode) || "00".equals(sortCode)) {

			this.addActionMessage("删除类别为空，或者类别错误!");

			return INPUT;
		}

		basecodeManager.deleteSortBySortCode(sortCode);

		log.debug("delete sort success,sortCode=" + sortCode);

		sortCode = "00";

		this.addActionMessage("删除类别：" + sortCode + "成功");

		return SUCCESS;
	}

}
