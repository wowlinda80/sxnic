package net.sxnic.comm.sms.action;

import java.util.List;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.sms.Sms;
import net.sxnic.comm.sms.SmsManager;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * <p>使用Ajax方式，来获取最新的信息
 * @author 孙宇飞
 * @version v1.0.0
 * @creationDate 2012-7-23
 * @moidfyRecords
 */
@SuppressWarnings("serial")
public class AjaxFindNewSms extends SmsAction {

	private String cate;

	private String content;

	private String title;

	@Autowired
	private SmsManager smsManager;

	public String execute() throws Exception {
		List<Sms> list = smsManager.findLastestUnReadedByReceiver((String)request
				.getSession().getAttribute(CommConstant.APPCONTEXT_USERNAME));
		if (list == null || list.size() == 0) {
			content = "您现在有<font color='red'>10</font>条待办任务。";
		} else {
			content = "您现在有<font color='red'>" + list.size() + "</font>条待办任务。";
		}

		return SUCCESS;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
