package net.sxnic.comm.attachment.action;
/**
 *  
 *
 * @version $Revision$
 * @author 吕盛槐  2011-10-05
 */
 
import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.attachment.Attachment;
import net.sxnic.comm.attachment.AttachmentManager;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class AttachAction extends CommActionSupport{

	protected Attachment attachment ;
	
	@Autowired
	protected AttachmentManager attachmentManager;

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}

	 

}
