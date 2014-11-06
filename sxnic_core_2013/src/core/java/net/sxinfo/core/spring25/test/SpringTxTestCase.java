package net.sxinfo.core.spring25.test;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Spring��֧�����ݿ����������ע���JUnit 4 TestCase�����д.
 * 
 * @author calvin
 */
// Ĭ������applicationContext.xml,�����е�@ContextConfiguration���彫�ϲ�����Ķ���.
public class SpringTxTestCase extends
		AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * ˢ��sessionFactory,ǿ��Hibernateִ��SQL����֤ORM����.
	 * 
	 * sessionFactory��Ĭ��Ϊ"sessionFactory".
	 * 
	 * @see #flush(String)
	 */
	protected void flush() {
		flush("sessionFactory");
	}

	/**
	 * ˢ��sessionFactory,ǿ��Hibernateִ��SQL����֤ORM����. ��Ϊû��ִ��commit����,������Ĳ������ݿ�.
	 * 
	 * @param sessionFactoryName applicationContext��sessionFactory������.
	 */
	protected void flush(final String sessionFactoryName) {
		((SessionFactory) applicationContext.getBean(sessionFactoryName))
				.getCurrentSession().flush();
	}

	/**
	 * �����������ֺ���ĸ������ַ���.
	 * 
	 * @param length �����ַ�������
	 */
	protected String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

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
