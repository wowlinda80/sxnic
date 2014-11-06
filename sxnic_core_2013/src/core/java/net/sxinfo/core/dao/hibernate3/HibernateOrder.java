package net.sxinfo.core.dao.hibernate3;

import org.hibernate.criterion.Order;

/**
 * Hibernate Order
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public class HibernateOrder {

    /**
     * ��������
     */
    private Order order;

    /**
     * Path
     */
    private String path;

    /**
     * ������
     *
     * @param order ��������
     */
    public HibernateOrder(Order order) {
        this.order = order;
    }

    /**
     * ������
     *
     * @param path Path
     * @param order ��������
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
