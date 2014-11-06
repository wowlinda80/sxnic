package net.sxinfo.core.test.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import net.sxinfo.core.entity.AbstractEntity;

@Entity
@Table(name = "test_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestUser extends AbstractEntity{
	
	private String userName;
	
	private String fullName;

	public TestUser() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

}
