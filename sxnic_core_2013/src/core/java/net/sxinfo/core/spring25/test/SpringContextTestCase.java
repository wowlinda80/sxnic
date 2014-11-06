package net.sxinfo.core.spring25.test;

import junit.framework.Assert;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * Spring��֧������ע���JUnit 4 TestCase�����д.
 * 
 * @author calvin
 */
// Ĭ������applicationContext.xml,�����е�@ContextConfiguration���彫�ϲ�����Ķ���.
@ContextConfiguration(locations = { "/applicationContext25.xml" })
public class SpringContextTestCase extends AbstractJUnit4SpringContextTests {

	// Assert ���� //

	protected void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	protected void assertEquals(String message, Object expected, Object actual) {
		Assert.assertEquals(message, expected, actual);
	}

	protected void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	protected void assertTrue(String message, boolean condition) {
		Assert.assertTrue(message, condition);
	}

	protected void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}

	protected void assertNotNull(String message, Object object) {
		Assert.assertNotNull(message, object);
	}
}
