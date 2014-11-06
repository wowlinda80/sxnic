package net.sxinfo.core.spring25;

import net.sxinfo.core.dao.hibernate3.TestEntity2;

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
