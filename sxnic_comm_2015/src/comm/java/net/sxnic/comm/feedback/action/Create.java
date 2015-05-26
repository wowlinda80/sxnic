package net.sxnic.comm.feedback.action;

@SuppressWarnings("serial")
public class Create extends FeedbackAction {

	public String execute() throws Exception {

		feedbackManager.save(feedback);

		return SUCCESS;
	}

}
