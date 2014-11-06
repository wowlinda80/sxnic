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
     * ����ָ����ʶ����Ӧ��ʵ��������û���ҵ��ͷ���null
     * 
     * @param <T> ��DaoҪ������ʵ�����
     * @param id Ҫ��ȡ��ʵ�����ı�ʶ��
     * @return ָ����ʶ����Ӧ��ʵ��������û���ҵ��ͷ���null
     * @throws PersistenceException �ڻ�ȡ�����г��ֵ��쳣
     */
	@Transactional(readOnly = true)
	T get(Serializable PK) throws PersistenceException;
	

	 /**
     * ����&���� ʵ�����
     * 
     * @param o Ҫ�����ʵ�����
     * @return ���ɵı�ʶ��
     * 
     * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
     * @throws PersistenceException �ڱ�������г��ֵ��쳣
     */
	@Transactional(readOnly = false)
	void save(final T entity) throws WrongObjectTypeException,
			PersistenceException;
	
	/**
	 * ����&���� һ��ʵ�����
	 * @param entitys ʵ�����List
	 * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
	 * @throws PersistenceException �ڱ�������г��ֵ��쳣
	 */
	@Transactional(readOnly = false)
	void save(List<T> entitys) throws WrongObjectTypeException,
	PersistenceException;
	
	/**
     * ����ָ��ʵ�����
     * 
     * @param o Ҫ���µ�ʵ�����
     * 
     * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
     * @throws PersistenceException �ڸ��¹����г��ֵ��쳣
     */
	@Transactional(readOnly = false)
	void update(final T entity) throws WrongObjectTypeException,
	PersistenceException;

	/**
     * ɾ��ָ��ʵ�����
     * 
     * @param o Ҫɾ����ʵ�����
     * 
     * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
     * @throws PersistenceException ��ɾ�������г��ֵ��쳣
     */
	@Transactional(readOnly = false)
	void delete(final T entity) throws WrongObjectTypeException,
			PersistenceException;

	 /**
     * ɾ��ָ��id��Ӧ��ʵ�����
     * 
     * @param PK Ҫɾ����ʵ������id
     * 
     * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
     * @throws PersistenceException ��ɾ�������г��ֵ��쳣
     */
	@Transactional(readOnly = false)
	void deleteById(Serializable PK) throws PersistenceException;
	
	/**
	 * ɾ��ָ��ids��Ӧ��ʵ�����
	 * 
	 * @param ids Ҫɾ����ʵ������id����
	 * 
	 * @throws PersistenceException
	 */
	@Transactional(readOnly = false)
	void deleteByIds(PK[] ids) throws PersistenceException;
	
	
	/**
     * ɾ��ָ��ʵ�����List
     * 
     * @param o Ҫɾ����ʵ�����
     * 
     * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
     * @throws PersistenceException ��ɾ�������г��ֵ��쳣
     */
	@Transactional(readOnly = false)
	void deleteEntitys(List<T> entitys) throws WrongObjectTypeException, PersistenceException;

	/**
	 * ���ָ�����͵�ʵ���
	 * @throws PersistenceException
	 */
	@Transactional(readOnly = false)
	void clear() throws PersistenceException;

	 /**
     * ����ָ�����͵�ȫ��ʵ�����
     */
	@Transactional(readOnly = true)
	List<T> getAll();

	 /**
     * ����ָ�����͵ķ�ҳ���ʵ�����
     * 
     * @param page ��ǰҳ��
     * @param pageSize һҳ�������ļ�¼��
     * @return ָ�����͵ķ�ҳ���ʵ�����
     * @throws PersistenceException �ڻ�ȡ�����г��ֵ��쳣
     */
	@Transactional(readOnly = true)
	Page getPage(int page, int pageSize, String orderProperty, boolean asc)
			throws PersistenceException;

	/**
	 * ����ָ�����͵ķ�ҳ���ʵ�����
	 * @param page ��ǰҳ��
	 * @param pageSize һҳ�������ļ�¼��
	 * @param hc ��ѯ������
	 * @return
	 * @throws PersistenceException �ڻ�ȡ�����г��ֵ��쳣
	 */
	@Transactional(readOnly = true)
	Page getPageByCriteria(final int page, final int pageSize,
			HibernateCriteria hc) throws PersistenceException;
	
	/**
	 * ��ҳ��ѯ ����Ҫ�û�������ѯ
	 * @param page ��ǰҳ��
	 * @param pageSize һҳ�������ļ�¼��
	 * @param dc ��ѯ������
	 * @return
	 */
	Page getPageByDetachedCriteria(int page, int pageSize,
			DetachedCriteria dc) throws PersistenceException;
	
	/**
	 * ��ѯָ������HibernateCriteria��List
	 * @param hc
	 * @return
	 */
	@Transactional(readOnly = true)
	List<T> getByCriteria(HibernateCriteria hc);
	
	/**
	 * �����Բ��Ҷ����б�,ƥ�䷽ʽΪ���.
	 */
	@Transactional(readOnly = true)
	List<T> findBy(final String propertyName, final Object value);
	
	/**
	 * �����Բ���Ψһ����,ƥ�䷽ʽΪ���.
	 */
	@Transactional(readOnly = true)
	T findByUnique(final String propertyName, final Object value);
	
	/**
	 * ����SQL��估������ѯ��sql�в��ܳ���select *����ʽfrom Entity where...
	 * @return
	 */
	@Transactional(readOnly = true)
	List<T> findBySql(String sql,final Object[] values);
	
	/**
	 * ����SQL��估������ѯ�� sql�в��ܳ���select * ����ʽfrom Entity where...
	 * @return
	 */
	@Transactional(readOnly = true)
	Page findPageBySql(int page, int pageSize, Class entityClass, String sql,final Object[] values);
}
