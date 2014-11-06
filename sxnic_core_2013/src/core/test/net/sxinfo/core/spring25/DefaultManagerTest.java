package net.sxinfo.core.spring25;

import java.util.List;
import java.util.Vector;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.dao.hibernate3.TestEntity2;
import net.sxinfo.core.spring25.test.SpringTxTestCase;

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
public class DefaultManagerTest extends SpringTxTestCase {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultManagerTest.class);

	@Autowired
	private ManagerExtra extraManager;

	@Test
	public void testSave() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 e1 = extraManager.get(entity.getId());
		assertEquals(e1, entity);

		extraManager.delete(entity);

		e1 = extraManager.get(entity.getId());
		assertEquals(e1, null);
	}
	
	@Test
	public void testSaveList() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		
		TestEntity2 e1 = new TestEntity2();
		entity.setName("entity3");

		List<TestEntity2> list = new Vector<TestEntity2>();
		list.add(e1);
		list.add(entity);

		extraManager.save(list);
		
		extraManager.clear();
		assertEquals(0, extraManager.getAll().size());
	}

	@Test
	public void testUpdate() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 e1 = extraManager.get(entity.getId());
		assertEquals(e1, entity);

		entity.setName("entity3");
		e1 = extraManager.get(entity.getId());

		assertEquals("entity3", e1.getName());

		extraManager.delete(entity);

		e1 = extraManager.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testDelete() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 e1 = extraManager.get(entity.getId());
		assertEquals(e1, entity);

		extraManager.delete(entity);

		e1 = extraManager.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testDeleteById() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 e1 = extraManager.get(entity.getId());
		assertEquals(e1, entity);

		extraManager.deleteById(entity.getId());

		e1 = extraManager.get(entity.getId());
		assertEquals(e1, null);
	}

	@Test
	public void testDeleteByIds() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 e1 = new TestEntity2();
		entity.setName("entity3");
		extraManager.save(e1);

		extraManager.deleteByIds(new Long[] { entity.getId(), e1.getId() });

		List<TestEntity2> list = extraManager.getAll();
		assertEquals(0, list.size());
	}

	@Test
	public void testDeleteByEntitys() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 e1 = extraManager.get(entity.getId());
		assertEquals(e1, entity);

		List<TestEntity2> list = new Vector<TestEntity2>();
		list.add(e1);
		list.add(entity);

		extraManager.deleteEntitys(list);

		list = extraManager.getAll();
		assertEquals(0, list.size());
	}

	@Test
	public void testClear() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraManager.save(entity1);

		extraManager.clear();

		assertEquals(0, extraManager.getAll().size());
	}

	@Test
	public void testGetPage() {

		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraManager.save(entity1);

		Page page = extraManager.getPage(1, 20, "id", true);

		assertEquals(2, page.getTotalResults());
		assertEquals(1, page.getTotalPages());

		extraManager.clear();

		assertEquals(0, extraManager.getAll().size());
	}

	@Test
	public void testGetPageByCriteria() {
		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraManager.save(entity1);

		HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder(
				"id", true);

		HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);

		Criterion dc = null;

		dc = Restrictions.like("name", "%entity%");
		hc.add(dc);

		Page page = extraManager.getPageByCriteria(1, 20, hc);

		assertEquals(2, page.getTotalResults());
		assertEquals(1, page.getTotalPages());

		extraManager.clear();

		assertEquals(0, extraManager.getAll().size());
	}

	@Test
	public void testGetAll() {

		TestEntity2 entity = new TestEntity2();
		entity.setName("entity2");
		extraManager.save(entity);

		TestEntity2 entity1 = new TestEntity2();
		entity1.setName("entity3");
		extraManager.save(entity1);

		List<TestEntity2> list = extraManager.getAll();

		assertEquals(2, list.size());

		extraManager.clear();

		assertEquals(0, extraManager.getAll().size());
	}

}
