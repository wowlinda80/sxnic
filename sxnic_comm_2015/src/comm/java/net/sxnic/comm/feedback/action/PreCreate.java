package net.sxnic.comm.feedback.action;

import net.sxnic.comm.feedback.Feedback;

@SuppressWarnings("serial")
public class PreCreate extends FeedbackAction{

	
	public String execute() throws Exception{
		
		feedback = new Feedback();
		
		return SUCCESS;
	}

}
