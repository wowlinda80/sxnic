package net.sxnic.comm.hsz.dao;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.hsz.Hsz;

import org.springframework.stereotype.Repository;

@Repository("hszDao")
public class HibernateHszDao extends HibernateDao<Hsz, String> implements
		HszDao {

	@Override
	public void recover(String id) {
		Hsz h = get(id);
		if (h == null) {
			return;
		}

		String sql = "update " + h.getEntityName()
				+ " set enabled=true where id=?";

		batchExecute(sql, new Object[] { h.getEntityId() });

	}

}
