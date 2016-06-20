package edu.fjnu.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.fjnu.oa.base.BaseAction;
import edu.fjnu.oa.domain.Department;
import edu.fjnu.oa.domain.Role;
import edu.fjnu.oa.domain.User;
import edu.fjnu.oa.util.DepartmentUtils;


@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	
	private Long departmentId;
	private Long[] roleIds;

	/**
	 * 查询所有
	 * @return
	 */
	public String list(){
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}

	/**
	 * 删除部门
	 * @return
	 */
	public String delete(){
		userService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String addUI(){
		//准备departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//准备roleList
		List<Role> roleList = roleService.findAll();
		for(Role role : roleList){
			System.out.println(role.getName());
		}
		ActionContext.getContext().put("roleList", roleList);
		
		return "saveUI";
	}
	
	/**
	 * 添加部门
	 * @return
	 */
	public String add(){
		model.setDepartment(departmentService.getById(departmentId));
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		//设置默认密码为1234（要使用MD5摘要）
		String md5Digest = DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);
		// 保存到数据库
		userService.save(model);
		return "toList";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	public String editUI(){
		//准备departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//准备roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		
		//准备回显的数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if(user.getDepartment() != null){
			departmentId = user.getDepartment().getId();
		}
		if(user.getRoles() != null){
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for(Role role : user.getRoles()){
				roleIds[index++] = role.getId();
			}
		}
		
		return "saveUI";
	}
	
	/**
	 * 编辑部门
	 * @return
	 */
	public String edit(){
		User user = userService.getById(model.getId());
		user.setDepartment(departmentService.getById(departmentId));
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));
		
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		
		userService.update(user);
		return "toList";
	}

	/**
	 * 初始化密码
	 * @return
	 */
	public String initPassword(){
		User user = userService.getById(model.getId());
		//设置默认密码为1234（要使用MD5摘要）
		String md5Digest = DigestUtils.md5Hex("1234");
		user.setPassword(md5Digest);
		userService.update(user);
		return "toList";
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	public String loginUI(){
		return "loginUI";
	}
	
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		User user = userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if(user == null){
			addFieldError("login", "用户名或密码不正确！");
			return "loginUI";
		}else{
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}  
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
