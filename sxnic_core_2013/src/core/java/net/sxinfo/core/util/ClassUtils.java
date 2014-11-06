package net.sxinfo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * һЩ����������Ĺ���
 *
 * @version $Revision: 1.3 $, $Date: 2005/08/09 03:00:23 $
 * @author Cao Hao
 */
public final class ClassUtils {

    /**
     * private������
     */
    private ClassUtils() {
        // ��ֹ��ʵ����
    }

    /**
     * ���ݸ�������������ʵ��
     *
     * @param className ����
     * @param callingClass ���ñ���������
     * @return ���ݸ���������������ʵ��
     * @throws Exception �κ�ʵ���������г��ֵ��쳣
     */
    public static Object newInstance(String className, Class callingClass)
        throws Exception {

        return loadClass(className, callingClass).newInstance();
    }

    /**
     * ����ָ����
     *
     * @param className Ҫ����������������
     * @param callingClass ���ñ���������
     * @return ָ������
     * @throws ClassNotFoundException �����������г��ִ�����׳�
     */
    public static Class loadClass(String className, Class callingClass)
        throws ClassNotFoundException {

        Class theClass = null;

        // ʹ�����ַ�������������
        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
            try {
                theClass = Thread.currentThread().getContextClassLoader()
                        .loadClass(className);
            } catch (ClassNotFoundException cnfe1) {
                theClass = callingClass.getClassLoader().loadClass(className);
            }
        }

        return theClass;
    }

    /**
     * ��ȡָ����InputStream
     *
     * @param resourceName ָ������Դ��
     * @param callingClass ���õ�����
     * @return ָ����Դ��InputStream����
     */
    public static InputStream getResourceAsStream(String resourceName,
            Class callingClass) {

        URL url = getResource(resourceName, callingClass);

        if (url != null) {
            try {
                return url.openStream();
            } catch (IOException ioe) {
                return null;
            }
        }

        return null;
    }

    /**
     * ����ָ����Դ��url
     *
     * @param resourceName ��Դ��
     * @param callingClass ���õ���
     * @return ָ����Դ��url
     */
    public static URL getResource(String resourceName, Class callingClass) {
        URL url = null;
        url = getResourceURL(resourceName, callingClass);

        if (url == null) {
            url = getResourceURL("/" + resourceName, callingClass);
        }

        return url;
    }

    /**
     * ��ȡָ����Դ���ƶ�Ӧ�ĵ�URL
     *
     * @param resourceName Ҫ��ȡURL����Դ����
     * @param callingClass ������
     * @return ָ����Դ���ƶ�Ӧ�ĵ�URL
     */
    private static URL getResourceURL(String resourceName, Class callingClass) {

        URL url = null;

        // ʹ�����з�������������Դ��url
        url = Thread.currentThread().getContextClassLoader().getResource(
            resourceName);

        if (url == null) {
            url = (ClassUtils.class).getClassLoader().getResource(resourceName);
        }

        if (url == null) {
            url = callingClass.getClassLoader().getResource(resourceName);
        }

        return url;
    }
}
