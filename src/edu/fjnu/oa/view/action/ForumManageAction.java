package edu.fjnu.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.fjnu.oa.base.BaseAction;
import edu.fjnu.oa.domain.Forum;

@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum>{

	/**
	 * 查询所有
	 * @return
	 */
	public String list(){
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}

	/**
	 * 删除板块
	 * @return
	 */
	public String delete(){
		forumService.delete(model.getId());
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
	 * 添加板块
	 * @return
	 */
	public String add(){
		forumService.save(model);
		return "toList";
	}
	
	/**
	 * 编辑板块页面
	 * @return
	 */
	public String editUI(){
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	
	/**
	 * 编辑板块
	 * @return
	 */
	public String edit(){
		Forum forum = forumService.getById(model.getId());
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		
		forumService.update(forum);
		return "toList";
	}
	
	/**
	 * 上移
	 * @return
	 */
	public String moveUp(){
		forumService.moveUp(model.getId());
		return "toList";
	}
	
	/**
	 * 下移
	 * @return
	 */
	public String moveDown(){
		forumService.moveDown(model.getId());
		return "toList";
	}
}
