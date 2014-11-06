package net.sxinfo.core.spring25;

import net.sxinfo.core.dao.hibernate3.TestEntity2;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("extraDao")
@Transactional
public class HibernateDaoImpl extends HibernateDao<TestEntity2, Long> implements
		DaoExtra {
}
