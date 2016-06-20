package edu.fjnu.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import edu.fjnu.oa.domain.User;
import edu.fjnu.oa.service.DepartmentService;
import edu.fjnu.oa.service.ForumService;
import edu.fjnu.oa.service.PrivilegeService;
import edu.fjnu.oa.service.ReplyService;
import edu.fjnu.oa.service.RoleService;
import edu.fjnu.oa.service.TopicService;
import edu.fjnu.oa.service.UserService;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	// =============== ModelDriven的支持 ==================
	protected T model;
	
	protected int pageNum = 1;		//当前页
	protected int pageSize = 10;	//每页显示多少条记录
	
	public BaseAction(){
		// 通过反射获取model的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
		// 通过反射创建model的实例
		try {
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public T getModel() {
		return model;
	}
	
	// =============== Service实例的声明 ==================
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	
	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
