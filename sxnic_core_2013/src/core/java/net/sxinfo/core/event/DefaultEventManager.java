package net.sxinfo.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 默认EventManager实现
 *
 * @version $Revision: 1.2 $
 * @author 曹浩
 */
public class DefaultEventManager implements EventManager {

    /**
     * Commons Logging
     */
    private static final Log LOG = LogFactory.getLog(DefaultEventManager.class);

    /**
     * 根据Listener可处理的Event分类后的Map
     */
    private Map<Class, List> listenersByEvent;

    /**
     * 全局Listener，任何事件发生都将发布给全局Listener
     */
    private List<EventListener> globalListeners;

    /**
     * 默认构造器
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
     * 将指定Event发布给指定列表中的全部EventListener
     *
     * @param event 要发布的Event
     * @param listeners 接收Event的EventListener列表
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
     * 将EventListener加入到列表中。
     * 列表根据EventListener能处理的Event分类。如果
     * 一个EvnetListener没有设定可处理的Event的话就
     * 填加到Global Listeners列表中，这个列表中的Listener
     * 在任何事件出现的时候都会被调用。
     *
     * @param clazz 用于分类的Class
     * @param listener 要填加的EventListener
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
     * 将EventListener从列表中删除。
     *
     * @param clazz 用于分类的Class
     * @param listener 要删除的EventListener
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
