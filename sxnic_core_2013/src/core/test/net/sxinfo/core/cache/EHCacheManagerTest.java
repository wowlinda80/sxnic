package net.sxinfo.core.cache;

import java.util.List;

import junit.framework.TestCase;

/**
 * @version $Revision: 1.2 $, $Date: 2005/08/05 07:47:17 $
 * @author 曹浩
 */
public class EHCacheManagerTest extends TestCase {

    protected EHCacheManager manager;

    public void setUp() throws Exception {
        manager = new EHCacheManager();

        net.sf.ehcache.CacheManager cm = manager.getEHCacheManager();
        cm.shutdown();

        manager = new EHCacheManager();
    }

    public void testGetCache() {
        Cache cache = manager.getCache("testEHCacheManager");
        assertNotNull(cache);
    }

    public void testRemoveCache() {
        Cache cache = manager.getCache("testEHCacheManager");
        cache.put("test", "testvalue");

        manager.removeCache("testEHCacheManager");

        assertNull(manager.getCache("testEHCacheManager").get("test"));
    }

    public void testClearAll() {
        Cache cache = manager.getCache("testEHCacheManager");
        cache.put("test", "testvalue");

        manager.clearAll();

        assertNull(manager.getCache("testEHCacheManager").get("test"));
    }

    public void testGetCaches() {
        Cache cache = manager.getCache("testEHCacheManager");
        Cache cache2 = manager.getCache("testEHCacheManager2");

        List l = manager.getCaches();

        assertEquals(2, l.size());

        // index 1和 index 2必须是cache和cache2无论顺序如何
        if (!((l.get(0) == cache && l.get(1) == cache2) || (l.get(0) == cache2 && l
                .get(1) == cache))) {

            fail();
        }
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(EHCacheManagerTest.class);
    }
}
