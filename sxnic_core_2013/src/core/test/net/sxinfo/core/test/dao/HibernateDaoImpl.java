package net.sxinfo.core.test.dao;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxinfo.core.test.entity.TestEntity2;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("extraDao")
@Transactional
public class HibernateDaoImpl extends HibernateDao<TestEntity2, Long> implements
		DaoExtra {
}
