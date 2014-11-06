package net.sxinfo.core.event;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 用来执行指定包下的全部TestSuite
 * 
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class EventTestSuite {

    /**
     * main method
     * 
     * @param args Arguments
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(EventTestSuite.suite());
    }

    /**
     * Test Suites
     * 
     * @return Test Suites
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("net.sxinfo.core.event包的测试");
        // $JUnit-BEGIN$
        suite.addTestSuite(DefaultEventManagerTest.class);
        // $JUnit-END$
        return suite;
    }
}
