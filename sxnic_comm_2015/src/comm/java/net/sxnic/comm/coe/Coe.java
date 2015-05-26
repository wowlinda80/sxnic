package net.sxnic.comm.coe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * <p>仿造Hibernate中的collectionofelment,就是构建一个基本的、通用的子类，其他所有业务实体类都可以做此类父类。
 * <P>用法1：业务实体类中，如果只需要key和value的子类，就可以用此类
 * @author 孙宇飞
 * @version v1.0.0
 * @creationDate 2012-7-23
 * @moidfyRecords
 */
@Entity
@Table(name = "comm_collection_of_elment")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Coe extends AbstractEntity{
	
	private static final long serialVersionUID = -385493208107736606L;

	@Column(name="key_")
	private String key;
	
	@Column(name="value_")
	private String value;	

	public Coe() {
		super();
	}

	public Coe(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coe other = (Coe) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
