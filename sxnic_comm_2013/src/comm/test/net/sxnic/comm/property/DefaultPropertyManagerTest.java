package net.sxnic.comm.property;

import java.util.List;

import junit.framework.Assert;
import net.sxinfo.core.dao.Page;
import net.sxinfo.core.entity.EntityAlreadyExistsException;
import net.sxnic.comm.CommConstant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * propertyManager 的测试类
 * 
 * @author 孙宇飞
 * @version 2009-3-5 Introduction ：
 */

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext_test.xml" })
public class DefaultPropertyManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private static Log log = LogFactory
			.getLog(DefaultPropertyManagerTest.class);

	@Autowired
	private PropertyManager propertyManager;

	@Test
	public void testCreate() throws EntityAlreadyExistsException {

		Property p1 = new Property("p1", "p11");
		Property p2 = new Property("p2", "p22");

		propertyManager.create(p1);
		propertyManager.create(p2);

		List<Property> list = propertyManager.getAll();

		Assert.assertEquals(2, list.size());

		Property p = propertyManager.get(p1.getId());

		Assert.assertEquals(p, p1);

		String value = propertyManager.getValueByName(p1.getPropName());

		Assert.assertEquals(value, p1.getPropValue());
	}

	@Test
	public void testGetPage() throws EntityAlreadyExistsException {
		Property p1 = new Property("p1", "p11");
		Property p2 = new Property("p2", "p22");

		propertyManager.create(p1);
		propertyManager.create(p2);

		Page page = propertyManager.getPropertys(1, 2, null, true);

		Assert.assertEquals(2, page.getResults().size());
	}

	@Test
	public void testInit() throws EntityAlreadyExistsException {
		Property p1 = new Property("p1", "p11");
		Property p2 = new Property("p2.p1.p3", "p22");

		propertyManager.create(p1);
		propertyManager.create(p2);

		propertyManager.init();

		log.debug(CommConstant.PROPERTY_MAP);

		Assert.assertEquals("p11", CommConstant.PROPERTY_MAP.get("p1"));
		Assert.assertEquals("p22", CommConstant.PROPERTY_MAP.get("p2.p1.p3"));
	}

	@Test
	public void testGetByName() throws EntityAlreadyExistsException {
		Property p1 = new Property("p1", "p11");
		propertyManager.create(p1);

		Property p = propertyManager.getByName(p1.getPropName());

		Assert.assertEquals(p1, p);

		p = propertyManager.getByName(p1.getPropName() + "000000000000");

		Assert.assertNull(p);

	}

}
