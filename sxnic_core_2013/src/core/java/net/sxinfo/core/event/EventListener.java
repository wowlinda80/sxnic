package net.sxinfo.core.event;

/**
 * ����һ��Listener
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public interface EventListener {

    /**
     * ����������ᱻEventManager����
     *
     * @param event ������Listener����Ĺ����¼�����������
     *              ���磺�����¼���Դ�����¼���һЩ���Ե�
     */
    void handleEvent(Event event);

    /**
     * ��Listener��������¼��࣬����EventManager��Ч�ķַ�
     * �¼�����ӦListener
     *
     * @return һ��������Listener��������¼����б������
     */
    Class[] getHandledEventClasses();
}
