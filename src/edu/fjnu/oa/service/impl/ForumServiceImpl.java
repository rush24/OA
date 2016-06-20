package edu.fjnu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fjnu.oa.base.DaoSupportImpl;
import edu.fjnu.oa.domain.Forum;
import edu.fjnu.oa.service.ForumService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService{

	public List<Forum> findAll(){
		return getSession().createQuery(
				"FROM Forum f ORDER BY f.position")
				.list();
	}
	
	public void save(Forum forum){
		super.save(forum);
		forum.setPosition(forum.getId().intValue());
	}
	
	@Override
	public void moveUp(Long id) {
		Forum forum = getById(id);
		Forum other = (Forum) getSession().createQuery(
				"FROM Forum f WHERE f.position<? ORDER BY f.position DESC")
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();
		if(other == null)	return;
		
		int tempId = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(tempId);
		//依靠hibernate的数据持久化,可以不用执行更新操作
		
	}

	@Override
	public void moveDown(Long id) {
		Forum forum = getById(id);
		Forum other = (Forum) getSession().createQuery(
				"FROM Forum f WHERE f.position>? ORDER BY f.position ASC")
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();
		if(other == null)	return;
		
		int tempId = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(tempId);
		//依靠hibernate的数据持久化,可以不用执行更新操作
		
	}

	
}
