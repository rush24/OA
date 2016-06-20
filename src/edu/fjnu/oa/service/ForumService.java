package edu.fjnu.oa.service;

import edu.fjnu.oa.base.DaoSupport;
import edu.fjnu.oa.domain.Forum;

public interface ForumService extends DaoSupport<Forum>{

	/**
	 * 上移板块
	 * @param id
	 */
	void moveUp(Long id);
	
	/**
	 * 下移板块
	 * @param id
	 */
	void moveDown(Long id);
}
