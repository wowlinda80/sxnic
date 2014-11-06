package net.sxinfo.core.event;

import java.util.Date;

/**
 * �����¼�����ĳ����࣬���������ڴ���һ���¼�
 * �а��������ݡ���Щ���ݽ����ṩ��Listenerʹ�á�
 *
 * @version $Revision: 1.2 $
 * @author �ܺ�
 */
public abstract class Event {

    /**
     * �¼�������ʱ��
     */
    protected Date date;

    /**
     * Ĭ�Ϲ�����
     */
    public Event() {
        date = new Date();
    }

    /**
     * @return Returns the date.
     */
    public Date getDate() {
        return date;
    }
}
