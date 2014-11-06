package net.sxinfo.core.dao;

/**
 * ���Dao�����Դ���ָ��������׳����쳣
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public class WrongObjectTypeException extends PersistenceException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 529667835369002790L;

    /**
     * Ĭ�Ϲ�����
     */
    public WrongObjectTypeException() {
        super();
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     */
    public WrongObjectTypeException(String msg) {
        super(msg);
    }

    /**
     * ������
     * 
     * @param t Root Cause
     */
    public WrongObjectTypeException(Throwable t) {
        super(t);
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     * @param t Root Cause
     */
    public WrongObjectTypeException(String msg, Throwable t) {
        super(msg, t);
    }
}
