package net.sxnic.comm.feedback.action;

@SuppressWarnings("serial")
public class Edit extends FeedbackAction{
	
	public String execute() throws Exception{
		
		feedback = feedbackManager.get(id);
		
		return SUCCESS;
	}

}
