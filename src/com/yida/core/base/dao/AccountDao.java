package com.yida.core.base.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;

@Repository
public class AccountDao extends BaseDao<Account, String> {

	
	public Account getAccountByVerifyPassword(String accounts, String password) {
		String hql = "from " + getCanonicalName() + " o where o.accounts = ? and o.password = ?"; 
		List<Object> params = new ArrayList<Object>(2);
		params.add(accounts);
		params.add(password);
		List<Account> list = this.findListByHql(hql, params.toArray(), null);
		return (1 != list.size()) ? null : list.get(0);
	}

	
	public Account getAccountByAccountName(String accounts) {
		String hql = "from " + getCanonicalName() + " o where o.accounts = ?"; 
		List<Object> params = new ArrayList<Object>(2);
		params.add(accounts);
		List<Account> list = this.findListByHql(hql, params.toArray(), null);
		return (1 != list.size()) ? null : list.get(0);
	}

	
	public boolean accountIsExists(String newAccount, String... excludes) {
		List<String> params = new ArrayList<String>(2);
		StringBuffer hql = new StringBuffer("select count(o.id) from " + getCanonicalName() + " o where o.accounts = ?");
		params.add(newAccount);
		if (null != excludes && 0 != excludes.length) {
			for (String e : excludes) {
				if (StringUtils.isMeaningFul(e)) {
					hql.append(" and id <> ?");
					params.add(e);
				}
			}
		}
		return 0 < this.findCountByHql(hql.toString(), params.toArray());
	}
	
}
