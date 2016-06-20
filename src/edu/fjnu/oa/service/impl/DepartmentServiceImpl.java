package edu.fjnu.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fjnu.oa.base.DaoSupportImpl;
import edu.fjnu.oa.dao.DepartmentDao;
import edu.fjnu.oa.domain.Department;
import edu.fjnu.oa.service.DepartmentService;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements DepartmentService{
	
//	@Resource
//	private DepartmentDao departmentDao;
	
//	@Resource
//	private SessionFactory sessionFactory;

	public List<Department> findTopList() {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent IS NULL")//
				.list();
	}

	public List<Department> findChildren(Long parentId) {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent.id=?")//
				.setParameter(0, parentId)//
				.list();
	}

//	@Override
//	public List<Department> findAll() {
//		return departmentDao.findAll();
//	}
//
//	@Override
//	public void delete(Long id) {
//		departmentDao.delete(id);
//	}
//
//	@Override
//	public Department getById(Long id) {
//		return departmentDao.getById(id);
//	}
//
//	@Override
//	public void update(Department department) {
//		departmentDao.update(department);
//	}
//
//	@Override
//	public void save(Department model) {
//		departmentDao.save(model);
//		
//	}


}
