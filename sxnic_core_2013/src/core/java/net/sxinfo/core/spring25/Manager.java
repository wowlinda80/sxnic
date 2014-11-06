package net.sxinfo.core.spring25;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.PersistenceException;
import net.sxinfo.core.dao.WrongObjectTypeException;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;


public interface Manager<T, PK extends Serializable> {

	/**
     * 返回指定标识符对应的实体对象，如果没有找到就返回null
     * 
     * @param <T> 本Dao要操作的实体对象
     * @param id 要获取的实体对象的标识符
     * @return 指定标识符对应的实体对象，如果没有找到就返回null
     * @throws PersistenceException 在获取过程中出现的异常
     */
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = false)
	void save(final T entity) throws WrongObjectTypeException,
			PersistenceException;
	
	/**
	 * 创建&更新 一组实体对象
	 * @param entitys 实体对象List
	 * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
	 * @throws PersistenceException 在保存过程中出现的异常
	 */
	@Transactional(readOnly = false)
	void save(List<T> entitys) throws WrongObjectTypeException,
	PersistenceException;
	
	/**
     * 更新指定实体对象
     * 
     * @param o 要更新的实体对象
     * 
     * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
     * @throws PersistenceException 在更新过程中出现的异常
     */
	@Transactional(readOnly = false)
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
	@Transactional(readOnly = false)
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
	@Transactional(readOnly = false)
	void deleteById(Serializable PK) throws PersistenceException;
	
	/**
	 * 删除指定ids对应的实体对象
	 * 
	 * @param ids 要删除的实体对象的id数组
	 * 
	 * @throws PersistenceException
	 */
	@Transactional(readOnly = false)
	void deleteByIds(PK[] ids) throws PersistenceException;
	
	
	/**
     * 删除指定实体对象List
     * 
     * @param o 要删除的实体对象
     * 
     * @throws WrongObjectTypeException 如果本Dao不可以处理这种对象就抛出
     * @throws PersistenceException 在删除过程中出现的异常
     */
	@Transactional(readOnly = false)
	void deleteEntitys(List<T> entitys) throws WrongObjectTypeException, PersistenceException;

	/**
	 * 清空指定类型的实体表
	 * @throws PersistenceException
	 */
	@Transactional(readOnly = false)
	void clear() throws PersistenceException;

	 /**
     * 载入指定类型的全部实体对象
     */
	@Transactional(readOnly = true)
	List<T> getAll();

	 /**
     * 载入指定类型的分页后的实体对象
     * 
     * @param page 当前页码
     * @param pageSize 一页所包含的记录数
     * @return 指定类型的分页后的实体对象
     * @throws PersistenceException 在获取过程中出现的异常
     */
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	Page getPageByCriteria(final int page, final int pageSize,
			HibernateCriteria hc) throws PersistenceException;
	
	/**
	 * 分页查询 ，主要用户级联查询
	 * @param page 当前页码
	 * @param pageSize 一页所包含的记录数
	 * @param dc 查询排序类
	 * @return
	 */
	Page getPageByDetachedCriteria(int page, int pageSize,
			DetachedCriteria dc) throws PersistenceException;
	
	/**
	 * 查询指定条件HibernateCriteria的List
	 * @param hc
	 * @return
	 */
	@Transactional(readOnly = true)
	List<T> getByCriteria(HibernateCriteria hc);
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	@Transactional(readOnly = true)
	List<T> findBy(final String propertyName, final Object value);
	
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	@Transactional(readOnly = true)
	T findByUnique(final String propertyName, final Object value);
	
	/**
	 * 根据SQL语句及参数查询。sql中不能出现select *。格式from Entity where...
	 * @return
	 */
	@Transactional(readOnly = true)
	List<T> findBySql(String sql,final Object[] values);
	
	/**
	 * 根据SQL语句及参数查询。 sql中不能出现select * 。格式from Entity where...
	 * @return
	 */
	@Transactional(readOnly = true)
	Page findPageBySql(int page, int pageSize, Class entityClass, String sql,final Object[] values);
}
