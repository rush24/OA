package edu.fjnu.oa.service;

import java.util.List;

import edu.fjnu.oa.base.DaoSupport;
import edu.fjnu.oa.domain.Department;
import edu.fjnu.oa.domain.Role;

public interface DepartmentService extends DaoSupport<Department>{
	
	public List<Department> findTopList();
	
	public List<Department> findChildren(Long parentId);

//	public List<Department> findAll();
//
//	public void delete(Long id);
//
//	public Department getById(Long id);
//
//	public void save(Department model);
//
//	public void update(Department department);

}
