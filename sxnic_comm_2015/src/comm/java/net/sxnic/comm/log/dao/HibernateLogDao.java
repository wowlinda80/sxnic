package net.sxnic.comm.log.dao;

import org.springframework.stereotype.Repository;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.log.Log;

@Repository("logDao")
public class HibernateLogDao extends HibernateDao<Log, String> implements
		LogDao {

}
