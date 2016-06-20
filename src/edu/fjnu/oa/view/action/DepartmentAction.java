package edu.fjnu.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import edu.fjnu.oa.base.BaseAction;
import edu.fjnu.oa.domain.Department;
import edu.fjnu.oa.service.DepartmentService;
import edu.fjnu.oa.util.DepartmentUtils;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{
	
	private Long parentId;

	/**
	 * 查询所有
	 * @return
	 */
	public String list(){
		List<Department> departmentList = null;
		if (parentId == null) { // 顶级部门列表
			departmentList = departmentService.findTopList();
		} else { // 子部门列表
			departmentList = departmentService.findChildren(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}

	/**
	 * 删除部门
	 * @return
	 */
	public String delete(){
		departmentService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String addUI(){
		// 准备数据, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}
	
	/**
	 * 添加部门
	 * @return
	 */
	public String add(){
		Department parent = departmentService.getById(parentId);
		model.setParent(parent);
		departmentService.save(model);
		return "toList";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	public String editUI(){
		// 准备数据, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//准备回显数据
		model = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(model);
		if(model.getParent() != null){
			parentId = model.getParent().getId();
		}
		return "saveUI";
	}
	
	/**
	 * 编辑部门
	 * @return
	 */
	public String edit(){
		Department department = departmentService.getById(model.getId());
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));
		departmentService.update(department);
		return "toList";
	}
	 
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	
}
