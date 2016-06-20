package edu.fjnu.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fjnu.oa.base.DaoSupportImpl;
import edu.fjnu.oa.dao.RoleDao;
import edu.fjnu.oa.domain.Role;
import edu.fjnu.oa.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService{
	
//	@Resource
//	private RoleDao roleDao;
//
//	@Override
//	public List<Role> findAll() {
//		return roleDao.findAll();
//	}
//
//	@Override
//	public void delete(Long id) {
//		roleDao.delete(id);
//	}
//
//	@Override
//	public void save(Role model) {
//		roleDao.save(model);
//		
//	}
//
//	@Override
//	public Role getById(Long id) {
//		return roleDao.getById(id);
//	}
//
//	@Override
//	public void update(Role role) {
//		roleDao.update(role);
//		
//	}

}
