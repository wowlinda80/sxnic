package net.sxinfo.core.event;

import java.util.List;

/**
 * EventManager interface
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public interface EventManager {

    /**
     * ����һ���¼�
     *
     * @param event �¼���Ӧ��Event����
     */
    void publishEvent(Event event);

    /**
     * ע�������
     *
     * @param listener Ҫע��ļ�����
     */
    void registerListener(EventListener listener);

    /**
     * �Ƴ�������
     *
     * @param listener Ҫ�Ƴ��ļ�����
     */
    void unregisterListener(EventListener listener);

    /**
     * ע���б��ڵ�ȫ��������
     *
     * @param listeners Ҫע��ļ������б�
     */
    void setListeners(List<EventListener> listeners);
}
