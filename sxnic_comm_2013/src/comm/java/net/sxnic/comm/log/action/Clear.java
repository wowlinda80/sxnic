package net.sxnic.comm.log.action;


/**
 *  
 *
 * @version $Revision$
 * @author 吕盛槐  2011-10-05
 */
@SuppressWarnings("serial")
public class Clear extends LogAction {

	public String execute() throws Exception {
		
		 try {						
			    
			 logManager.clear();
				
			 msg = "deleteSuccess";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return SUCCESS;
	}
}
