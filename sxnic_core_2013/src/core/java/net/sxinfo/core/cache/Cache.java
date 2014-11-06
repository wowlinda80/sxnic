package net.sxinfo.core.cache;

/**
 * Cache�ӿ�
 *
 * @version $Revision: 1.2 $, $Date: 2005/08/09 02:57:53 $
 * @author �ܺ�
 */
public interface Cache {

    /**
     * ��ȡ���������
     *
     * @return ���������
     */
    String getName();

    /**
     * ��ȡCache��ָ�����ƵĶ���
     *
     * @param key Ҫ��ȡ���������
     * @return ��������Ӧ��Cache�еĶ���
     * @throws CacheException �ڻ�ȡ��������ʱ����
     */
    Object get(Object key) throws CacheException;

    /**
     * ��Cache�вμ�����Ϊkey,ֵΪobj�Ķ���
     *
     * @param key ��������
     * @param obj ����
     * @throws CacheException ���򻺴�����Ӷ���ʱ����
     */
    void put(Object key, Object obj) throws CacheException;

    /**
     * ɾ��Cache��ָ�����ƵĶ���
     *
     * @param key ��������
     * @throws CacheException ��ɾ�������еĶ���ʱ����
     */
    void remove(Object key) throws CacheException;

    /**
     * ���Cache�����еĶ���
     * @throws CacheException ����ջ���ʱ����
     */
    void clear() throws CacheException;

    /**
     * ��ȡ�����������
     *
     * @return �����������
     */
    int getHitCount();

    /**
     * ��ȡ�����δ������
     *
     * @return �����δ������
     */
    int getMissCount();
}
