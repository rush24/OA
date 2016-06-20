package edu.fjnu.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.fjnu.oa.domain.Department;

public class DepartmentUtils {

	/**
	 * 根据topList返回一个树状的List
	 * @param topList
	 * @return
	 */
	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTreeList(topList,list, "┣");
		return list;
	}

	/**
	 * 递归遍历
	 * @param topList
	 * @param list
	 * @param prefix
	 */
	private static void walkDepartmentTreeList(Collection<Department> topList, List<Department> list, String prefix) {
		for(Department top : topList){
			Department copy = new Department();
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy);
			
			// 子树
			walkDepartmentTreeList(top.getChildren(),list,"　" + prefix); // 使用全角的空格
		}
		
	}

}
