package edu.fjnu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fjnu.oa.base.DaoSupportImpl;
import edu.fjnu.oa.domain.Forum;
import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.domain.Reply;
import edu.fjnu.oa.domain.Topic;
import edu.fjnu.oa.service.ReplyService;

@Service
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService{
	
	public void save(Reply reply){
		getSession().save(reply);
		
		// 2，维护相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();

		forum.setArticleCount(forum.getArticleCount() + 1); // 文章数量（主题数+回复数）
		topic.setReplyCount(topic.getReplyCount() + 1); // 回复数量
		topic.setLastReply(reply); // 最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime()); // 最后更新时间（主题的发表时间或最后回复的时间）

		getSession().update(topic);
		getSession().update(forum);

	}



}
