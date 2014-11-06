package net.sxinfo.core.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * ����ִ��ָ�����µ�ȫ��TestSuite
 * 
 * @version $Revision: 1.4 $
 * @author �ܺ�
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
        TestSuite suite = new TestSuite("net.sxinfo.core.util���Ĳ���");
        // $JUnit-BEGIN$
        suite.addTestSuite(ClassUtilsTest.class);
        suite.addTestSuite(CookieUtilsTest.class);
        // $JUnit-END$
        return suite;
    }
}
