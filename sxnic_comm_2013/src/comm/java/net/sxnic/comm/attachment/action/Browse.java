package net.sxnic.comm.attachment.action;

 
/**
 *  
 *
 * @version $Revision$
 * @author 吕盛槐  2011-10-05
 */
import net.sxinfo.core.dao.Page;
import net.sxinfo.core.util.WebUtils;
import net.sxnic.comm.utils.PropertyUtil;

@SuppressWarnings("serial")
public class Browse extends AttachAction {

	private Page attachments;

	public String execute() throws Exception {
		
		 
		
		
		try {						
			int page = WebUtils.getPageNumber(request);
			int pageSize = PropertyUtil.findTableSize("firstbrowse.tabsize");
			attachments = attachmentManager.getPage(page, pageSize, "creationDate", false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 
		return SUCCESS;
	}

	public Page getAttachments() {
		return attachments;
	}

	public void setAttachments(Page attachments) {
		this.attachments = attachments;
	}

	 
}