package net.sxinfo.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Ĭ��EventManagerʵ��
 *
 * @version $Revision: 1.2 $
 * @author �ܺ�
 */
public class DefaultEventManager implements EventManager {

    /**
     * Commons Logging
     */
    private static final Log LOG = LogFactory.getLog(DefaultEventManager.class);

    /**
     * ����Listener�ɴ����Event������Map
     */
    private Map<Class, List> listenersByEvent;

    /**
     * ȫ��Listener���κ��¼���������������ȫ��Listener
     */
    private List<EventListener> globalListeners;

    /**
     * Ĭ�Ϲ�����
     */
    public DefaultEventManager() {
        listenersByEvent = new HashMap<Class, List>();
        globalListeners = new ArrayList<EventListener>();
    }

    /**
     * @see net.sxinfo.core.event.EventManager
     *      #publishEvent(net.sxinfo.core.event.Event)
     */
    public void publishEvent(Event event) {
        publishEvent(event, globalListeners);
        publishEvent(event, listenersByEvent.get(event.getClass()));
    }

    /**
     * ��ָ��Event������ָ���б��е�ȫ��EventListener
     *
     * @param event Ҫ������Event
     * @param listeners ����Event��EventListener�б�
     */
    private void publishEvent(Event event, List<EventListener> listeners) {
        if (listeners == null) {
            return;
        }

        for (EventListener listener : listeners) {
            LOG.debug("Publishing event: " + event + " to " + listener);
            listener.handleEvent(event);
        }
    }

    /**
     * @see net.sxinfo.core.event.EventManager
     *      #registerListener(net.sxinfo.core.event.EventListener)
     */
    public void registerListener(EventListener listener) {
        if (listener.getHandledEventClasses() == null
                || listener.getHandledEventClasses().length == 0) {

            globalListeners.add(listener);
            return;
        }

        for (Class clazz : listener.getHandledEventClasses()) {
            addToListenerList(clazz, listener);
        }
    }

    /**
     * ��EventListener���뵽�б��С�
     * �б����EventListener�ܴ����Event���ࡣ���
     * һ��EvnetListenerû���趨�ɴ����Event�Ļ���
     * ��ӵ�Global Listeners�б��У�����б��е�Listener
     * ���κ��¼����ֵ�ʱ�򶼻ᱻ���á�
     *
     * @param clazz ���ڷ����Class
     * @param listener Ҫ��ӵ�EventListener
     */
    private void addToListenerList(Class clazz, EventListener listener) {
        if (!listenersByEvent.containsKey(clazz)) {
            listenersByEvent.put(clazz, new ArrayList<EventListener>());
        }

        listenersByEvent.get(clazz).add(listener);
    }

    /**
     * @see net.sxinfo.core.event.EventManager
     *      #unregisterListener(net.sxinfo.core.event.EventListener)
     */
    public void unregisterListener(EventListener listener) {
        if (listener.getHandledEventClasses() == null
                || listener.getHandledEventClasses().length == 0) {

            globalListeners.remove(listener);
            return;
        }

        for (Class clazz : listener.getHandledEventClasses()) {
            removeFromListenerList(clazz, listener);
        }
    }

    /**
     * ��EventListener���б���ɾ����
     *
     * @param clazz ���ڷ����Class
     * @param listener Ҫɾ����EventListener
     */
    private void removeFromListenerList(Class clazz, EventListener listener) {
        List<EventListener> list = listenersByEvent.get(clazz);

        if (list != null) {
            list.remove(listener);
        }
    }

    /**
     * @see net.sxinfo.core.event.EventManager
     *      #setListeners(java.util.List)
     */
    public void setListeners(List listeners) {
        for (Object listener : listeners) {
            registerListener((EventListener) listener);
        }
    }
}
