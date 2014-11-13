package net.sxinfo.core.spring25;

import java.io.Serializable;
import java.util.List;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.PersistenceException;
import net.sxinfo.core.dao.WrongObjectTypeException;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;

import org.hibernate.criterion.DetachedCriteria;

public abstract class DefaultManager<T, EntityDao extends Dao<T, PK>, PK extends Serializable>
		implements Manager<T, PK> {

	protected abstract Dao<T, PK> getEntityDao();

	
	public void clear() throws PersistenceException {
		this.getEntityDao().clear();
	}


	public void update(T entity) throws WrongObjectTypeException,
			PersistenceException {
		getEntityDao().update(entity);

	}

	
	public void delete(T entity) throws WrongObjectTypeException,
			PersistenceException {
		getEntityDao().delete(entity);

	}

	
	public void deleteById(Serializable PK) throws PersistenceException {
		getEntityDao().deleteById(PK);
	}

	
	public T get(Serializable PK) throws PersistenceException {
		return getEntityDao().get(PK);
	}

	
	public List<T> getAll() {
		return getEntityDao().getAll();
	}

	
	public Page getPage(int page, int pageSize, String orderProperty,
			boolean asc) throws PersistenceException {
		return getEntityDao().getPage(page, pageSize, orderProperty, asc);
	}

	
	public Page getPageByCriteria(int page, int pageSize, HibernateCriteria hc)
			throws PersistenceException {
		return getEntityDao().getPageByCriteria(page, pageSize, hc);
	}
	
	public Page getPageByDetachedCriteria(int page, int pageSize,
			DetachedCriteria dc) throws PersistenceException{
		return getEntityDao().getPageByDetachedCriteria(page, pageSize, dc);
	}


	public void save(T entity) throws WrongObjectTypeException,
			PersistenceException {
		getEntityDao().save(entity);
	}


	public void deleteByIds(PK[] ids) throws PersistenceException {
		for (PK id : ids) {
			getEntityDao().deleteById(id);
		}
	}


	public void deleteEntitys(List<T> entitys) throws WrongObjectTypeException,
			PersistenceException {
		for (T entity : entitys) {
			delete(entity);
		}
	}


	public void save(List<T> entitys) throws WrongObjectTypeException,
			PersistenceException {
		for (T entity : entitys) {
			save(entity);
		}
	}
	

	public List<T> getByCriteria(HibernateCriteria hc){
		return this.getEntityDao().getByCriteria(hc);
	}
	
	
	public List<T> getByDetachedCriteria(DetachedCriteria dc){
		return this.getEntityDao().getByDetachedCriteria(dc);
	}
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value){
		return this.getEntityDao().findBy(propertyName, value);
	}
	
	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findByUnique(final String propertyName, final Object value){
		return this.getEntityDao().findByUnique(propertyName, value);
	}
	
	/**
	 * 根据SQL语句及参数查询。sql中不能出现select *。格式from Entity where...
	 * @return
	 */
	public List<T> findBySql(String sql,final Object[] values){
		return this.getEntityDao().findBySql(sql, values);
	}
	
	/**
	 * 根据SQL语句及参数查询。 sql中不能出现select * 。格式from Entity where...
	 * @return
	 */
	public Page getPageBySql(int page, int pageSize, Class<T> entityClass, String sql,final Object[] values){
		return this.getEntityDao().getPageBySql(page, pageSize, entityClass, sql, values);
	}

}
