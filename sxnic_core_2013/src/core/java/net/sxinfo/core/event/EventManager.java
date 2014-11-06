package net.sxinfo.core.event;

import java.util.List;

/**
 * EventManager interface
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public interface EventManager {

    /**
     * 发布一个事件
     *
     * @param event 事件对应的Event对象
     */
    void publishEvent(Event event);

    /**
     * 注册监听器
     *
     * @param listener 要注册的监听器
     */
    void registerListener(EventListener listener);

    /**
     * 移除监听器
     *
     * @param listener 要移除的监听器
     */
    void unregisterListener(EventListener listener);

    /**
     * 注册列表内的全部监听器
     *
     * @param listeners 要注册的监听器列表
     */
    void setListeners(List<EventListener> listeners);
}
