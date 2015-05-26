package net.sxnic.comm.sms.dao;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.sms.Sms;

import org.springframework.stereotype.Repository;

@Repository("smsDao")
public class HibernateSmsDao extends HibernateDao<Sms,String> implements SmsDao{

}
