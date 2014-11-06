package net.sxinfo.core.util;

import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

/**
 * ≤‚ ‘ClassUtils
 *
 * @version $Revision: 1.1 $, $Date: 2005/08/02 09:44:56 $
 * @author Cao Hao
 */
public class ClassUtilsTest extends TestCase {

    public void testNewInstance() {
        try {
            String test = (String) ClassUtils.newInstance("java.lang.String",
                ClassUtilsTest.class);
        } catch (Exception e) {
            fail();
        }
    }

    public void testLoadClass() {
        try {
            Class test = ClassUtils.loadClass("java.lang.String",
                ClassUtilsTest.class);
        } catch (ClassNotFoundException cnfe) {
            fail();
        }
    }

    public void testLoadClass2() {
        try {
            Class test = ClassUtils
                    .loadClass("notExists", ClassUtilsTest.class);

            fail();
        } catch (ClassNotFoundException cnfe) {
            // ignore
        }
    }

    public void testGetResource() {
        URL url = ClassUtils.getResource("log4j.xml", ClassUtilsTest.class);
        assertNotNull(url);
    }

    public void testGetResource2() {
        URL url = ClassUtils.getResource("notExists.xml", ClassUtilsTest.class);

        assertNull(url);
    }

    public void testGetResourceAsStream() {
        InputStream in = ClassUtils.getResourceAsStream("log4j.xml",
            ClassUtilsTest.class);

        assertNotNull(in);
    }

    public void testGetResourceAsStream2() {
        InputStream in = ClassUtils.getResourceAsStream("notExists.xml",
            ClassUtilsTest.class);

        assertNull(in);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ClassUtilsTest.class);
    }
}
