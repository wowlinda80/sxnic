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
 * Data Access Object�Ľӿڣ������˴󲿷�DAO����Ҫ�ķ�����
 * 
 * @author �����
 * @version $Revision: 1.0 $
 *
 * @param <T> ʵ���ࡣ
 * @param <PK> ʵ������������͡���ΪString,Long�ȡ�
 */
public interface Dao<T, PK extends Serializable> {

	 /**
     * ����ָ����ʶ����Ӧ��ʵ��������û���ҵ��ͷ���null
     * 
     * @param <T> ��DaoҪ������ʵ�����
     * @param id Ҫ��ȡ��ʵ�����ı�ʶ��
     * @return ָ����ʶ����Ӧ��ʵ��������û���ҵ��ͷ���null
     * @throws PersistenceException �ڻ�ȡ�����г��ֵ��쳣
     */
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
	void save(final T entity) throws WrongObjectTypeException,
			PersistenceException;
	
	/**
     * ����ָ��ʵ�����
     * 
     * @param o Ҫ���µ�ʵ�����
     * 
     * @throws WrongObjectTypeException �����Dao�����Դ������ֶ�����׳�
     * @throws PersistenceException �ڸ��¹����г��ֵ��쳣
     */
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
	void deleteById(Serializable PK) throws PersistenceException;

	/**
	 * ���ָ�����͵�ʵ���
	 * @throws PersistenceException
	 */
	void clear() throws PersistenceException;

	 /**
     * ����ָ�����͵�ȫ��ʵ�����
     */
	List<T> getAll();

	 /**
     * ����ָ�����͵ķ�ҳ���ʵ�����
     * 
     * @param page ��ǰҳ��
     * @param pageSize һҳ�������ļ�¼��
     * @return ָ�����͵ķ�ҳ���ʵ�����
     * @throws PersistenceException �ڻ�ȡ�����г��ֵ��쳣
     */
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
	Page getPageByCriteria(final int page, final int pageSize,
			HibernateCriteria hc) throws PersistenceException;
	
	/**
	 * ��ҳ��ѯ ����Ҫ������ѯ
	 * @param page ��ǰҳ��
	 * @param pageSize һҳ�������ļ�¼��
	 * @param dc ��ѯ������
	 * @return
	 */
	Page getPageByDetachedCriteria(int page, int pageSize,
			DetachedCriteria dc) throws PersistenceException;
	
	/**
	 *  ��ѯָ������HibernateCriteria��List
	 * @param hc
	 * @return
	 */
	List<T> getByCriteria(HibernateCriteria hc);
	
	/**
	 * ����DetachedCriteria��ѯList
	 * @param dc
	 * @return
	 */
	List<T> getByDetachedCriteria(DetachedCriteria dc);
	
	/**
	 * �����Բ��Ҷ����б�,ƥ�䷽ʽΪ���.
	 */
	List<T> findBy(final String propertyName, final Object value);
	
	/**
	 * �����Բ���Ψһ����,ƥ�䷽ʽΪ���.
	 */
	T findByUnique(final String propertyName, final Object value);
	
	/**
	 * ����map��ѯ
	 * @param map
	 * @return
	 */
	List<T> findBy(String entityName,Map<String,Object> map);
	
	/**
	 * ����SQL��估������ѯ��sql�в��ܳ���select *����ʽfrom Entity where...
	 * @return
	 */
	List<T> findBySql(String sql,final Object[] values);
	
	/**
	 * ����SQL��估������ѯ�� sql�в��ܳ���select * ����ʽfrom Entity where...
	 * @return
	 */
	Page getPageBySql(int page, int pageSize, Class<T> entityClass, String sql,final Object[] values);

}
