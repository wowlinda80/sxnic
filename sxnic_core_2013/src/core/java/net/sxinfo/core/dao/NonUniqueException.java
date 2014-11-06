package net.sxinfo.core.dao;

/**
 * 如果在要求获取一条数据却返回超过一条
 * 数据时抛出本异常
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class NonUniqueException extends PersistenceException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -682116555320266095L;

    /**
     * 默认构造器
     */
    public NonUniqueException() {
        super();
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     */
    public NonUniqueException(String msg) {
        super(msg);
    }

    /**
     * 构造器
     * 
     * @param t Root Cause
     */
    public NonUniqueException(Throwable t) {
        super(t);
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     * @param t Root Cause
     */
    public NonUniqueException(String msg, Throwable t) {
        super(msg, t);
    }
}
