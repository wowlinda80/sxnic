package net.sxnic.comm.sms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统内消息和短信 实体类
 * @author 孙宇飞
 *
 */
@Entity
@Table(name = "comm_sms")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sms extends AbstractEntity{

	private static final long serialVersionUID = -6330018529812475145L;
	
	/**
	 * 001待办任务  002系统内邮件 003 手机消息
	 */
	private String cate;
	
	/**
	 * 模块名
	 */
	@Column(name="module_")
	private String module;
	
	/**
	 * 发送者或者手机号码
	 */
	private String sender;
	
	/**
	 * 接受者或者手机号码
	 */
	private String receiver;
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 *  实体类Id
	 */
	private String entityId;
	/**
	 * 内容
	 */
	@Lob
	@Column(name="content_")
	private String content;
	
	/**
	 * 访问URL
	 */
	private String url;
	
	/**
	 * 站内消息，是否已阅读
	 */
	private boolean readed;
	
	/**
	 * 如果需要发送短消息，此字段标志是否已发送
	 */
	private boolean sended;

	public Sms() {
		super();
		readed= false;
		sended= false;
		cate = "001";
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public boolean isSended() {
		return sended;
	}

	public void setSended(boolean sended) {
		this.sended = sended;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
		
	
	
}
