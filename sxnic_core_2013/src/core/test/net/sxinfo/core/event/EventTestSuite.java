package net.sxinfo.core.event;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * ����ִ��ָ�����µ�ȫ��TestSuite
 * 
 * @version $Revision: 1.1 $
 * @author �ܺ�
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
        TestSuite suite = new TestSuite("net.sxinfo.core.event���Ĳ���");
        // $JUnit-BEGIN$
        suite.addTestSuite(DefaultEventManagerTest.class);
        // $JUnit-END$
        return suite;
    }
}
