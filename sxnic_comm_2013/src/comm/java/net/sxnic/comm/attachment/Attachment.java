package net.sxnic.comm.attachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * Comments: 附件实体类
 * <p>
 * Author：孙宇飞
 * <p>
 * Create Date：2009-9-1
 * <p>
 */
@Entity
@Table(name = "comm_attachment")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment extends AbstractEntity {

	private static final long serialVersionUID = -1657803623447014569L;
	
	/**
	 * 类型 普通类型
	 */
	public static final String TYPE_NORMAL="a";
	
	/**
	 * 图片类型
	 */
	public static final String TYPE_IMAGE="i";
	
	/**
	 * word类型
	 */
	public static final String TYPE_DOC="d";
	
	/**
	 * 原来的文件名 包括后缀名
	 */
	private String oriName;
	
	/**
	 * 完整的物理路径（包括文件名及后缀，不包括基础路径）
	 */
	private String filePath;
	
	/**
	 * 后缀
	 */
	@Column(name="suffix_")
	private String suffix;

	
	/**
	 * 文件大小
	 */
	@Column(name="size_")
	private Double size;
	
	/**
	 * 排序
	 */
	@Column(name="rank_")
	private int rank;
	
	/**
	 * 类型
	 */
	@Column(name="type_")
	private String type;
	
	//关联实体ID
	private String entityId;

	public Attachment() {
		super();
	}


	public String getOriName() {
	    return oriName;
	}


	public void setOriName(String oriName) {
	    this.oriName = oriName;
	}


	public String getFilePath() {
	    return filePath;
	}


	public void setFilePath(String filePath) {
	    this.filePath = filePath;
	}


	public String getSuffix() {
	    return suffix;
	}


	public void setSuffix(String suffix) {
	    this.suffix = suffix;
	}


	public Double getSize() {
	    return size;
	}


	public void setSize(Double size) {
	    this.size = size;
	}




	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}

	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	public String getEntityId() {
		return entityId;
	}


	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	
	

}
