package net.sxinfo.core.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 用来执行指定包下的全部TestSuite
 * 
 * @version $Revision: 1.4 $
 * @author 曹浩
 */
public class UtilTestSuite {

    /**
     * main method
     * 
     * @param args Arguments
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(UtilTestSuite.suite());
    }

    /**
     * Test Suites
     * 
     * @return Test Suites
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("net.sxinfo.core.util包的测试");
        // $JUnit-BEGIN$
        suite.addTestSuite(ClassUtilsTest.class);
        suite.addTestSuite(CookieUtilsTest.class);
        // $JUnit-END$
        return suite;
    }
}
