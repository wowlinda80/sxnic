package net.sxnic.comm.log;

import java.util.List;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.log.dao.LogDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("logManager")
public class DefaultLogManager extends DefaultManager<Log, LogDao, String>
		implements LogManager {

	@Autowired
	private LogDao logDao;

	@Override
	protected Dao<Log, String> getEntityDao() {
		return logDao;
	}


	public List<Log> getLogsByUserName(String userName) {
		return null;
	}


	public Page getPageByUserName(String userName) {
		return null;
	}

}
