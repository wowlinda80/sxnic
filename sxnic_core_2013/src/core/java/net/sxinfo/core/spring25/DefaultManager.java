package net.sxinfo.core.spring25;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.PersistenceException;
import net.sxinfo.core.dao.WrongObjectTypeException;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;

public abstract class DefaultManager<T, EntityDao extends Dao, PK extends Serializable>
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
	
	/**
	 * �����Բ��Ҷ����б�,ƥ�䷽ʽΪ���.
	 */
	public List<T> findBy(final String propertyName, final Object value){
		return this.getEntityDao().findBy(propertyName, value);
	}
	
	/**
	 * �����Բ���Ψһ����,ƥ�䷽ʽΪ���.
	 */
	public T findByUnique(final String propertyName, final Object value){
		return this.getEntityDao().findByUnique(propertyName, value);
	}
	
	/**
	 * ����SQL��估������ѯ��sql�в��ܳ���select *����ʽfrom Entity where...
	 * @return
	 */
	public List<T> findBySql(String sql,final Object[] values){
		return this.getEntityDao().findBySql(sql, values);
	}
	
	/**
	 * ����SQL��估������ѯ�� sql�в��ܳ���select * ����ʽfrom Entity where...
	 * @return
	 */
	public Page findPageBySql(int page, int pageSize, Class entityClass, String sql,final Object[] values){
		return this.getEntityDao().findPageBySql(page, pageSize, entityClass, sql, values);
	}

}