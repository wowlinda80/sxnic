package net.sxnic.comm.attachment.dao;

import java.util.List;

import net.sxinfo.core.spring25.Dao;
import net.sxnic.comm.attachment.Attachment;

public interface AttachmentDao extends Dao<Attachment, String> {

	/**
	 * 根据EntityId查询附件列表，按排序字段排序
	 * @param entityId 相关实体类ID
	 * @return 附件列表
	 */
	List<Attachment> findByEntityId(String entityId);

	/**
	 * 置换entityId，有些时候，在主表Id未确定时先弄一个临时Id作为关联之用。一般用UUID即可。
	 * @param oldEntityId 原有Id
	 * @param newEntityId 新Id
	 */
	void replaceEntityId(String oldEntityId, String newEntityId);

	/**
	 * 根据EntityId、附件类别查询附件列表，按排序字段排序
	 * @param entityId 相关实体类ID
	 * @param type 附件类型
	 * @return 附件列表
	 */
	List<Attachment> findByEntityIdAndType(String entityId,
			String type);
	
	 

}
