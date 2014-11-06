package net.sxinfo.core.cache;

import java.io.Serializable;

import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cache��EHCacheʵ�֡�
 * �ڳ����쳣��ʱ��ֻ��¼��־�������׳��쳣��
 *
 * @version $Revision: 1.4 $, $Date: 2005/08/09 02:58:23 $
 * @author �ܺ�
 */
public class EHCache implements Cache {

    /**
     * commons logging
     */
    private static final Log LOG = LogFactory.getLog(EHCache.class);

    /**
     * Cache ����
     */
    private String name;

    /**
     * EHCache instance
     */
    private net.sf.ehcache.Cache cache;

    /**
     * ������
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
             * key��value�������ǿ����л���
             * ��ΪEHCacheʹ�����л�������
             * ������洢��������
             */
            Element element = cache.get((Serializable) key);
            if (element != null) {
                return element.getValue();
            }
        } catch (Exception e) {
            LOG.error("��cache: " + name + " ��ȡ����ʱ����, key: " + key, e);
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
            LOG.error("���������cache�г���, key: " + key, e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.Cache#remove(java.lang.Object)
     */
    public void remove(Object key) {
        try {
            cache.remove((Serializable) key);
        } catch (Exception e) {
            LOG.error("ɾ��cache�еĶ���ʱ����, key: " + key, e);
        }
    }

    /**
     * @see net.sxinfo.core.cache.Cache#clear()
     */
    public void clear() {
        try {
            cache.removeAll();
        } catch (Exception e) {
            LOG.error("������cache: " + name + " ʱ����", e);
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
     * ��ȡ��˵�EHCache��Cacheʵ��
     *
     * @return ��˵�EHCache��Cacheʵ��
     */
    public net.sf.ehcache.Cache getEHCache() {
        return cache;
    }
}
