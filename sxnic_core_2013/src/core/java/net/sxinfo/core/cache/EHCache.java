package net.sxinfo.core.cache;

import java.io.Serializable;

import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cache的EHCache实现。
 * 在出现异常的时候只记录日志，而不抛出异常。
 *
 * @version $Revision: 1.4 $, $Date: 2005/08/09 02:58:23 $
 * @author 曹浩
 */
public class EHCache implements Cache {

    /**
     * commons logging
     */
    private static final Log LOG = LogFactory.getLog(EHCache.class);

    /**
     * Cache 名称
     */
    private String name;

    /**
     * EHCache instance
     */
    private net.sf.ehcache.Cache cache;

    /**
     * 构造器
     * 
     * @param cache a EHCache instance
     */
    public EHCache(net.sf.ehcache.Cache cache) {
        this.cache = cache;
        this.name = cache.getName();
    }

    /**
     * @see net.sxinfo.core.cache.Cache#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see net.sxinfo.core.cache.Cache#get(java.lang.Object)
     */
    public Object get(Object key) {
        try {

            /*
             * key和value都必须是可序列化的
             * 因为EHCache使用序列化机制来
             * 将对象存储到磁盘上
             */
            Element element = cache.get((Serializable) key);
            if (element != null) {
                return element.getValue();
            }
        } catch (Exception e) {
            LOG.error("从cache: " + name + " 中取对象时出错, key: " + key, e);
        }

        return null;
    }

    /**
     * @see net.sxinfo.core.cache.Cache
     * #put(java.lang.Object,
     *      java.lang.Object)
     */
    public void put(Object key, Object obj) {
        try {
            Element element = new Element((Serializable) key,
                    (Serializable) obj);
            cache.put(element);
        } catch (Exception e) {
            LOG.error("将对象存入cache中出错, key: " + key, e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.Cache#remove(java.lang.Object)
     */
    public void remove(Object key) {
        try {
            cache.remove((Serializable) key);
        } catch (Exception e) {
            LOG.error("删除cache中的对象时出错, key: " + key, e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.Cache#clear()
     */
    public void clear() {
        try {
            cache.removeAll();
        } catch (Exception e) {
            LOG.error("在清理cache: " + name + " 时出错", e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.Cache#getHitCount()
     */
    public int getHitCount() {
       // return cache.getHitCount();
    	return 0;
    }

    /**
     * @see net.sxinfo.core.cache.Cache#getMissCount()
     */
    public int getMissCount() {
        //return cache.getMissCountExpired() + cache.getMissCountNotFound();
    	return 0;
    }

    /**
     * 获取后端的EHCache的Cache实例
     *
     * @return 后端的EHCache的Cache实例
     */
    public net.sf.ehcache.Cache getEHCache() {
        return cache;
    }
}
