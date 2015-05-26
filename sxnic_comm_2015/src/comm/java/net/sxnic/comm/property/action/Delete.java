package net.sxnic.comm.property.action;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delete extends PropertyAction {

	private static final long serialVersionUID = -3008240868443537008L;

	private static final Logger logger = LoggerFactory.getLogger(Delete.class);

	private String id;
	

	public String execute()throws Exception {
		
		try {						
		
			if ((id == null) || ("".equals(id))) {
				//addActionMessage("请指定要删除条目的ID");
				return INPUT;
			}
		
			propertyManager.deleteByIds(new String[] { id });
		
			msg = "deleteSuccess";
		
			//log
			logger.debug("delete property success. id = " + id);
			addLog(Log.LOG_OPERATION_DLETE, request.getSession().getAttribute(
					CommConstant.APPCONTEXT_USERNAME)
					+ " delete property success! id=" + id);	 
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		 


		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
