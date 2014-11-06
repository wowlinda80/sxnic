package net.sxinfo.core.dao;

/**
 * 包装在持久化过程中出现的异常，由于持久化过程
 * 中出现的异常几乎是没有有效的手段去恢复的，所以
 * 本异常是RuntimeException
 *
 * @version $Revision: 1.3 $
 * @author 曹浩
 */
public class PersistenceException extends RuntimeException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -3582160448076254238L;

    /**
     * 默认构造器
     */
    public PersistenceException() {
        super();
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     */
    public PersistenceException(String msg) {
        super(msg);
    }

    /**
     * 构造器
     * 
     * @param t Root Cause
     */
    public PersistenceException(Throwable t) {
        super(t);
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     * @param t Root Cause
     */
    public PersistenceException(String msg, Throwable t) {
        super(msg, t);
    }
}
