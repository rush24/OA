package edu.fjnu.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.fjnu.oa.base.BaseAction;
import edu.fjnu.oa.domain.Forum;
import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.domain.Topic;
import edu.fjnu.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{
	
	/**
	 * 0 表示查看全部主题<br>
	 * 1 表示只看精华帖
	 */
	private int viewType = 0;

	/**
	 * 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 表示只按最后更新时间排序<br>
	 * 2 表示只按主题发表时间排序<br>
	 * 3 表示只按回复数量排序
	 */
	private int orderBy = 0;

	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;


	public String list(){
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	public String show(){
		System.out.println("viewType: " + viewType + "---orderBy: " + orderBy + "---asc: " + asc);
		
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
		//分页数据
		new QueryHelper(Topic.class, "t")
			// 过滤条件
			.addCondition("t.forum=?", forum)//
			.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST) // 1 表示只看精华帖
			// 排序条件
			.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc) // 1 表示只按最后更新时间排序
			.addOrderProperty((orderBy == 2), "t.postTime", asc) // 2 表示只按主题发表时间排序
			.addOrderProperty((orderBy == 3), "t.replyCount", asc) // 3 表示只按回复数量排序
			.addOrderProperty((orderBy == 0), "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
			.addOrderProperty((orderBy == 0), "t.lastUpdateTime", false) // 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
			.preparePageBean(topicService, pageNum, pageSize);
		
		return "show";
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	
}
