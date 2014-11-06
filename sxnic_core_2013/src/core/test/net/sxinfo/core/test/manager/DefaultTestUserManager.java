package net.sxinfo.core.test.manager;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxinfo.core.test.dao.TestUserDao;
import net.sxinfo.core.test.entity.TestUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userManager")
public class DefaultTestUserManager extends
		DefaultManager<TestUser, TestUserDao, String> implements
		TestUserManager {

	@Autowired
	private TestUserDao userDao;

	@Override
	protected Dao<TestUser, String> getEntityDao() {
		return userDao;
	}

}
