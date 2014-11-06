package net.sxinfo.core.cache;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CacheManager的EHCache实现
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
     * EHCache的CacheManager对象
     */
    private net.sf.ehcache.CacheManager manager;

    /**
     * Cache类的实例池
     */
    private Map<String, EHCache> instancePool;

    /**
     * 构造器
     * 
     * @throws CacheException 在初始化CacheManager过程出现异常
     */
    public EHCacheManager() throws CacheException {
        instancePool = new ConcurrentHashMap<String, EHCache>();

        try {
            manager = net.sf.ehcache.CacheManager.create();
        } catch (Exception e) {
            LOG.error("在初始化EHCacheManager时出错", e);
            throw new CacheException("在初始化EHCacheManager时出错", e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.CacheManager
     *      #getCache(java.lang.String)
     */
    public Cache getCache(String name) throws CacheException {
        // 是否在实例池中？
        if (instancePool.containsKey(name)) {
            return instancePool.get(name);
        }

        // 不在实例池中，根据情况创建或返回
        if (!manager.cacheExists(name)) {
            // 如果Cache不存在就创建一个
            try {
                manager.addCache(name);
            } catch (Exception e) {
                LOG.error("在填加EHCache实例到CacheManager时出错", e);
                throw new CacheException("在填加EHCache实例到CacheManager时出错", e);
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
                // 在状态正常的时候才做清除操作
                if (e.getEHCache().getStatus() == Status.STATUS_ALIVE) {
                    cache.clear();
                }
            }
        } catch (Exception e) {
            LOG.error("在清除全部缓存时出错", e);
            throw new CacheException("在清除全部缓存时出错", e);
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
     * 获取后端的EHCache的CacheManager实例
     *
     * @return 后端的EHCache的CacheManager实例
     */
    public net.sf.ehcache.CacheManager getEHCacheManager() {
        return manager;
    }
}
