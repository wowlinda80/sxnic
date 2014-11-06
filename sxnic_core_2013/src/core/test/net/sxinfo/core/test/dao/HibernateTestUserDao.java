package net.sxinfo.core.test.dao;

import org.springframework.stereotype.Repository;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxinfo.core.test.entity.TestUser;

@Repository("userDao")
public class HibernateTestUserDao extends HibernateDao<TestUser,String> implements TestUserDao{

}
