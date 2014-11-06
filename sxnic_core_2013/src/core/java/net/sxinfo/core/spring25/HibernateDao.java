package net.sxinfo.core.spring25;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sxinfo.core.dao.NonUniqueException;
import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.PersistenceException;
import net.sxinfo.core.dao.WrongObjectTypeException;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateCriterion;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.util.ReflectionUtils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@SuppressWarnings("unchecked")
public abstract class HibernateDao<T, PK extends Serializable> implements
		Dao<T, PK> {

	protected Logger logger = LoggerFactory.getLogger(HibernateDao.class);

	@Autowired
	private SessionFactory sf;

	protected Class<T> entityClass;

	/**
	 * ������չ��DAO����ʹ�õĹ��캯��.
	 * 
	 * ͨ������ķ��Ͷ���ȡ�ö�������Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */

	public HibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * ��ȡ��Dao��Ҫ������ʵ����
	 * 
	 * @return ��Dao��Ҫ������ʵ����
	 */
	// protected abstract Class<T> getObjectClass();
	/**
	 * ���ָ������������Ƿ�Ϊ��Dao���Դ�������͡� �ȼ������Ƿ�Ϊnull��,�����null���׳��쳣
	 * 
	 * @param o
	 *            Ҫ���Ķ���
	 * 
	 * @throws WrongObjectTypeException
	 *             �����Dao�����Դ������ֶ�����׳�
	 * @throws PersistenceException
	 *             ָ����������null���׳��쳣
	 */
	@SuppressWarnings("unused")
	private void checkObjectType(Object o) throws WrongObjectTypeException,
			PersistenceException {

		if (o == null) {
			logger.error("���Դ���Ϊnull��ʵ�����������Ǹ������ϵĴ���");
			throw new PersistenceException("Ҫ����Ķ�����Ϊnull");
		}

		if (!o.getClass().isInstance(entityClass)) {
			
			logger.error("��Dao: " + this.getClass() + "ֻ�ܴ�������Ϊ: " + entityClass
					+ " �Ķ���, ��ǰ��������Ϊ: " + o.getClass());

			throw new WrongObjectTypeException("��Dao: " + this.getClass()
					+ "ֻ�ܴ�������Ϊ: " + entityClass + " �Ķ���, ��ǰ��������Ϊ: "
					+ o.getClass());
		}
	}

	/**
	 * ��ձ����������ݡ������ã����ɻָ���
	 */
	public void clear() throws PersistenceException {
		try {
			sf.getCurrentSession()
					.createQuery("delete " + entityClass.getSimpleName())
					.executeUpdate();
			logger.debug("clear entity: {} success", entityClass.toString());
		} catch (Exception e) {
			logger.error("clear entity: {} delete��e:{}",
					entityClass.toString(), e.toString());
			throw new PersistenceException("�����ʵ����� " + entityClass.toString()
					+ " ʱ�����쳣", e);
		}
	}

	/**
	 * ������߸���ʵ����
	 */
	public void save(T entity) throws WrongObjectTypeException,
			PersistenceException {
		try {
			sf.getCurrentSession().saveOrUpdate(entity);
			logger.debug("save or  update entity: {} success", entity);
		} catch (Exception e) {
			logger.error("save or  update entity: {} error", entity);

			throw new PersistenceException("�ڱ���ʵ����� " + entity + " ʱ�����쳣", e);
		}
	}

	/**
	 * ����ʵ����
	 */
	public void update(T entity) throws WrongObjectTypeException,
			PersistenceException {		
		try {
			sf.getCurrentSession().saveOrUpdate(entity);
			logger.debug("save or  update entity: {} success", entity);
		} catch (Exception e) {
			logger.error("save or  update entity: {} error", entity);

			throw new PersistenceException("�ڱ���ʵ����� " + entity + " ʱ�����쳣", e);
		}
	}

	/**
	 * ɾ��ʵ����
	 */
	public void delete(T entity) throws WrongObjectTypeException,
			PersistenceException {
		try {
			sf.getCurrentSession().delete(entity);
			logger.debug("delete entity: {} success", entity);
		} catch (Exception e) {
			logger.error("delete entity: {} error", entity);
			throw new PersistenceException("��ɾ��ʵ����� " + entity + " ʱ�����쳣", e);
		}
	}

	/**
	 * ��������IDɾ��ʵ����
	 */
	public void deleteById(Serializable PK) throws PersistenceException {
		Object entity = get(PK);
		try {
			sf.getCurrentSession().delete(entity);
			logger.debug("delete entity: {} success", entity);
		} catch (Exception e) {
			logger.error("delete entity: {} error", entity);
			throw new PersistenceException("��ɾ��ʵ����� " + entity + " ʱ�����쳣", e);
		}

	}

	/**
	 * ��������ID��ѯʵ����
	 */
	public T get(Serializable PK) throws PersistenceException {
		try {
			return (T) sf.getCurrentSession().load(entityClass, PK);
		} catch (Exception e) {
			logger.debug("load entity error, id: {} ", PK
					+ ", Error: entity does not existed");
			// throw new PersistenceException("�ڲ�ѯʵ����� " + PK + " ʱ�����쳣", e);
			return null;
		}
	}

	/**
	 * ��ȡȫ������.
	 */
	public List<T> getAll() {
		Criterion criterion = Restrictions.isNotNull("id");
		return find(criterion);
	}

	/**
	 * �����Բ��Ҷ����б�,ƥ�䷽ʽΪ���.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * �����Բ���Ψһ����,ƥ�䷽ʽΪ���.
	 */
	public T findByUnique(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * ��HQL��ѯ�����б�.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * ��HQL��ѯ�����б�.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	public List<T> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * ��HQL��ѯΨһ����.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	public T findUnique(final String hql, final Object... values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * ��HQL��ѯΨһ����.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	public T findUnique(final String hql, final Map<String, Object> values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * ��HQL��ѯInteger���ͽ��.
	 */
	public Integer findInt(final String hql, final Object... values) {
		return (Integer) findUnique(hql, values);
	}

	public Integer findInt(final String hql, final Map<String, Object> values) {
		return (Integer) findUnique(hql, values);
	}

	/**
	 * ��HQL��ѯLong���ͽ��.
	 */
	public Long findLong(final String hql, final Object... values) {
		return (Long) findUnique(hql, values);
	}

	/**
	 * ��HQL��ѯLong���ͽ��.
	 */
	public Long findLong(final String hql, final Map<String, Object> values) {
		return (Long) findUnique(hql, values);
	}

	/**
	 * ִ��HQL���������޸�/ɾ������.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * ִ��HQL���������޸�/ɾ������.
	 */
	public int batchExecute(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * ���ݲ�ѯHQL������б���Query����.
	 * 
	 * �����װ��find()����ȫ��Ĭ�Ϸ��ض�������ΪT,����ΪTʱʹ�ñ�����.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString����Ϊ��");
		Query query = sf.getCurrentSession()
				.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * ���ݲ�ѯHQL������б���Query����.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	public Query createQuery(final String queryString,
			final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString����Ϊ��");
		Query query = sf.getCurrentSession()
				.createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * ��Criteria��ѯ�����б�.
	 * 
	 * @param criterions
	 *            �����ɱ��Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * ��Criteria��ѯΨһ����.
	 * 
	 * @param criterions
	 *            �����ɱ��Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * ����Criterion��������Criteria.
	 * 
	 * �����װ��find()����ȫ��Ĭ�Ϸ��ض�������ΪT,����ΪTʱʹ�ñ�����.
	 * 
	 * @param criterions
	 *            �����ɱ��Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = sf
				.getCurrentSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * ��ʼ������. ʹ��load()�����õ��Ľ��Ƕ���Proxy��, �ڴ���View��ǰ��Ҫ���г�ʼ��. initObject(user)
	 * ,��ʼ��User��ֱ�����ԣ��������ʼ���ӳټ��صĹ������Ϻ�����.
	 * initObject(user.getRoles())����ʼ��User��ֱ�����Ժ͹�������.
	 * initObject(user.getDescription())����ʼ��User��ֱ�����Ժ��ӳټ��ص�Description����.
	 */
	public void initObject(Object object) {
		Hibernate.initialize(object);
	}

	/**
	 * ͨ��Set����Ψһ�Ķ����б�Ψһ��. ��Ҫ����HQL/CriteriaԤ���ع��������γ��ظ���¼,�ֲ�����ʹ��distinct��ѯ���ʱ.
	 */
	public <X> List<X> distinct(List<X> list) {
		Set<X> set = new LinkedHashSet<X>(list);
		return new ArrayList<X>(set);
	}

	/**
	 * ȡ�ö����������.
	 */
	public String getIdName() {
		ClassMetadata meta = sf
				.getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass.getSimpleName()
				+ " not define in HibernateSessionFactory.");
		return meta.getIdentifierPropertyName();
	}

	/** =========��������ΪSpringSide calvin====================== **/

	/** =========��������Ϊ �ܺ� ԭ��====================== **/

	/**
	 * ����ָ�����͵ķ�ҳ���ʵ��������������������б��С�
	 * <p>
	 * ������������Criterion��Order<br/>
	 * ���Ҵ�����nested properties
	 * 
	 * @param page
	 *            ��ǰҳ��
	 * @param pageSize
	 *            һҳ�������ļ�¼��
	 * @param criterions
	 *            ��ѯ�����б�
	 * @param orders
	 *            ���������б�
	 * @return ����ָ��������ҳ���Page����
	 * @throws PersistenceException
	 *             �ڷ�ҳ�����г��ֵ��쳣
	 */
	public Page getPageByCriteria(final int page, final int pageSize,
			HibernateCriteria hc) {

		Set<String> aliasList = new HashSet<String>();

		final DetachedCriteria criteria = getDetachedCriteria();

		addNestedProperties(hc, criteria, aliasList);

		// ��ȡ��¼��
		int totalResults = getTotalResults(hc);

		try {
			Criteria c = criteria.getExecutableCriteria(sf.getCurrentSession());

			// ���ÿ�ʼ�ļ�¼��
			c.setFirstResult((page - 1) * pageSize);

			// ����ȡ������¼��
			c.setMaxResults(pageSize);

			List results = c.list();

			return new Page(results, page, pageSize, totalResults);
		} catch (Exception e) {
			logger.error("�ڸ���������ҳʱ�����쳣", e);
			throw new PersistenceException("�ڸ���������ҳʱ�����쳣", e);
		}
	}

	/**
	 * ���� HibernateCriteria ��������ѯ����List
	 * 
	 * @param hc
	 * @return
	 */
	public List<T> getByCriteria(HibernateCriteria hc) {

		Set<String> aliasList = new HashSet<String>();

		final DetachedCriteria criteria = getDetachedCriteria();

		addNestedProperties(hc, criteria, aliasList);

		try {
			return criteria.getExecutableCriteria(
					sf.getCurrentSession())
					.list();

		} catch (Exception e) {
			logger.error("�ڸ���������ѯʱ�����쳣", e);
			throw new PersistenceException("�ڸ���������ѯʱ�����쳣��", e);
		}
	}

	public Page getPage(int page, int pageSize, String orderProperty,
			boolean asc) {

		HibernateOrder order = HibernateDaoUtils.createHibernateOrder(
				orderProperty, asc);

		return getPageByCriteria(page, pageSize, new HibernateCriteria()
				.add(order));
	}

	/**
	 * ��ȡ���ݲ�ѯ������ͶӰ���õļ�¼����
	 * 
	 * <p>
	 * ��ʵ�������������Բ������ַ�������������˵�һ�֡�<br/>
	 * ���ַ��������˶�ʹ��һ��SQL���select count(*) �ķ�ʽ��ѯ�����ݵ������������ַ�����ȱ�㣬����Sybase�������﷨
	 * �ϲ�֧�ֻ�ȡ�ض��������ݵ����ݿ⣬�ᵼ�������½���
	 * 
	 * <p>
	 * �ڶ��ַ�ʽ��ͨ���ɹ����Ľ������Ȼ��ͨ��last()��ָ�붨λ�� �������ĩ�ˣ�Ȼ��ͨ��getRow()����ȡ���һ�����ݵļ�¼��
	 * �Ӷ�����������������ַ�ʽҲ��ȱ�ݣ������е�JDBC Driver�ڷ����� �˲���֧�ֿɹ����ļ�¼����ֻ�ò��ý��α�ͨ��next()����
	 * ���������һ�������ķ�ʽʵ�֣������ڻ�ȡ�������ݵ�ʱ��Ч����ȻҲ�͵� �ľ����ˡ�����Oracle��DB2��JDBC Driver����������
	 * 
	 * <p>
	 * �ȽϺõ�JDBC Driverʵ����ͨ��Server Cursor����Store Procedure ʵ�����ֹ��ܵġ���SQL Server
	 * jtds��
	 * 
	 * <p>
	 * <b>ע��</b>����count(*)��ʱ��Ӧ���ǲ���order by�ģ����Ƿ��������������
	 * ��Projection��Criterion��Ҳ����˵����Ҫ��ʵ��ʹ������ȥ�����ˡ�
	 * 
	 * ʹ��SessionFactoryImpl�е�getDialect����֪������ʹ�õ����ݿ����ͣ� ���Կ��Ǹ��ݾ������������ʹ�õ�һ�ֻ�ڶ��ַ�����
	 * 
	 * @param criterions
	 *            ��ѯ�����б�
	 * @return ���ݲ�ѯ������ͶӰ���õļ�¼��
	 * @throws PersistenceException
	 *             ��װ��ʹ�ÿɹ�����¼����ȡ��¼��ʱ�����쳣
	 */
	protected int getTotalResults(HibernateCriteria hc) {
		final DetachedCriteria criteria = getDetachedCriteria();

		if (hc != null && hc.getCriterion().size() > 0) {
			// ��������
			addNestedCriterions(hc.getCriterion(), criteria,
					new HashSet<String>());
		}

		criteria.setProjection(Projections.rowCount());
		//int total = Long.signum((Long)getSingleObject(criteria)) ;
		//System.out.println("getSingleObject(criteria)====="+getSingleObject(criteria));
		int total = (Integer) getSingleObject(criteria);
		logger.debug("Total: " + total);

		return total;
	}

	/**
	 * ��ȡָ�������ĵ������󣬱����ǵ�������������صĽ�������� ��������׳�PersistenceException
	 * 
	 * @param <T>
	 *            ��DaoҪ������ʵ�����
	 * @param criteria
	 *            ָ������
	 * @return ָ�������ĵ�������
	 * @throws NonUniqueException
	 *             �����ó���һ�����ݾ��׳����쳣
	 * @throws PersistenceException
	 *             �ڻ�ȡ�����г��ֵ��쳣
	 */
	protected int getSingleObject(final DetachedCriteria criteria)
			throws NonUniqueException {

		List list = getObjects(criteria);

		if (list.size() == 0) {
			return 0;
		}

		if (list.size() > 1) {
			throw new NonUniqueException();
		}

		return ((Long)list.get(0)).intValue();
	}

	/**
	 * ��ȡָ�������Ķ����б�
	 * 
	 * @param criteria
	 *            ָ������
	 * @return ָ�������Ķ����б�
	 * @throws PersistenceException
	 *             �ڻ�ȡ�����г��ֵ��쳣
	 */
	protected <T> List<T> getObjects(final DetachedCriteria criteria)
			throws PersistenceException {

		try {
			return criteria.getExecutableCriteria(
					sf.getCurrentSession())
					.list();
		} catch (Exception e) {
			logger.error("�ڻ�ȡ�����б�ʱ�����쳣", e);
			throw new PersistenceException("�ڻ�ȡ�����б�ʱ�����쳣", e);
		}
	}

	/**
	 * ͨ���ɹ����Ľ��������ý��������
	 * 
	 * @param query
	 *            Hibernate Query Object
	 * @return ���������
	 */
	protected int getTotalResultsByQuery(Query query) {
		ScrollableResults scroll = query.scroll();
		scroll.last();
		int rowNumber = scroll.getRowNumber();
		return rowNumber + 1;
	}

	/**
	 * ��ȡDao��Ӧ��DetachedCriteria
	 * 
	 * @return Dao��Ӧ��DetachedCriteria
	 */
	protected DetachedCriteria getDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass);
	}

	/**
	 * ���Criterion��Order
	 * 
	 * @param hc
	 *            HibernateCriteria
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            a alias list
	 * @return �����Criterion��Order��DetachedCriteria
	 */
	private DetachedCriteria addNestedProperties(HibernateCriteria hc,
			DetachedCriteria c, Set<String> aliasList) {

		if (hc == null) {
			return c;
		}

		if (hc.getCriterion().size() > 0) {
			addNestedCriterions(hc.getCriterion(), c, aliasList);
		}

		if (hc.getOrders().size() > 0) {
			addNestedOrders(hc.getOrders(), c, aliasList);
		}

		return c;
	}

	/**
	 * ���Criterion��֧��nested properties
	 * 
	 * @param criterions
	 *            Criterions
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            a alias list
	 * @return �����Criterion��DetachedCriteria
	 */
	private DetachedCriteria addNestedCriterions(
			List<HibernateCriterion> criterions, DetachedCriteria c,
			Set<String> aliasList) {

		for (HibernateCriterion hc : criterions) {
			c = createAlias(hc.getPath(), c, aliasList);
			addCriterion(hc, c);
		}

		return c;
	}

	/**
	 * ΪDetachedCriteria����alias
	 * 
	 * @param path
	 *            Path
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            Alias List
	 * @return ����alias���DetachedCriteria
	 */
	private DetachedCriteria createAlias(String path, DetachedCriteria c,
			Set<String> aliasList) {

		if (path != null) {
			String[] pathPart = StringUtils.split(path, ".");

			for (int i = 0; i < pathPart.length; i++) {
				String alias = "";
				for (int j = 0; j <= i; j++) {
					alias = alias + pathPart[j];
					if (j < i) {
						alias += "_";
					}
				}

				if (!aliasList.contains(alias)) {
					// create alias
					logger.debug("Create alias: " + alias.replaceAll("_", ".")
							+ " - " + alias);

					c = c.createAlias(alias.replaceAll("_", "."), alias);
					aliasList.add(alias);
				} else {
					logger.debug(alias + " contained, not create");
				}

				logger.debug("alias: " + alias);
			}
		}

		return c;
	}

	/**
	 * �������������֧��nested properties
	 * 
	 * @param orders
	 *            ��������
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            a alias list
	 * @return �����Order��DetachedCriteria
	 */
	private DetachedCriteria addNestedOrders(List<HibernateOrder> orders,
			DetachedCriteria c, Set<String> aliasList) {

		for (HibernateOrder ho : orders) {
			c = createAlias(ho.getPath(), c, aliasList);
			addOrder(ho, c);
		}

		return c;
	}

	/**
	 * ����ѯ������ӵ�ָ��DetachedCriteria��
	 * 
	 * @param criterion
	 *            Ҫ��ӵĲ�ѯ������Ӧ��HibernateCriterion����
	 * @param c
	 *            DetachedCriteria
	 */
	private static void addCriterion(HibernateCriterion criterion,
			DetachedCriteria c) {
		c.add(criterion.getCriterion());
	}

	/**
	 * ������������ӵ�ָ��DetachedCriteria�ϣ�һ������²�Ҫֱ�ӵ��ñ�����
	 * 
	 * @param order
	 *            Ҫ��ӵ�����������Ӧ��HibernateOrder����
	 * @param c
	 *            DetachedCriteria
	 */
	private static void addOrder(HibernateOrder order, DetachedCriteria c) {
		c.addOrder(order.getOrder());
	}
	
	/**
	 * �����౩¶��ȡ��ǰSession�ķ���
	 * @return
	 */
	protected Session getCurrSession(){
		return sf.getCurrentSession();
	}
	
	
	/**
	 * ����map��ѯ
	 * @param map
	 * @return
	 */
	public List<T> findBy(String entityName,Map<String,Object> map){
		
		if(map == null || map.keySet().size()==0){
			return null;
		}
		
		String sql = " from "+entityName+" where ";
		
		for(String key:map.keySet()){
			sql = sql + " "+key +" = ? and";
		}
		
		if(sql.endsWith("and")){
			sql = StringUtils.removeEnd(sql, "and");
		}		
		
		return null;
	}
	
	/**
	 * ����map��ѯ
	 * @param map
	 * @return
	 */
	public List<T> findBySql(String sql,final Object[] values){
		Query query=sf.getCurrentSession().createQuery(sql);
		
		int i = 0;
		for(Object obj:values){
			query.setParameter(i, obj);
			i++;
		}
		
		return query.list();
	}
	
	/**
	 * ����SQL��估������ѯ
	 * @return
	 */
	public Page findPageBySql(final int page, final int pageSize,Class entityClass,String sql,final Object[] values){
		
		if(entityClass == null){
			entityClass = this.entityClass;
		}
		
		Query query=sf.getCurrentSession().createQuery(sql);
		
		int i = 0;
		for(Object obj:values){
			query.setParameter(i, obj);
			i++;
		}
		
		int totalResults = getTotalResultByQuery(entityClass, sql, values);
				
		// ���ÿ�ʼ�ļ�¼��
		query.setFirstResult((page - 1) * pageSize);		
		// ����ȡ������¼��
		query.setMaxResults(pageSize);
		
		List list = query.list();
		
		return new Page(list, page, pageSize, totalResults);
	}
	
	private int getTotalResultByQuery(Class entityClass,String sql,final Object[] values){
		
		if(!StringUtils.contains(sql, "select")){
			sql = "select count(id) "+sql;
		}else{
			sql = StringUtils.substringAfter(sql, "from");
			sql = "select count(id) from "+sql;
		}
		
		if(StringUtils.contains(sql, " order")){
			sql = StringUtils.substringBefore(sql, "order");
		}		
		
		Query query=sf.getCurrentSession().createQuery(sql);
		
		int i = 0;
		for(Object obj:values){
			query.setParameter(i, obj);
			i++;
		}
		
		return ((Long)query.uniqueResult()).intValue(); 
		
	}
	
}
