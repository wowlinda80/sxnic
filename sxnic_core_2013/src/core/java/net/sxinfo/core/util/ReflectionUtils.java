package net.sxinfo.core.util;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * �����Utils��������.
 * 
 * �ṩ����˽�б���,��ȡ��������Class,��ȡ������Ԫ�ص����Ե�Utils����.
 * 
 * @author calvin
 */
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	private ReflectionUtils() {
	}

	/**
	 * ֱ�Ӷ�ȡ��������ֵ,����private/protected���η�,������getter����.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("�������׳����쳣{}", e.getMessage());
		}
		return result;
	}

	/**
	 * ֱ�����ö�������ֵ,����private/protected���η�,������setter����.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}
	}

	/**
	 * ѭ������ת��,��ȡ�����DeclaredField.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object, "object����Ϊ��");
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * ѭ������ת��,��ȡ���DeclaredField.
	 */
	@SuppressWarnings("unchecked")
	protected static Field getDeclaredField(final Class clazz, final String fieldName) {
		Assert.notNull(clazz, "clazz����Ϊ��");
		Assert.hasText(fieldName, "fieldName");
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field���ڵ�ǰ�ඨ��,��������ת��
			}
		}
		return null;
	}

	/**
	 * ǿ��ת��fileld�ɷ���.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * ͨ������,��ö���Classʱ�����ĸ���ķ��Ͳ���������. ��public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * ͨ������,��ö���Classʱ�����ĸ���ķ��Ͳ���������. ��public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */

	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * ��ȡ�����еĶ��������,��ϳ�List.
	 * 
	 * @param collection ��Դ����.
	 * @param propertityName Ҫ��ȡ��������.
	 */
	@SuppressWarnings("unchecked")
	public static List fetchElementPropertyToList(final Collection collection, final String propertyName)
			throws Exception {

		List list = new ArrayList();

		for (Object obj : collection) {
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}

		return list;
	}

	/**
	 * ��ȡ�����еĶ��������,��ϳ��ɷָ���ָ����ַ���.
	 * 
	 * @param collection ��Դ����.
	 * @param propertityName Ҫ��ȡ��������.
	 * @param separator �ָ���.
	 */
	@SuppressWarnings("unchecked")
	public static String fetchElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) throws Exception {
		List list = fetchElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}
}
