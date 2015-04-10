package com.yida.core.base.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Org;

@Repository
public class OrgDao extends BaseDao<Org, String> {

	
	public List<Org> getAuditOrgs(String auditOrgId,Boolean b) {
		List<Object> params = new ArrayList<Object>(1);
		StringBuilder sb = new StringBuilder("from " + this.getCanonicalName() + " o where o.parent is null");
		if (StringUtils.isMeaningFul(auditOrgId)) {
			sb.append(" and o.id = ?");
			params.add(auditOrgId);
		}
		if(null == b || !b){
			sb.append(" and isNull(o.isOrg,0) = 0");
		}
		sb.append(" order by o.sortCode");
		return this.findListByHql(sb.toString(), params.toArray(), null);
	}

	
	public boolean isNameExistsInSameHierarchy(String name, Integer hierarchy, String excludeOrgId) {
		List<Object> params = new ArrayList<Object>(2);
		StringBuilder hql = new StringBuilder("select count(id) from " + getSimpleName() + " where name = ?");
		params.add(name);
		
		if (null != hierarchy && 0 < hierarchy) {
			hql.append(" and hierarchy = ?");
			params.add(hierarchy);
		} else {
			throw new IllegalArgumentException("参数hierarchy无效");
		}
		
		if (null != excludeOrgId) {
			hql.append(" and id <> ?");
			params.add(excludeOrgId);
		}
		
		Long count = (Long) getHibernateTemplate().find(hql.toString(), params.toArray()).get(0);
		return 0 < count;
	}

	
	public boolean orgPropertiyIsExists(String propName, Object propValue, String excludeOrgId) {
		List<Object> params = new ArrayList<Object>(2);
		StringBuilder hql = new StringBuilder("select count(id) from " + getSimpleName() + " where ").append(propName).append(" = ?");
		params.add(propValue);
		if (null != excludeOrgId) {
			hql.append(" and id <> ?");
			params.add(excludeOrgId);
		}
		Long count = (Long) getHibernateTemplate().find(hql.toString(), params.toArray()).get(0);
		return 0 < count;
	}

	
	public Org getRootOrgByOrgId(String orgId) {
		Org org = this.get(orgId);
		while (null != org.getParent()) {
			org = org.getParent();
		}
		return org;
	}

	
	public List<Org> listOrgByLeaderId(String leaderId) {
		String sql = "select o from Org o where o.leader.id = ? order by hierarchy";
		return this.findListByHql(sql, new Object[]{leaderId});
	}
	
}
