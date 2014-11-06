package net.sxinfo.core;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.sxinfo.core.cache.CacheTestSuite;
import net.sxinfo.core.dao.DaoTestSuite;
import net.sxinfo.core.event.EventTestSuite;
import net.sxinfo.core.util.UtilTestSuite;

/**
 * 用来执行全部TestSuite
 * 
 * @version $Revision: 1.5 $
 * @author 曹浩
 */
public class AllTests {

    /**
     * main method
     * 
     * @param args Arguments
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    /**
     * Test Suites
     * 
     * @return Test Suites
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("All Tests");
        // $JUnit-BEGIN$
        suite.addTest(DaoTestSuite.suite());
        suite.addTest(UtilTestSuite.suite());
        suite.addTest(CacheTestSuite.suite());
        suite.addTest(EventTestSuite.suite());
        // $JUnit-END$
        return suite;
    }
}
