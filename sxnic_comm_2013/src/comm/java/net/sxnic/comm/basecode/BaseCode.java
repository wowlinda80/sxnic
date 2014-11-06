package net.sxnic.comm.basecode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * <p>基本信息表 或者说字典表实体类
 * @author 孙宇飞
 * @version v1.0.0
 * @creationDate: 2012-7-23
 * @moidfyRecords:
 */
@Entity
@Table(name = "comm_basecode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseCode extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3091353026685818737L;

	/**
	 * 分类编码
	 */
	@Column(nullable = false)
	private String sortCode;

	/**
	 * 分类名称
	 */
	@Column(nullable = false)
	private String sortName;

	/**
	 * 信息码
	 */
	@Column(nullable = false)
	private String infoCode;

	/**
	 * 编码信息
	 */
	@Column(nullable = false)
	private String infoName;

	/**
	 * 特殊排序字段一般情况按infoCode排序即可，但是做查询的时候先按infoIndex排序，再按infoCode排
	 */
	private String infoIndex;
	
	/**
	 * 年份
	 */
	private String cyear;

	/**
	 * 构造器
	 */
	public BaseCode() {
		super();
	}
	
	public BaseCode(String sortCode, String sortName, String infoCode,
			String infoName, String order,String cyear) {
		super();
		this.sortCode = sortCode;
		this.sortName = sortName;
		this.infoCode = infoCode;
		this.infoName = infoName;
		this.infoIndex = order;
		this.cyear = cyear;
	}
	
	public BaseCode(String sortCode, String sortName, String infoCode,
			String infoName) {
		super();
		this.sortCode = sortCode;
		this.sortName = sortName;
		this.infoCode = infoCode;
		this.infoName = infoName;
	}

	/**
	 * @return the infoCode
	 */
	public String getInfoCode() {
		return infoCode;
	}

	/**
	 * @return the infoName
	 */
	public String getInfoName() {
		return infoName;
	}

	/**
	 * @return the order
	 */
	public String getInfoIndex() {
		return infoIndex;
	}

	/**
	 * @return the sortCode
	 */
	public String getSortCode() {
		return sortCode;
	}

	/**
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @param infoCode the infoCode to set
	 */
	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}

	/**
	 * @param infoName the infoName to set
	 */
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	/**
	 * @param order the order to set
	 * 
	 */
	public void setInfoIndex(String ordered) {
		this.infoIndex = ordered;
	}

	/**
	 * @param sortCode the sortCode to set
	 */
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	/**
	 * @param sortName the sortName to set
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BaseCode)) {
			return false;
		}
		final BaseCode u = (BaseCode) o;
		return new EqualsBuilder().appendSuper(super.equals(u)).append(id,
				u.getId()).isEquals();
	}

	public String getCyear() {
		return cyear;
	}

	public void setCyear(String cyear) {
		this.cyear = cyear;
	}
}
