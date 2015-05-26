package net.sxnic.comm.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.search.dao.SearchDao;

@Service("searchManager")
public class DefaultSearchManager extends DefaultManager<Search, SearchDao, String> implements SearchManager{

	@Autowired
	private SearchDao searchDao;
	
	@Override
	protected Dao<Search, String> getEntityDao() {		
		return searchDao;
	}

	@Override
	public Search findByEntityIdBelonger(String entityId, String belonger) {		
		return searchDao.findByEntityIdBelonger(entityId, belonger);
	}

}
