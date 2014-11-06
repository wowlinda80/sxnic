package net.sxnic.comm.attachment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.attachment.Attachment;

@Repository("attachmentDao")
public class HibernateAttachmentDao extends HibernateDao<Attachment, String>
		implements AttachmentDao {

	public List<Attachment> findByEntityId(String entityId) {
		return find("from Attachment where entityId = ? order by aOrder",
				new Object[] { entityId });
	}

	public void replaceEntityId(String oldEntityId, String newEntityId) {
		getCurrSession().createQuery(
				"update Attachment set entityId = '" + newEntityId
						+ ", where entityId='" + oldEntityId + "'")
				.executeUpdate();
	}

	@Override
	public List<Attachment> findByEntityIdAndType(String entityId, String type) {

		return find("from Attachment where entityId = ? and type= ?",
				new Object[] { entityId, type });
	}
}
