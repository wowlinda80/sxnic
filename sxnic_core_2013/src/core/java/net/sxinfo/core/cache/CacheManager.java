package net.sxinfo.core.cache;

import java.util.List;

/**
 * Cache������,ʹ�ô�����������Ӧ��
 * Cacheʵ��,ÿһ��Cacheʵ�ֶ�Ӧ����
 * һ����Ӧ��CacheManagerʵ��.
 * <p>
 * ���ڰ�װ�ײ�cachingʵ��.
 * �ṩ��һ�����,������ʵ�ֵ�api,����
 * �Ϳ�������Ҫʱ����л�cachingʵ��.
 *
 * @version $Revision: 1.3 $, $Date: 2005/08/09 02:57:53 $
 * @author �ܺ�
 */
public interface CacheManager {

    /**
     * ����ָ�����Ƶ�cache������
     *
     * @param name cache����
     * @return һ��cache����
     * @throws CacheException ��װ�ڻ�ȡCache�����г��ֵ��쳣
     */
    Cache getCache(String name) throws CacheException;

    /**
     * ��ȡȫ��Cache
     *
     * @return ȫ��Cache
     * @throws CacheException ��װ�ڻ�ȡȫ��Cache�����г��ֵ��쳣
     */
    List getCaches() throws CacheException;

    /**
     * �Ƴ�һ������
     *
     * @param name ���������
     * @throws CacheException ��װ���Ƴ�Cache�����г��ֵ��쳣
     */
    void removeCache(String name) throws CacheException;

    /**
     * ���ȫ����������
     * 
     * @throws CacheException ��װ������������ݹ����г��ֵ��쳣
     */
    void clearAll() throws CacheException;
}
