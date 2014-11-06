package net.sxinfo.core.test.manager;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxinfo.core.test.dao.TestDataDao;
import net.sxinfo.core.test.entity.TestData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dataManager")
public class DefaultTestDataManager extends DefaultManager<TestData, TestDataDao, String> implements TestDataManager{
	
	@Autowired
	private TestDataDao dataDao;

	@Override
	protected Dao<TestData, String> getEntityDao() {
		return dataDao;
	}

}
