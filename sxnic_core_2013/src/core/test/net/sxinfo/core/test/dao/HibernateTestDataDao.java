package net.sxinfo.core.test.dao;

import org.springframework.stereotype.Repository;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxinfo.core.test.entity.TestData;

@Repository("dataDao")
public class HibernateTestDataDao extends HibernateDao<TestData, String> implements TestDataDao{

}
