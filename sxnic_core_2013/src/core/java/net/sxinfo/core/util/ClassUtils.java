package net.sxinfo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 一些用于类载入的工具
 *
 * @version $Revision: 1.3 $, $Date: 2005/08/09 03:00:23 $
 * @author Cao Hao
 */
public final class ClassUtils {

    /**
     * private构造器
     */
    private ClassUtils() {
        // 防止被实例化
    }

    /**
     * 根据给定的类名创建实例
     *
     * @param className 类名
     * @param callingClass 调用本方法的类
     * @return 根据给定的类名创建的实例
     * @throws Exception 任何实例化过程中出现的异常
     */
    public static Object newInstance(String className, Class callingClass)
        throws Exception {

        return loadClass(className, callingClass).newInstance();
    }

    /**
     * 载入指定类
     *
     * @param className 要载入的类的完整类名
     * @param callingClass 调用本方法的类
     * @return 指定的类
     * @throws ClassNotFoundException 如果载入过程中出现错误就抛出
     */
    public static Class loadClass(String className, Class callingClass)
        throws ClassNotFoundException {

        Class theClass = null;

        // 使用三种方法尝试载入类
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
     * 获取指定的InputStream
     *
     * @param resourceName 指定的资源名
     * @param callingClass 调用的类名
     * @return 指定资源的InputStream对象
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
     * 载入指定资源的url
     *
     * @param resourceName 资源名
     * @param callingClass 调用的类
     * @return 指定资源的url
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
     * 获取指定资源名称对应的的URL
     *
     * @param resourceName 要获取URL的资源名称
     * @param callingClass 调用类
     * @return 指定资源名称对应的的URL
     */
    private static URL getResourceURL(String resourceName, Class callingClass) {

        URL url = null;

        // 使用三中方法尝试载入资源的url
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
