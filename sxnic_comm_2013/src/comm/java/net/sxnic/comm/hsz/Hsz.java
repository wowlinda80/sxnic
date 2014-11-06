package net.sxnic.comm.hsz;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @Description: Hsz.java 回收站
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2014年6月6日
 */
@Entity
@Table(name = "comm_hsz")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hsz extends AbstractEntity {

	private static final long serialVersionUID = 7595373987688476636L;
	
	/**
	 *  实体ID
	 */
	private String entityId;
	
	/**
	 * 实体ID类型 默认0=String型，1=数字型
	 */
	private int entityIdCate;
	
	/**
	 * 标题或者说明
	 */
	private String title;
	
	/**
	 * 实体名，非数据库表名！
	 */
	private String entityName;
	
	
	/**
	 * 操作员
	 */
	private String optor;
	
	/**
	 * 备注
	 */
	private String memo;

	public Hsz() {
		super();
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public int getEntityIdCate() {
		return entityIdCate;
	}

	public void setEntityIdCate(int entityIdCate) {
		this.entityIdCate = entityIdCate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getOptor() {
		return optor;
	}

	public void setOptor(String optor) {
		this.optor = optor;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
