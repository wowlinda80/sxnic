package net.sxinfo.core.event;

import java.util.Date;

/**
 * 代表事件情况的抽象类，将包含对于处理一个事件
 * 有帮助的内容。这些内容将会提供给Listener使用。
 *
 * @version $Revision: 1.2 $
 * @author 曹浩
 */
public abstract class Event {

    /**
     * 事件发生的时间
     */
    protected Date date;

    /**
     * 默认构造器
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
