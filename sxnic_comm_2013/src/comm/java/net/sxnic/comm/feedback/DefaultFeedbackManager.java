package net.sxnic.comm.feedback;

import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.feedback.dao.FeedbackDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("feedbackManager")
public class DefaultFeedbackManager extends
		DefaultManager<Feedback, FeedbackDao, String> implements
		FeedbackManager {

	@Autowired
	private FeedbackDao feedbacDao;

	@Override
	protected Dao<Feedback, String> getEntityDao() {
		return feedbacDao;
	}

}
