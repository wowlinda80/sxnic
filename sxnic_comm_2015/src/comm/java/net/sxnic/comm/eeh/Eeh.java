package net.sxnic.comm.eeh;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 实体类的修改记录表
 * @author 孙宇飞
 *
 */
@Entity
@Table(name = "comm_entity_edit_history")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Eeh extends AbstractEntity{

	private static final long serialVersionUID = 1885815200558826458L;
	
	private String entityId;
	
	private String entityName;
	
	private String editor;
	
	private String editContent;
	
	public Eeh() {
		super();
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditContent() {
		return editContent;
	}

	public void setEditContent(String editContent) {
		this.editContent = editContent;
	}
	
	

}
