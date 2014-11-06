package net.sxinfo.core.cache;

/**
 * 在缓存系统出现无法恢复且无法运行的错误时抛出本异常
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class CacheException extends RuntimeException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -6316394948946623186L;

    /**
     * 构造器
     *
     * @param msg 异常信息
     */
    public CacheException(String msg) {
        super(msg);
    }

    /**
     * 构造器
     * 
     * @param t Root Cause
     */
    public CacheException(Throwable t) {
        super(t);
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     * @param t Root Cause
     */
    public CacheException(String msg, Throwable t) {
        super(msg, t);
    }
}
