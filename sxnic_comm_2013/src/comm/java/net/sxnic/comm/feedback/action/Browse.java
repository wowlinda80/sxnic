package net.sxnic.comm.feedback.action;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.util.WebUtils;

@SuppressWarnings("serial")
public class Browse extends FeedbackAction{
	
	public Page feedbacks;		

	public String execute() throws Exception {

		int pageNumber = WebUtils.getPageNumber(request);
		int pageSize = WebUtils.getPageSize(request);

		feedbacks = feedbackManager.getPage(pageNumber, pageSize, "id",true);
		
		return SUCCESS;
	}

	public Page getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Page feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	
	

}
