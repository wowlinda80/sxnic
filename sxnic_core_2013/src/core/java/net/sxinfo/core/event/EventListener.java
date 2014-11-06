package net.sxinfo.core.event;

/**
 * 代表一个Listener
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public interface EventListener {

    /**
     * 这个方法将会被EventManager调用
     *
     * @param event 包含了Listener所需的关于事件发生的内容
     *              例如：发起事件的源对象，事件的一些属性等
     */
    void handleEvent(Event event);

    /**
     * 本Listener所处理的事件类，便于EventManager高效的分发
     * 事件给对应Listener
     *
     * @return 一个包含本Listener所处理的事件类列表的数组
     */
    Class[] getHandledEventClasses();
}
