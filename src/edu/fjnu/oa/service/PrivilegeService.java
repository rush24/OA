package edu.fjnu.oa.service;

import java.util.Collection;
import java.util.List;

import edu.fjnu.oa.base.DaoSupport;
import edu.fjnu.oa.domain.Privilege;

public interface PrivilegeService extends DaoSupport<Privilege>{

	List<Privilege> findTopList();

	Collection<String> getAllPrivilegeUrls();

}
