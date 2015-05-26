package net.sxnic.comm.sms;

import java.util.List;

import net.sxinfo.core.spring25.Manager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SmsManager extends Manager<Sms, String>{
	
	/**
	 * 保存一条SMS信息
	 * @param title 标题
	 * @param assignee 接收人
	 * @param entityId 实体ID
	 * @param content 内容
	 * @param url 访问URL
	 */
	void addInfo(String title, String assignee,String entityId, String content, String url);
	
	/**
	 * 根据Id删除实体类
	 * @param entityId
	 */
	void deleteByEntityId(String entityId);
	
	/**
	 * 根据Id更新状态
	 * @param entityId
	 * @param readed 标志
	 */
	void updateStatus(String entityId,boolean readed);
	
	/**
	 * 根据发送人查询最新的未读短信
	 * @param receiver
	 * @return
	 */
	List<Sms> findLastestUnReadedByReceiver(String receiver);

}
