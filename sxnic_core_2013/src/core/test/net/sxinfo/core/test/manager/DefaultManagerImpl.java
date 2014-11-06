package net.sxinfo.core.test.manager;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxinfo.core.test.dao.DaoExtra;
import net.sxinfo.core.test.entity.TestEntity2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("extraManager")
@Transactional
public class DefaultManagerImpl extends
		DefaultManager<TestEntity2, DaoExtra, Long> implements ManagerExtra {

	@Autowired
	private DaoExtra extraDao;

	@Override
	protected Dao<TestEntity2, Long> getEntityDao() {
		return extraDao;
	}

}
