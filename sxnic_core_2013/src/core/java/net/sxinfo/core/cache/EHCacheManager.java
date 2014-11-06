package net.sxinfo.core.cache;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CacheManager��EHCacheʵ��
 *
 * @version $Revision: 1.4 $, $Date: 2006/04/25 09:42:32 $
 * @author fenny
 * @author Cao Hao
 */
public class EHCacheManager implements CacheManager {

    /**
     * commons logging
     */
    private static final Log LOG = LogFactory.getLog(EHCacheManager.class);

    /**
     * EHCache��CacheManager����
     */
    private net.sf.ehcache.CacheManager manager;

    /**
     * Cache���ʵ����
     */
    private Map<String, EHCache> instancePool;

    /**
     * ������
     * 
     * @throws CacheException �ڳ�ʼ��CacheManager���̳����쳣
     */
    public EHCacheManager() throws CacheException {
        instancePool = new ConcurrentHashMap<String, EHCache>();

        try {
            manager = net.sf.ehcache.CacheManager.create();
        } catch (Exception e) {
            LOG.error("�ڳ�ʼ��EHCacheManagerʱ����", e);
            throw new CacheException("�ڳ�ʼ��EHCacheManagerʱ����", e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.CacheManager
     *      #getCache(java.lang.String)
     */
    public Cache getCache(String name) throws CacheException {
        // �Ƿ���ʵ�����У�
        if (instancePool.containsKey(name)) {
            return instancePool.get(name);
        }

        // ����ʵ�����У�������������򷵻�
        if (!manager.cacheExists(name)) {
            // ���Cache�����ھʹ���һ��
            try {
                manager.addCache(name);
            } catch (Exception e) {
                LOG.error("�����EHCacheʵ����CacheManagerʱ����", e);
                throw new CacheException("�����EHCacheʵ����CacheManagerʱ����", e);
            }
        }

        EHCache cache = new EHCache(manager.getCache(name));
        instancePool.put(name, cache);
        return cache;
    }

    /**
     * @see net.sxinfo.core.cache.CacheManager#getCaches()
     */
    public List<Cache> getCaches() throws CacheException {
        List<Cache> list = new LinkedList<Cache>();

        for (Cache cache : instancePool.values()) {
            list.add(cache);
        }

        return list;
    }

    /**
     * @see net.sxinfo.core.cache.CacheManager#clearAll()
     */
    public void clearAll() throws CacheException {
        try {
            List<Cache> list = getCaches();

            for (Cache cache : list) {
                EHCache e = (EHCache) cache;
                // ��״̬������ʱ������������
                if (e.getEHCache().getStatus() == Status.STATUS_ALIVE) {
                    cache.clear();
                }
            }
        } catch (Exception e) {
            LOG.error("�����ȫ������ʱ����", e);
            throw new CacheException("�����ȫ������ʱ����", e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.CacheManager#removeCache(java.lang.String)
     */
    public void removeCache(String name) {
        instancePool.remove(name);
        manager.removeCache(name);
    }

    /**
     * ��ȡ��˵�EHCache��CacheManagerʵ��
     *
     * @return ��˵�EHCache��CacheManagerʵ��
     */
    public net.sf.ehcache.CacheManager getEHCacheManager() {
        return manager;
    }
}
