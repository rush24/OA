package edu.fjnu.oa.service;

import java.util.List;

import edu.fjnu.oa.base.DaoSupport;
import edu.fjnu.oa.domain.Forum;
import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.domain.Topic;

public interface TopicService extends DaoSupport<Topic>{

	List<Topic> findByForum(Forum forum);


}
