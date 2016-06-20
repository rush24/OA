package edu.fjnu.oa.base;

import java.util.List;

import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.util.QueryHelper;

public interface DaoSupport<T> {
	
	public void save(T entity);
	
	public void delete(Long id);
	
	public void update(T entity);
	
	public T getById(Long id);
	
	public List<T> getByIds(Long[] ids);
	
	public List<T> findAll();
	
	/**
	 * 生成分页信息
	 * @param pageNum
	 * @param pageSize
	 * @param queryHelper
	 * @return
	 */
	public PageBean getPageBean(int pageNum,int pageSize,QueryHelper queryHelper);
	
}
