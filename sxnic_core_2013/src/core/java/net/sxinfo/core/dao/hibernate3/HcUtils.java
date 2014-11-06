package net.sxinfo.core.dao.hibernate3;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class HcUtils {

	public static HibernateCriteria creat(Map<String, Object> hcMap,
			Map<String, Boolean> sortMap) {

		HibernateCriteria hc = new HibernateCriteria();
		if (hcMap == null || hcMap.size() == 0) {
			return hc;
		}

		HibernateOrder order = null;

		Criterion dc = null;

		for (String h : hcMap.keySet()) {
			dc = Restrictions.eq(h, hcMap.get(h));
			hc.add(dc);
		}

		if (sortMap == null || sortMap.size() == 0) {
			return hc;
		}

		for (String s : sortMap.keySet()) {
			order = HibernateDaoUtils.createHibernateOrder(s, sortMap.get(s));
			hc.add(order);
		}

		return hc;
	}

}
