package net.sxnic.comm.feedback;

import org.springframework.transaction.annotation.Transactional;

import net.sxinfo.core.spring25.Manager;

@Transactional
public interface FeedbackManager extends Manager<Feedback,String>{

}
