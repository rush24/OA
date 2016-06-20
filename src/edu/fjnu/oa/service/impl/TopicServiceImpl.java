package edu.fjnu.oa.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fjnu.oa.base.DaoSupportImpl;
import edu.fjnu.oa.domain.Forum;
import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.domain.Topic;
import edu.fjnu.oa.service.TopicService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService{

	@Override
	public List<Topic> findByForum(Forum forum) {
		
		return getSession().createQuery(
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC")
				.setParameter(0, forum)
				.list();
	}
	
	public void save(Topic topic){
		// 1，设置属性并保存
		topic.setPostTime(new Date()); // 当前时间
		topic.setType(Topic.TYPE_NORMAL); // 默认为普通帖
		topic.setReplyCount(0);
		topic.setLastReply(null);
		topic.setLastUpdateTime(topic.getPostTime());
		getSession().save(topic); // 保存
		
		// 2，维护相关的特殊属性
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount() + 1); // 主题数量
		forum.setArticleCount(forum.getArticleCount() + 1);// 文章数量（主题数+回复数）
		forum.setLastTopic(topic); // 最后发表的主题
		getSession().update(forum);

	}


}
