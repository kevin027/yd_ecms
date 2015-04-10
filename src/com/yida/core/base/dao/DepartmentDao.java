package com.yida.core.base.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yida.core.base.entity.Department;

@Repository
public class DepartmentDao extends BaseDao<Department, String> {

	
	public List<Department> getDepartmentByAuditOrgId(String auditOrgId) {
		String hql = " from " + super.getSimpleName() + " o where o.auditOrg.id = ?";
		@SuppressWarnings("unchecked")
		List<Department> list = getHibernateTemplate().find(hql, new Object[]{auditOrgId});
		return list;
	}

	
	public List<Department> getSubDepartmentByDepartmentId(String departmentId) {
		String hql = " from " + super.getSimpleName() + " o where o.parent.id = ?";
		@SuppressWarnings("unchecked")
		List<Department> list = getHibernateTemplate().find(hql, new Object[]{departmentId});
		return list;
	}

	
	public List<Department> getAllSubDepartmentByDepartmentId(String departmentId) {
		String hql = " from " + super.getSimpleName() + " o where o.parent.id = ?";
		@SuppressWarnings("unchecked")
		List<Department> list = getHibernateTemplate().find(hql, new Object[]{departmentId});
		List<Department> rlist = new ArrayList<Department>(list);
		while (0 < list.size()) {
			Department t = list.get(0);
			rlist.add(t);
			@SuppressWarnings("unchecked")
			List<Department> children = this.getHibernateTemplate().find(hql, new Object[]{t.getId()});
			for (Department cd : children) {
				list.add(0, cd);
			}
		}
		return rlist;
	}

	
	public int getDepartmentCountByAuditOrgId(String auditOrgId) {
		String hql = "select count(id) from " + super.getSimpleName() + " o where o.auditOrg.id = ?";
		@SuppressWarnings("unchecked")
		List<Integer> list = getHibernateTemplate().find(hql, new Object[]{auditOrgId});
		return list.get(0);
	}

	
	public boolean getDepartmentNameIsExists(String departmentName, String parentDepartmentId,
			String excludeDepartmentId) {
		List<Object> params = new ArrayList<Object>(3);
		StringBuilder hql = new StringBuilder("select count(id) from " + getSimpleName() + " where name = ?");
		params.add(departmentName);
		if (null != parentDepartmentId) {
			hql.append(" and o.parentDepartmentId = ?");
			params.add(parentDepartmentId);
		}
		if (null != excludeDepartmentId) {
			hql.append(" and id <> ?");
			params.add(excludeDepartmentId);
		}
		Integer count = (Integer) getHibernateTemplate().find(hql.toString(), params.toArray()).get(0);
		return 0 < count;
	}
	
}
