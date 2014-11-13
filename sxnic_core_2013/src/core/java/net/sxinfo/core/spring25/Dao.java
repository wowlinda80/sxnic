package net.sxinfo.core.spring25;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.PersistenceException;
import net.sxinfo.core.dao.WrongObjectTypeException;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Data Access Object的接口，包含了大部分DAO所需要的方法。
 * 
 * @author 孙宇飞
 * @version $Revision: 1.0 $
 *
 * @param <T> 实体类。
 * @param <PK> 实体类的主键类型。可为String,Long等。
 */
public interface Dao<T, PK extends Serializable> {

	 /**
     * 返回指定标识符对应的实体对象，如果没有找到就返回null
     * 
     * @param <T> 本Dao要操作的实体对象
     * @param id 要获取的实体对象的标识符
     * @return 指定标识符对应的实体对象，如果没有找到就返回null
     * @throws PersistenceException 在获取过程中出现的异常
     */
	T get(Serializable PK) throws PersistenceException;
	

	 /**
     * 创建&更新 实体对象
     * 
     * @param o 要保存的实体对象
     * @return 生成的标识符
     * 
     * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
     * @throws PersistenceException 在保存过程中出现的异常
     */
	void save(final T entity) throws WrongObjectTypeException,
			PersistenceException;
	
	/**
     * 更新指定实体对象
     * 
     * @param o 要更新的实体对象
     * 
     * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
     * @throws PersistenceException 在更新过程中出现的异常
     */
	void update(final T entity) throws WrongObjectTypeException,
	PersistenceException;

	/**
     * 删除指定实体对象
     * 
     * @param o 要删除的实体对象
     * 
     * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
     * @throws PersistenceException 在删除过程中出现的异常
     */
	void delete(final T entity) throws WrongObjectTypeException,
			PersistenceException;

	 /**
     * 删除指定id对应的实体对象
     * 
     * @param PK 要删除的实体对象的id
     * 
     * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
     * @throws PersistenceException 在删除过程中出现的异常
     */
	void deleteById(Serializable PK) throws PersistenceException;

	/**
	 * 清空指定类型的实体表
	 * @throws PersistenceException
	 */
	void clear() throws PersistenceException;

	 /**
     * 载入指定类型的全部实体对象
     */
	List<T> getAll();

	 /**
     * 载入指定类型的分页后的实体对象
     * 
     * @param page 当前页码
     * @param pageSize 一页所包含的记录数
     * @return 指定类型的分页后的实体对象
     * @throws PersistenceException 在获取过程中出现的异常
     */
	Page getPage(int page, int pageSize, String orderProperty, boolean asc)
			throws PersistenceException;

	/**
	 * 载入指定类型的分页后的实体对象
	 * @param page 当前页码
	 * @param pageSize 一页所包含的记录数
	 * @param hc 查询排序类
	 * @return
	 * @throws PersistenceException 在获取过程中出现的异常
	 */
	Page getPageByCriteria(final int page, final int pageSize,
			HibernateCriteria hc) throws PersistenceException;
	
	/**
	 * 分页查询 ，主要级联查询
	 * @param page 当前页码
	 * @param pageSize 一页所包含的记录数
	 * @param dc 查询排序类
	 * @return
	 */
	Page getPageByDetachedCriteria(int page, int pageSize,
			DetachedCriteria dc) throws PersistenceException;
	
	/**
	 *  查询指定条件HibernateCriteria的List
	 * @param hc
	 * @return
	 */
	List<T> getByCriteria(HibernateCriteria hc);
	
	/**
	 * 根据DetachedCriteria查询List
	 * @param dc
	 * @return
	 */
	List<T> getByDetachedCriteria(DetachedCriteria dc);
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	List<T> findBy(final String propertyName, final Object value);
	
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	T findByUnique(final String propertyName, final Object value);
	
	/**
	 * 根据map查询
	 * @param map
	 * @return
	 */
	List<T> findBy(String entityName,Map<String,Object> map);
	
	/**
	 * 根据SQL语句及参数查询。sql中不能出现select *。格式from Entity where...
	 * @return
	 */
	List<T> findBySql(String sql,final Object[] values);
	
	/**
	 * 根据SQL语句及参数查询。 sql中不能出现select * 。格式from Entity where...
	 * @return
	 */
	Page getPageBySql(int page, int pageSize, Class<T> entityClass, String sql,final Object[] values);

}
