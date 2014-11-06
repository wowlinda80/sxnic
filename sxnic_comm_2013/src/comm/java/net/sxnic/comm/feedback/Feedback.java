package net.sxnic.comm.feedback;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 信息反馈实体类
 * @author 孙宇飞
 *
 */
@Entity
@Table(name = "comm_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback extends AbstractEntity{

	private static final long serialVersionUID = 2866493303794477572L;
	
	private String reporter;
	
	private String asker;
	
	private String askerLink;
	
	private String expert;
	
	@Lob
	private String content;
	
	@Lob
	private byte[] bcontent;
	
	

	public Feedback() {
		super();
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getAsker() {
		return asker;
	}

	public void setAsker(String asker) {
		this.asker = asker;
	}

	public String getAskerLink() {
		return askerLink;
	}

	public void setAskerLink(String askerLink) {
		this.askerLink = askerLink;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getBcontent() {
		return bcontent;
	}

	public void setBcontent(byte[] bcontent) {
		this.bcontent = bcontent;
	}

}
