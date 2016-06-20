package edu.fjnu.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.fjnu.oa.domain.PageBean;
import edu.fjnu.oa.util.QueryHelper;

@SuppressWarnings("unchecked")
@Transactional
public class DaoSupportImpl<T> implements DaoSupport<T> {
	
	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;
	
	public DaoSupportImpl() {
		//使用反射机制得到T的真实类型
		//获取当前new对象的 泛型的父类 的类型
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];// 获取第一个类型参数的真实类型
	}
	
	//获得当前可用的session
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void save(T entity) {
		getSession().save(entity);
		
	}

	@Override
	public void delete(Long id) {
		Object object = getById(id);
		if(object != null){
			getSession().delete(object);
		}
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public T getById(Long id) {
		if(id == null){
			return null;
		}else{
			return (T)getSession().get(clazz, id);
		}		
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		if(ids == null || ids.length == 0){
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery(
				"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")
				.setParameterList("ids", ids)
				.list();
	}

	@Override
	public List<T> findAll() {
		return getSession().createQuery(
				"FROM " + clazz.getSimpleName())
				.list();
	}

	@Override
	public PageBean getPageBean(int pageNum, int pageSize,QueryHelper queryHelper) {
		//查询本页的数据列表
		Query listQuery = getSession().createQuery(queryHelper.getListQueryHql());
		List parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size();i++){
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		List list = listQuery.setFirstResult((pageNum - 1) * pageSize)
					.setMaxResults(pageSize)
					.list();
		
		//查询总记录数量
		Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
		if(parameters != null){
			for(int i = 0; i < parameters.size();i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
	
		Long count = (Long) countQuery.uniqueResult();
		
		return new PageBean(pageNum, pageSize, count.intValue(), list);
	}

}
