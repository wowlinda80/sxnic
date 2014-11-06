package net.sxinfo.core.dao;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtilsTest;

/**
 * ����ִ��ָ�����µ�ȫ��TestSuite
 * 
 * @version $Revision: 1.2 $
 * @author �ܺ�
 */
public class DaoTestSuite {

    /**
     * main method
     * 
     * @param args Arguments
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(DaoTestSuite.suite());
    }

    /**
     * Test Suites
     * 
     * @return Test Suites
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("net.sxinfo.core.dao���Ĳ���");
        // $JUnit-BEGIN$
        suite.addTestSuite(PageTest.class);

        //suite.addTestSuite(HibernateDaoTest.class);
        suite.addTestSuite(HibernateDaoUtilsTest.class);
        // $JUnit-END$
        return suite;
    }
}
