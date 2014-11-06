package net.sxinfo.core.cache;

/**
 * �ڻ���ϵͳ�����޷��ָ����޷����еĴ���ʱ�׳����쳣
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public class CacheException extends RuntimeException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -6316394948946623186L;

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     */
    public CacheException(String msg) {
        super(msg);
    }

    /**
     * ������
     * 
     * @param t Root Cause
     */
    public CacheException(Throwable t) {
        super(t);
    }

    /**
     * ������
     *
     * @param msg �쳣��Ϣ
     * @param t Root Cause
     */
    public CacheException(String msg, Throwable t) {
        super(msg, t);
    }
}
