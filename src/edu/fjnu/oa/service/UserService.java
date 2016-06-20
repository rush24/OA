package edu.fjnu.oa.service;

import edu.fjnu.oa.base.DaoSupport;
import edu.fjnu.oa.domain.User;

public interface UserService extends DaoSupport<User>{

	User findByLoginNameAndPassword(String loginName, String password);

}
