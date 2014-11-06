package net.sxnic.comm.hsz;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.hsz.dao.HszDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hszManager")
public class DefaultHszManager extends DefaultManager<Hsz, HszDao, String>
		implements HszManager {

	@Autowired
	private HszDao hszDao;

	@Override
	protected Dao<Hsz, String> getEntityDao() {
		return hszDao;
	}

	@Override
	public void recover(String id) {
		hszDao.recover(id);
	}

}
