package net.sxinfo.core.cache;

import junit.framework.TestCase;

/**
 * @version $Revision: 1.1 $, $Date: 2005/08/05 03:43:30 $
 * @author ²ÜºÆ
 */
public abstract class AbstractCacheTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AbstractCacheTest.class);
    }

    public void testPut() {
        Cache cache = getCache();

        cache.put("test1key", "test1value");
        assertEquals("test1value", cache.get("test1key"));
    }

    public void testGet() {
        Cache cache = getCache();

        cache.put("test2key", "test2value");
        assertEquals("test2value", cache.get("test2key"));
    }

    public void testRemove() {
        Cache cache = getCache();

        cache.put("test1key", "test1value");
        assertNotNull(cache.get("test1key"));
        cache.remove("test1key");
        assertNull(cache.get("test1key"));
    }

    public void testClear() {
        Cache cache = getCache();

        cache.put("test1key", "test1value");
        cache.put("test2key", "test2value");
        cache.put("test3key", "test3value");

        cache.clear();

        assertNull(cache.get("test1key"));
        assertNull(cache.get("test2key"));
        assertNull(cache.get("test3key"));
    }

    public void testGetMissCount() {
        Cache cache = getCache();

        cache.get("notExists");
        cache.get("notExists2");

        cache.put("test", "testvalue");
        cache.get("test");

        assertEquals(0, cache.getMissCount());
    }

    public void testGetHitCount() {
        Cache cache = getCache();

        cache.get("notExists");
        cache.get("notExists2");

        cache.put("testHit", "testvalue");
        cache.get("testHit");
        cache.get("testHit");

        assertEquals(0, cache.getHitCount());
    }

    public abstract Cache getCache();
}
