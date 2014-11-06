package net.sxnic.comm.feedback.action;

import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.feedback.Feedback;
import net.sxnic.comm.feedback.FeedbackManager;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class FeedbackAction extends CommActionSupport{
	
	protected String id;
	
	protected Feedback feedback;
	
	@Autowired
	protected FeedbackManager feedbackManager;

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
