package net.sxinfo.core.spring25;

import java.util.List;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.spring25.test.SpringTxTestCase;
import net.sxinfo.core.test.dao.DaoExtra;
import net.sxinfo.core.test.entity.TestEntity2;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "/applicationContext_test.xml" })
public class HibernateDaoTest extends SpringTxTestCase {

	private static final Logger logger = LoggerFactory
			.getLogger(HibernateDaoTest.class);

	@Autowired
	private DaoExtra extraDao;

	@Test
	public void testSave() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 e1 = extraDao.get(entity.getId());
		assertEquals(e1, entity);

		extraDao.delete(entity);

		e1 = extraDao.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testUpdate() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 e1 = extraDao.get(entity.getId());
		assertEquals(e1, entity);

		entity.setName("entity3");
		e1 = extraDao.get(entity.getId());

		assertEquals("entity3", e1.getName());

		extraDao.delete(entity);

		e1 = extraDao.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testDelete() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 e1 = extraDao.get(entity.getId());
		assertEquals(e1, entity);

		extraDao.delete(entity);

		e1 = extraDao.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testDeleteById() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 e1 = extraDao.get(entity.getId());
		assertEquals(e1, entity);

		extraDao.deleteById(entity.getId());

		e1 = extraDao.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testClear() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraDao.save(entity1);

		extraDao.clear();

		assertEquals(0, extraDao.getAll().size());
	}

	@Test
	public void testGetPage() {

		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraDao.save(entity1);

		Page page = extraDao.getPage(1, 20, "id", true);

		assertEquals(2, page.getTotalResults());
		assertEquals(1, page.getTotalPages());

		extraDao.clear();

		assertEquals(0, extraDao.getAll().size());
	}

	@Test
	public void testGetPageByCriteria() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraDao.save(entity1);

		HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder(
				"id", true);

		HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);

		Criterion dc = null;

		dc = Restrictions.like("name", "%entity%");
		hc.add(dc);

		Page page = extraDao.getPageByCriteria(1, 20, hc);

		assertEquals(2, page.getTotalResults());
		assertEquals(1, page.getTotalPages());

		extraDao.clear();

		assertEquals(0, extraDao.getAll().size());
	}

	@Test
	public void testGetAll() {

		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraDao.save(entity1);

		List<TestEntity2> list = extraDao.getAll();

		assertEquals(2, list.size());

		extraDao.clear();

		assertEquals(0, extraDao.getAll().size());
	}
	
	@Test
	public void testFindPageBySql(){
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraDao.save(entity);
		
		entity = new TestEntity2();
		entity.setName("entity21");
		extraDao.save(entity);
		
		entity = new TestEntity2();
		entity.setName("entity22");
		extraDao.save(entity);
		
		List<TestEntity2> list = extraDao.getAll();

		assertEquals(3, list.size());
		
		Page page = extraDao.getPageBySql(1, 10, TestEntity2.class, "from TestEntity2 where name <> ? ", new Object[]{"entity2"});
		
		assertEquals(2,page.getTotalResults());
		
	}

}
