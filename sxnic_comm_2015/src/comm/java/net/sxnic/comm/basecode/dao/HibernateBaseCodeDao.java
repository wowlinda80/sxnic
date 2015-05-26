package net.sxnic.comm.basecode.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.basecode.BaseCode;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("basecodeDao")
public class HibernateBaseCodeDao extends HibernateDao<BaseCode, String>
		implements BaseCodeDao {

	public Page getAllCodes() {
		return this.getAllCodes();
	}

	public void updateSortCode(String newSortCode, String oldSortCode) {
		getCurrSession().createQuery(
				"update BaseCode set sortCode='" + newSortCode
						+ "' where sortCode = '" + oldSortCode + "'")
				.executeUpdate();
	}

	public void deleteSortBySortCode(String sortCode) {
		getCurrSession().createQuery(
				"delete from BaseCode where sortCode = '" + sortCode + "'")
				.executeUpdate();
	}

	public List getListBySortCode(String sortCode, String orderField) {
		if (orderField == null)
			return this.find("from BaseCode where sortCode = ?",
					new Object[] { sortCode });
		else if (orderField.equals("infoCode"))
			return this.find(
					"from BaseCode where sortCode = ? order by infoCode",
					new Object[] { sortCode });
		else if (orderField.equals("infoIndex"))
			return this.find(
					"from BaseCode where sortCode = ? order by infoIndex",
					new Object[] { sortCode });
		else
			return null;
	}

	public List getListBySortCodeInfoIndex(String sortCode, String infoIndex,
			String orderField) {
		if (orderField == null)
			return this.find(
					"from BaseCode where sortCode = ? and infoIndex=? ",
					new Object[] { sortCode, infoIndex });
		else if (orderField.equals("infoCode"))
			return this
					.find("from BaseCode where sortCode = ? and infoIndex=?  order by infoCode",
							new Object[] { sortCode, infoIndex });
		else if (orderField.equals("ordered"))
			return this
					.find("from BaseCode where sortCode = ? and infoIndex=?  order by ordered",
							new Object[] { sortCode, infoIndex });
		else
			return null;
	}

	public Map<String, List> initMap() {
		Map<String, List> map = new LinkedHashMap<String, List>();
		List list = find("select distinct sortCode from BaseCode ");

		Iterator iter = list.iterator();
		String sortCode = "";
		while (iter.hasNext()) {
			sortCode = (String) iter.next();
			map.put(sortCode, this.getListBySortCode(sortCode, "infoCode"));
		}

		return map;
	}

	public void clearTable() {
		getCurrSession().createQuery("delete BaseCode").executeUpdate();
	}

	public List getSortCodes() {
		return find("select distinct sortCode,sortName from BaseCode order by sortCode");
	}

	public void deleteById(String id) {
		getCurrSession().createQuery("delete BaseCode where id='" + id + "'")
				.executeUpdate();
	}

	public List<BaseCode> getAll() {
		return find("from BaseCode order by sortCode,infoCode");
	}

	public Page getBasecodesOrderBySortCode(int page, int pageSize,
			String orderProperty, boolean asc) {

		HibernateOrder sortCodeOrder = HibernateDaoUtils.createHibernateOrder(
				"sortCode", true);

		HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder(
				"infoCode", true);

		HibernateCriteria hc = new HibernateCriteria().add(sortCodeOrder).add(
				infoCodeOrder);

		if (!StringUtils.isBlank(orderProperty)) {
			HibernateOrder order = HibernateDaoUtils.createHibernateOrder(
					orderProperty, asc);
			hc = hc.add(order);
		}

		return this.getPageByCriteria(page, pageSize, hc);

	}

	public Page getPageBySortCode(int page, int pageSize, String sortCode) {

		HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder(
				"infoCode", true);

		HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);

		Criterion dc = Restrictions.eq("sortCode", sortCode);

		hc.add(dc);

		return this.getPageByCriteria(page, pageSize, hc);
	}

	public Map<String, String> getInfoMap(String sortCode) {
		List<BaseCode> list = this.find(
				"from BaseCode where sortCode=? order by infoIndex,infoCode",
				new Object[] { sortCode });

		Map<String, String> map = new LinkedHashMap<String, String>();

		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			BaseCode bc = (BaseCode) iter.next();
			map.put(bc.getInfoCode(), bc.getInfoName());
		}

		return map;
	}

	public BaseCode getBaseCode(String sortCode, String sortName,
			String infoCode, String infoName) {
		try {
			List<BaseCode> list = find(
					"from BaseCode where sortCode=? and sortName=? and infoCode=? and infoName=?",
					new Object[] { sortCode, sortName, infoCode, infoName });
			if (list == null) {
				return null;
			}

			return list.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public BaseCode getBaseCode(String sortCode, String infoCode) {
		try {
			List<BaseCode> list = find(
					"from BaseCode where sortCode=? and infoCode=? ",
					new Object[] { sortCode, infoCode });
			if (list == null) {
				return null;
			}

			return list.get(0);
		} catch (Exception e) {
			return null;
		}
	}

}
