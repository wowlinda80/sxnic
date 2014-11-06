package net.sxinfo.core.test.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import net.sxinfo.core.entity.AbstractEntity;

@Entity
@Table(name = "test_maindata")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestData extends AbstractEntity{
	
	private String str1;
	
	private String str2;
	
	@ManyToOne
	private TestUser user;

	public TestData() {
		super();
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public TestUser getUser() {
		return user;
	}

	public void setUser(TestUser user) {
		this.user = user;
	}
	
	

}
