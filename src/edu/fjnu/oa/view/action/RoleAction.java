package edu.fjnu.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.fjnu.oa.base.BaseAction;
import edu.fjnu.oa.domain.Privilege;
import edu.fjnu.oa.domain.Role;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	
	private Long[] privilegeIds;

	/**
	 * 查询所有
	 * @return
	 */
	public String list(){
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "list";
	}

	/**
	 * 删除岗位
	 * @return
	 */
	public String delete(){
		roleService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String addUI(){
		return "saveUI";
	}
	
	/**
	 * 添加岗位
	 * @return
	 */
	public String add(){
		roleService.save(model);
		return "toList";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	public String editUI(){
		//准备回显数据
		model = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(model);
		return "saveUI";
	}
	
	/**
	 * 编辑岗位
	 * @return
	 */
	public String edit(){
		Role role = roleService.getById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "toList";
	}
	
	/**
	 * 设置权限页面
	 * @return
	 */
	public String setPrivilegeUI(){
		//准备回显数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);
		
		//准备已有的权限数据回显
		if (role.getPrivileges() != null) {
			privilegeIds = new Long[role.getPrivileges().size()];
			int index = 0;
			for (Privilege priv : role.getPrivileges()) {
				privilegeIds[index++] = priv.getId();
			}
		}

		// 准备数据 privilegeList
		List<Privilege> privilegeList = privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", privilegeList); 
		
		return "setPrivilegeUI";
	}
	
	/**
	 * 设置权限
	 * @return
	 */
	public String setPrivilege(){
		Role role = roleService.getById(model.getId());
		
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		
		roleService.update(role);
		return "toList";
	}

	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
	

}
