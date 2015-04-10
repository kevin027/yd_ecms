package com.yida.core.base.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Role;


@Repository
public class RoleDao extends BaseDao<Role, String> {

	
	public List<Role> listRoleByAccountId(String accountId) {
		String hql = "form " + super.getCanonicalName() + " o where o.accountsSet.id = ?";
		return this.findListByHql(hql, null, null);
	}

	
	public boolean roleNameIsExists(String roleName, String... excludeRoleIds) {
		List<String> params = new ArrayList<String>(2);
		StringBuffer hql = new StringBuffer("select count(o.id) from " + getCanonicalName() + " o where o.name = ?");
		params.add(roleName);
		if (null != excludeRoleIds && 0 != excludeRoleIds.length) {
			for (String e : excludeRoleIds) {
				if (StringUtils.isMeaningFul(e)) {
					hql.append(" and id <> ?");
					params.add(e);
				}
			}
		}
		return 0 < this.findCountByHql(hql.toString(), params.toArray());
	}
	
}
