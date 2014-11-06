package net.sxnic.comm.feedback.dao;

import org.springframework.stereotype.Repository;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.feedback.Feedback;

@Repository("feedbackDao")
public class HibernateFeedbackDao extends HibernateDao<Feedback, String>
		implements FeedbackDao {

}
