package net.sxinfo.core.cache;

/**
 * Cache接口
 *
 * @version $Revision: 1.2 $, $Date: 2005/08/09 02:57:53 $
 * @author 曹浩
 */
public interface Cache {

    /**
     * 获取缓存的名称
     *
     * @return 缓存的名称
     */
    String getName();

    /**
     * 获取Cache中指定名称的对象
     *
     * @param key 要获取对象的名称
     * @return 名称所对应的Cache中的对象
     * @throws CacheException 在获取缓存内容时出错
     */
    Object get(Object key) throws CacheException;

    /**
     * 向Cache中参加名称为key,值为obj的对象
     *
     * @param key 对象名称
     * @param obj 对象
     * @throws CacheException 在向缓存中填加对象时出错
     */
    void put(Object key, Object obj) throws CacheException;

    /**
     * 删除Cache中指定名称的对象
     *
     * @param key 对象名称
     * @throws CacheException 在删除缓存中的对象时出错
     */
    void remove(Object key) throws CacheException;

    /**
     * 清除Cache中所有的对象
     * @throws CacheException 在清空缓存时出错
     */
    void clear() throws CacheException;

    /**
     * 获取缓存的命中数
     *
     * @return 缓存的命中数
     */
    int getHitCount();

    /**
     * 获取缓存的未命中数
     *
     * @return 缓存的未命中数
     */
    int getMissCount();
}
