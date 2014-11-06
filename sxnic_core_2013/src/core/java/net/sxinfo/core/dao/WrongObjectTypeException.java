package net.sxinfo.core.dao;

/**
 * 如果Dao不可以处理指定对象就抛出本异常
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class WrongObjectTypeException extends PersistenceException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 529667835369002790L;

    /**
     * 默认构造器
     */
    public WrongObjectTypeException() {
        super();
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     */
    public WrongObjectTypeException(String msg) {
        super(msg);
    }

    /**
     * 构造器
     * 
     * @param t Root Cause
     */
    public WrongObjectTypeException(Throwable t) {
        super(t);
    }

    /**
     * 构造器
     *
     * @param msg 异常信息
     * @param t Root Cause
     */
    public WrongObjectTypeException(String msg, Throwable t) {
        super(msg, t);
    }
}
