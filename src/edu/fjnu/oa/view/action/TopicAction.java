package edu.fjnu.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.fjnu.oa.base.BaseAction;
import edu.fjnu.oa.domain.Forum;
import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.domain.Reply;
import edu.fjnu.oa.domain.Topic;
import edu.fjnu.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic>{

	private Long forumId;
	
	public String show(){
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		
		//分页数据
		new QueryHelper(Reply.class, "r")
			.addCondition("r.topic=?", topic)
			.addOrderProperty("r.postTime", true)
			.preparePageBean(replyService, pageNum, pageSize);
		
		return "show";
	}
	
	public String addUI(){
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		
		return "addUI";
	}
	
	public String add(){
		// 封装
		// >> 表单参数，已经封装了title, content
		// model.setTitle(title);
		// model.setContent(content);
		model.setForum(forumService.getById(forumId));
		// >> 当前直接获取的信息
		model.setAuthor(getCurrentUser()); // 当前登录用户
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr()); // 当前请求中的IP

		// 保存
		topicService.save(model);
		return "toShow";
	}

	
	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}


	
	
	
	
}
