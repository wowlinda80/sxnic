package net.sxinfo.core.dao.hibernate3;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 * Hibernate Criteria£¨∑≈÷√Criteriaº∞Order
 *
 * @version $Revision: 1.1 $, $Date: 2006/10/08 06:48:10 $
 * @author ≤‹∫∆
 */
public class HibernateCriteria {

    public List<HibernateCriterion> criterion;

    public List<HibernateOrder> orders;

    public HibernateCriteria() {
        criterion = new ArrayList<HibernateCriterion>();
        orders = new ArrayList<HibernateOrder>();
    }

    public HibernateCriteria add(HibernateCriterion hc) {
        if (hc != null) {
            criterion.add(hc);
        }

        return this;
    }

    public HibernateCriteria add(HibernateOrder ho) {
        if (ho != null) {
            orders.add(ho);
        }

        return this;
    }

    public HibernateCriteria add(Criterion c) {
        criterion.add(new HibernateCriterion(c));
        return this;
    }

    public HibernateCriteria add(String path, Criterion c) {
        criterion.add(new HibernateCriterion(path, c));
        return this;
    }

    public HibernateCriteria add(Order o) {
        orders.add(new HibernateOrder(o));
        return this;
    }

    public HibernateCriteria add(String path, Order o) {
        orders.add(new HibernateOrder(path, o));
        return this;
    }

    /**
     * @return the criterion
     */
    public List<HibernateCriterion> getCriterion() {
        return criterion;
    }

    /**
     * @return the orders
     */
    public List<HibernateOrder> getOrders() {
        return orders;
    }
}
