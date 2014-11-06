package net.sxinfo.core.cache;

import java.util.List;

/**
 * Cache管理类,使用此类来创建相应的
 * Cache实现,每一个Cache实现都应该有
 * 一个对应的CacheManager实现.
 * <p>
 * 用于包装底层caching实现.
 * 提供了一组最简单,独立于实现的api,这样
 * 就可以在需要时灵活切换caching实现.
 *
 * @version $Revision: 1.3 $, $Date: 2005/08/09 02:57:53 $
 * @author 曹浩
 */
public interface CacheManager {

    /**
     * 构造指定名称的cache并返回
     *
     * @param name cache名称
     * @return 一个cache对象
     * @throws CacheException 包装在获取Cache过程中出现的异常
     */
    Cache getCache(String name) throws CacheException;

    /**
     * 获取全部Cache
     *
     * @return 全部Cache
     * @throws CacheException 包装在获取全部Cache过程中出现的异常
     */
    List getCaches() throws CacheException;

    /**
     * 移除一个缓存
     *
     * @param name 缓存的名称
     * @throws CacheException 包装在移除Cache过程中出现的异常
     */
    void removeCache(String name) throws CacheException;

    /**
     * 清除全部缓存内容
     * 
     * @throws CacheException 包装在清除缓存内容过程中出现的异常
     */
    void clearAll() throws CacheException;
}
