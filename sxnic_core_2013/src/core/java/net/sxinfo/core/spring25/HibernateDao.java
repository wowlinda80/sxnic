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
	 * 用于扩展的DAO子类使用的构造函数.
	 * 
	 * 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */

	public HibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 获取本Dao所要操作的实体类
	 * 
	 * @return 本Dao所要操作的实体类
	 */
	// protected abstract Class<T> getObjectClass();
	/**
	 * 检查指定对象的类型是否为本Dao可以处理的类型。 先检查对象是否为null，,如果是null就抛出异常
	 * 
	 * @param o
	 *            要检查的对象
	 * 
	 * @throws WrongObjectTypeException
	 *             如果本Dao不可以处理这种对象就抛出
	 * @throws PersistenceException
	 *             指定对象是是null就抛出异常
	 */
	@SuppressWarnings("unused")
	private void checkObjectType(Object o) throws WrongObjectTypeException,
			PersistenceException {

		if (o == null) {
			logger.error("尝试处理为null的实体对象，这可能是个程序上的错误");
			throw new PersistenceException("要处理的对象不能为null");
		}

		if (!o.getClass().isInstance(entityClass)) {
			
			logger.error("本Dao: " + this.getClass() + "只能处理类型为: " + entityClass
					+ " 的对象, 当前对象类型为: " + o.getClass());

			throw new WrongObjectTypeException("本Dao: " + this.getClass()
					+ "只能处理类型为: " + entityClass + " 的对象, 当前对象类型为: "
					+ o.getClass());
		}
	}

	/**
	 * 清空表内所有数据。（慎用！不可恢复）
	 */
	public void clear() throws PersistenceException {
		try {
			sf.getCurrentSession()
					.createQuery("delete " + entityClass.getSimpleName())
					.executeUpdate();
			logger.debug("clear entity: {} success", entityClass.toString());
		} catch (Exception e) {
			logger.error("clear entity: {} delete。e:{}",
					entityClass.toString(), e.toString());
			throw new PersistenceException("在清空实体对象 " + entityClass.toString()
					+ " 时出现异常", e);
		}
	}

	/**
	 * 保存或者更新实体类
	 */
	public void save(T entity) throws WrongObjectTypeException,
			PersistenceException {
		try {
			sf.getCurrentSession().saveOrUpdate(entity);
			logger.debug("save or  update entity: {} success", entity);
		} catch (Exception e) {
			logger.error("save or  update entity: {} error", entity);

			throw new PersistenceException("在保存实体对象 " + entity + " 时出现异常", e);
		}
	}

	/**
	 * 更新实体类
	 */
	public void update(T entity) throws WrongObjectTypeException,
			PersistenceException {		
		try {
			sf.getCurrentSession().saveOrUpdate(entity);
			logger.debug("save or  update entity: {} success", entity);
		} catch (Exception e) {
			logger.error("save or  update entity: {} error", entity);

			throw new PersistenceException("在保存实体对象 " + entity + " 时出现异常", e);
		}
	}

	/**
	 * 删除实体类
	 */
	public void delete(T entity) throws WrongObjectTypeException,
			PersistenceException {
		try {
			sf.getCurrentSession().delete(entity);
			logger.debug("delete entity: {} success", entity);
		} catch (Exception e) {
			logger.error("delete entity: {} error", entity);
			throw new PersistenceException("在删除实体对象 " + entity + " 时出现异常", e);
		}
	}

	/**
	 * 根据主键ID删除实体类
	 */
	public void deleteById(Serializable PK) throws PersistenceException {
		Object entity = get(PK);
		try {
			sf.getCurrentSession().delete(entity);
			logger.debug("delete entity: {} success", entity);
		} catch (Exception e) {
			logger.error("delete entity: {} error", entity);
			throw new PersistenceException("在删除实体对象 " + entity + " 时出现异常", e);
		}

	}

	/**
	 * 根据主键ID查询实体类
	 */
	public T get(Serializable PK) throws PersistenceException {
		try {
			return (T) sf.getCurrentSession().load(entityClass, PK);
		} catch (Exception e) {
			logger.debug("load entity error, id: {} ", PK
					+ ", Error: entity does not existed");
			// throw new PersistenceException("在查询实体对象 " + PK + " 时出现异常", e);
			return null;
		}
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		Criterion criterion = Restrictions.isNotNull("id");
		return find(criterion);
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findByUnique(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public List<T> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public T findUnique(final String hql, final Object... values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public T findUnique(final String hql, final Map<String, Object> values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询Integer类型结果.
	 */
	public Integer findInt(final String hql, final Object... values) {
		return (Integer) findUnique(hql, values);
	}

	public Integer findInt(final String hql, final Map<String, Object> values) {
		return (Integer) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类型结果.
	 */
	public Long findLong(final String hql, final Object... values) {
		return (Long) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类型结果.
	 */
	public Long findLong(final String hql, final Map<String, Object> values) {
		return (Long) findUnique(hql, values);
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
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
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString,
			final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = sf.getCurrentSession()
				.createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
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
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy后, 在传到View层前需要进行初始化. initObject(user)
	 * ,初始化User的直接属性，但不会初始化延迟加载的关联集合和属性.
	 * initObject(user.getRoles())，初始化User的直接属性和关联集合.
	 * initObject(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initObject(Object object) {
		Hibernate.initialize(object);
	}

	/**
	 * 通过Set将不唯一的对象列表唯一化. 主要用于HQL/Criteria预加载关联集合形成重复记录,又不方便使用distinct查询语句时.
	 */
	public <X> List<X> distinct(List<X> list) {
		Set<X> set = new LinkedHashSet<X>(list);
		return new ArrayList<X>(set);
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = sf
				.getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass.getSimpleName()
				+ " not define in HibernateSessionFactory.");
		return meta.getIdentifierPropertyName();
	}

	/** =========以上内容为SpringSide calvin====================== **/

	/** =========以上内容为 曹浩 原创====================== **/

	/**
	 * 载入指定类型的分页后的实体对象，条件设置在条件列表中。
	 * <p>
	 * 本方法处理了Criterion、Order<br/>
	 * 并且处理了nested properties
	 * 
	 * @param page
	 *            当前页码
	 * @param pageSize
	 *            一页所包含的记录数
	 * @param criterions
	 *            查询条件列表
	 * @param orders
	 *            排序条件列表
	 * @return 根据指定条件分页后的Page对象
	 * @throws PersistenceException
	 *             在分页过程中出现的异常
	 */
	public Page getPageByCriteria(final int page, final int pageSize,
			HibernateCriteria hc) {

		Set<String> aliasList = new HashSet<String>();

		final DetachedCriteria criteria = getDetachedCriteria();

		addNestedProperties(hc, criteria, aliasList);

		// 获取记录数
		int totalResults = getTotalResults(hc);

		try {
			Criteria c = criteria.getExecutableCriteria(sf.getCurrentSession());

			// 设置开始的记录号
			c.setFirstResult((page - 1) * pageSize);

			// 设置取的最大记录数
			c.setMaxResults(pageSize);

			List results = c.list();

			return new Page(results, page, pageSize, totalResults);
		} catch (Exception e) {
			logger.error("在根据条件分页时出现异常", e);
			throw new PersistenceException("在根据条件分页时出现异常", e);
		}
	}

	/**
	 * 根据 HibernateCriteria 条件，查询数据List
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
			logger.error("在根据条件查询时出现异常", e);
			throw new PersistenceException("在根据条件查询时出现异常：", e);
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
	 * 获取根据查询条件和投影所得的记录数。
	 * 
	 * <p>
	 * 从实现上来将，可以采用两种方法，这里采用了第一种。<br/>
	 * 这种方法采用了多使用一条SQL语句select count(*) 的方式查询了数据的数量。但这种方法有缺点，对于Sybase这种在语法
	 * 上不支持获取特定区间数据的数据库，会导致性能下降。
	 * 
	 * <p>
	 * 第二种方式是通过可滚动的结果集，然后通过last()将指针定位到 结果集的末端，然后通过getRow()来获取最后一条数据的记录号
	 * 从而获得数据量。但这种方式也有缺陷，就是有的JDBC Driver在服务器 端并不支持可滚动的记录集，只好采用将游标通过next()逐行
	 * 滚动到最后一行这样的方式实现，这样在获取大量数据的时候效率自然也就低 的惊人了。比如Oracle和DB2的JDBC Driver就是这样。
	 * 
	 * <p>
	 * 比较好的JDBC Driver实现是通过Server Cursor或者Store Procedure 实现这种功能的。如SQL Server
	 * jtds。
	 * 
	 * <p>
	 * <b>注意</b>：在count(*)的时候应该是不能order by的，但是否还有类似情况发生
	 * 在Projection或Criterion上也很难说，需要在实际使用中再去发现了。
	 * 
	 * 使用SessionFactoryImpl中的getDialect可以知道正在使用的数据库类型， 可以考虑根据具体情况来决定使用第一种或第二种方法。
	 * 
	 * @param criterions
	 *            查询条件列表
	 * @return 根据查询条件和投影所得的记录数
	 * @throws PersistenceException
	 *             包装在使用可滚动记录集获取记录数时出现异常
	 */
	protected int getTotalResults(HibernateCriteria hc) {
		final DetachedCriteria criteria = getDetachedCriteria();

		if (hc != null && hc.getCriterion().size() > 0) {
			// 计算行数
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
	 * 获取指定条件的单个对象，必须是单个对象，如果返回的结果集包含 多个对象将抛出PersistenceException
	 * 
	 * @param <T>
	 *            本Dao要操作的实体对象
	 * @param criteria
	 *            指定条件
	 * @return 指定条件的单个对象
	 * @throws NonUniqueException
	 *             如果获得超过一条数据就抛出本异常
	 * @throws PersistenceException
	 *             在获取过程中出现的异常
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
	 * 获取指定条件的对象列表
	 * 
	 * @param criteria
	 *            指定条件
	 * @return 指定条件的对象列表
	 * @throws PersistenceException
	 *             在获取过程中出现的异常
	 */
	protected <T> List<T> getObjects(final DetachedCriteria criteria)
			throws PersistenceException {

		try {
			return criteria.getExecutableCriteria(
					sf.getCurrentSession())
					.list();
		} catch (Exception e) {
			logger.error("在获取对象列表时出现异常", e);
			throw new PersistenceException("在获取对象列表时出现异常", e);
		}
	}

	/**
	 * 通过可滚动的结果集来获得结果的数量
	 * 
	 * @param query
	 *            Hibernate Query Object
	 * @return 结果的数量
	 */
	protected int getTotalResultsByQuery(Query query) {
		ScrollableResults scroll = query.scroll();
		scroll.last();
		int rowNumber = scroll.getRowNumber();
		return rowNumber + 1;
	}

	/**
	 * 获取Dao对应的DetachedCriteria
	 * 
	 * @return Dao对应的DetachedCriteria
	 */
	protected DetachedCriteria getDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass);
	}

	/**
	 * 添加Criterion及Order
	 * 
	 * @param hc
	 *            HibernateCriteria
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            a alias list
	 * @return 添加了Criterion和Order的DetachedCriteria
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
	 * 填加Criterion，支持nested properties
	 * 
	 * @param criterions
	 *            Criterions
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            a alias list
	 * @return 添加了Criterion的DetachedCriteria
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
	 * 为DetachedCriteria创建alias
	 * 
	 * @param path
	 *            Path
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            Alias List
	 * @return 创建alias后的DetachedCriteria
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
	 * 添加排序条件，支持nested properties
	 * 
	 * @param orders
	 *            排序条件
	 * @param c
	 *            DetachedCriteria
	 * @param aliasList
	 *            a alias list
	 * @return 添加了Order的DetachedCriteria
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
	 * 将查询条件填加到指定DetachedCriteria上
	 * 
	 * @param criterion
	 *            要填加的查询条件对应的HibernateCriterion对象
	 * @param c
	 *            DetachedCriteria
	 */
	private static void addCriterion(HibernateCriterion criterion,
			DetachedCriteria c) {
		c.add(criterion.getCriterion());
	}

	/**
	 * 将排序条件填加到指定DetachedCriteria上，一般情况下不要直接调用本方法
	 * 
	 * @param order
	 *            要填加的排序条件对应的HibernateOrder对象
	 * @param c
	 *            DetachedCriteria
	 */
	private static void addOrder(HibernateOrder order, DetachedCriteria c) {
		c.addOrder(order.getOrder());
	}
	
	/**
	 * 向子类暴露获取当前Session的方法
	 * @return
	 */
	protected Session getCurrSession(){
		return sf.getCurrentSession();
	}
	
	
	/**
	 * 根据map查询
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
	 * 根据map查询
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
	 * 根据SQL语句及参数查询
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
				
		// 设置开始的记录号
		query.setFirstResult((page - 1) * pageSize);		
		// 设置取的最大记录数
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
