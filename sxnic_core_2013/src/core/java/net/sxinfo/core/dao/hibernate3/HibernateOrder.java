package net.sxinfo.core.dao.hibernate3;

import org.hibernate.criterion.Order;

/**
 * Hibernate Order
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class HibernateOrder {

    /**
     * 排序条件
     */
    private Order order;

    /**
     * Path
     */
    private String path;

    /**
     * 构造器
     *
     * @param order 排序条件
     */
    public HibernateOrder(Order order) {
        this.order = order;
    }

    /**
     * 构造器
     *
     * @param path Path
     * @param order 排序条件
     */
    public HibernateOrder(String path, Order order) {
        this(order);
        this.path = path;
    }

    /**
     * @return Returns the order.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }
}
