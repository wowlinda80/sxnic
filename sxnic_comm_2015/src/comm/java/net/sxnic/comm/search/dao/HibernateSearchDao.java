package net.sxnic.comm.search.dao;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.search.Search;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository("searchDao")
public class HibernateSearchDao extends HibernateDao<Search, String> implements
		SearchDao {

	@Override
	public Search findByEntityIdBelonger(String entityId, String belonger) {

		Search s = null;

		try {
			if (StringUtils.isBlank(belonger)) {
				s = findUnique("from Search where entityId = ? ",
						new Object[] { entityId });
			} else {
				s = findUnique(
						"from Search where entityId = ? and belonger = ?",
						new Object[] { entityId, belonger });
			}

		} catch (Exception e) {
			return null;
		}

		return s;
	}

}
