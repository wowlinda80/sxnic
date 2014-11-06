package net.sxinfo.core.dao;

/**
 * ��װ�ڳ־û������г��ֵ��쳣�����ڳ־û�����
 * �г��ֵ��쳣������û����Ч���ֶ�ȥ�ָ��ģ�����
 * ���쳣��RuntimeException
 *
 * @version $Revision: 1.3 $
 * @author �ܺ�
 */
public class PersistenceException extends RuntimeException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -3582160448076254238L;

    /**
     * Ĭ�Ϲ�����
     */
    public PersistenceException() {
        super();
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     */
    public PersistenceException(String msg) {
        super(msg);
    }

    /**
     * ������
     * 
     * @param t Root Cause
     */
    public PersistenceException(Throwable t) {
        super(t);
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     * @param t Root Cause
     */
    public PersistenceException(String msg, Throwable t) {
        super(msg, t);
    }
}
