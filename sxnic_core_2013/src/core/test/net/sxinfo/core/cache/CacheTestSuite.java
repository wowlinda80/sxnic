package net.sxinfo.core.cache;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @version $Revision: 1.1 $, $Date: 2005/08/05 03:43:30 $
 * @author �ܺ�
 */
public class CacheTestSuite {

    public static void main(String[] args) {
        TestRunner.main(args);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("net.sxinfo.core.cache����ȫ������");
        //$JUnit-BEGIN$
        suite.addTestSuite(EHCacheTest.class);
        suite.addTestSuite(EHCacheManagerTest.class);
        //$JUnit-END$
        return suite;
    }
}
