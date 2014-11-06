package net.sxinfo.core.cache;

/**
 * @version $Revision: 1.1 $, $Date: 2005/08/05 03:43:30 $
 * @author ²ÜºÆ
 */
public class EHCacheTest extends AbstractCacheTest {

    public void testGetName() {
        Cache cache = getCache();

        assertEquals("testEHCache", cache.getName());
    }

    public Cache getCache() {
        new EHCacheManager().removeCache("testEHCache");
        Cache cache = new EHCacheManager().getCache("testEHCache");
        cache.clear();

        return cache;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(EHCacheTest.class);
    }
}
