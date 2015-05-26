package net.sxnic.comm.attachment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.attachment.dao.AttachmentDao;

@Service("AttachmentManager")
public class DefaultAttachmentManager extends
		DefaultManager<Attachment, AttachmentDao, String> implements
		AttachmentManager {

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	protected Dao<Attachment, String> getEntityDao() {
		return attachmentDao;
	}


	public List<Attachment> findByEntityId(String entityId) {		
		return attachmentDao.findByEntityId(entityId);
	}


	public void replaceEntityId(String oldEntityId, String newEntityId) {
		attachmentDao.replaceEntityId(oldEntityId, newEntityId);		
	}


	@Override
	public List<Attachment> findByEntityIdAndType(String entityId,
			String type) {
		return attachmentDao.findByEntityIdAndType(entityId, type);
	}
	
 


}
