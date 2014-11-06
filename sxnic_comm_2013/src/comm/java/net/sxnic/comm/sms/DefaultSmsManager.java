package net.sxnic.comm.sms;

import java.util.List;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.sms.dao.SmsDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("smsManager")
public class DefaultSmsManager extends DefaultManager<Sms, SmsDao, String>
		implements SmsManager {

	private static Logger logger = LoggerFactory
			.getLogger(DefaultSmsManager.class);

	@Autowired
	private SmsDao smsDao;

	@Override
	protected Dao<Sms, String> getEntityDao() {
		return smsDao;
	}

	@Override
	public void addInfo(String title, String assignee, String entityId,
			String content, String url) {
		Sms sms = new Sms();
		sms.setTitle(title);
		sms.setReceiver(assignee);
		sms.setContent(content);
		sms.setUrl(url);
		sms.setEntityId(entityId);

		smsDao.save(sms);
	}

	@Override
	public void deleteByEntityId(String entityId) {
		List<Sms> list = smsDao.findBy("entityId", entityId);
		if(list==null || list.size()==0){
			return;
		}
		Sms sms = list.get(0);
		smsDao.delete(sms);

		logger.debug("===删除消息成功===" + sms.getTitle());
	}

	@Override
	public void updateStatus(String entityId, boolean readed) {
		List<Sms> list = smsDao.findBy("entityId", entityId);
		if(list==null || list.size()==0){
			return;
		}
		
		Sms sms = list.get(0);
		sms.setReaded(readed);
		smsDao.update(sms);

		logger.debug("===设置消息状态成功==readed===" + readed);
	}

	@Override
	public List<Sms> findLastestUnReadedByReceiver(String receiver) {
		return smsDao.findBySql("from Sms where receiver = ? and readed=? order by creationDate", new Object[]{receiver,false});
	}
}
