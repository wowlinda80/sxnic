package net.sxinfo.core.dao;

/**
 * �����Ҫ���ȡһ������ȴ���س���һ��
 * ����ʱ�׳����쳣
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public class NonUniqueException extends PersistenceException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -682116555320266095L;

    /**
     * Ĭ�Ϲ�����
     */
    public NonUniqueException() {
        super();
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     */
    public NonUniqueException(String msg) {
        super(msg);
    }

    /**
     * ������
     * 
     * @param t Root Cause
     */
    public NonUniqueException(Throwable t) {
        super(t);
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     * @param t Root Cause
     */
    public NonUniqueException(String msg, Throwable t) {
        super(msg, t);
    }
}
