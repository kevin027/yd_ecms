package com.yida.core.base.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.yida.core.base.entity.Function;
import com.yida.core.base.vo.ListFunctionForm;
import com.yida.core.common.PageInfo;

@Repository
public class FunctionDao extends BaseDao<Function, String> {

	
	public List<Function> getTopLevelFunction(boolean isFetchChildren) {
		String hql = "select distinct o from " + Function.class.getSimpleName() + " o";
		if (isFetchChildren) {
			hql += " left join fetch o.children left join fetch o.parent";
		}
		hql += " where o.parent is null";
		return this.findListByHql(hql, null, null);
	}

	@SuppressWarnings("unchecked")
	public List<Function> getFunctionByStaffId(final String accountId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Function>>() {
			
			@Override
			public List<Function> doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery("select * from s_function where id in (select functionId from V_STAFF_FUNCTION where accountId = ?) order by hierarchy, sortCode");
				q.addEntity(Function.class);
				q.setParameter(0, accountId);
				List<Function> list = q.list();
				return list;
			}
		});
	}

	
	public List<Function> listFunction(ListFunctionForm query, PageInfo page) {
		StringBuilder hql = new StringBuilder(" from " + getSimpleName() + " o where 1=1");
		hql.append(" order by o.hierarchy, o.sortCode");
		List<Function> list = this.findListByHql(hql.toString(), null, null);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Function> findAccountFunctionsForStaff(final String accountId, final String auditOrgId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Function>>() {
			
			@Override
			public List<Function> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String subSql = "select r.id from mp_account_role mar inner join s_role r on mar.roleId = r.id where mar.accountId = ? and r.audit_org_id = ? and r.invalid <> 1";
				StringBuilder sb = new StringBuilder("select distinct o.* from s_function o inner join mp_role_function mrf on o.id = mrf.functionId where mrf.roleId in (").append(subSql).append(")");
				sb.append(" order by o.hierarchy, o.sortCode");
				SQLQuery q = session.createSQLQuery(sb.toString());
				q.addEntity(Function.class);
				q.setParameter(0, accountId);
				q.setParameter(1, auditOrgId);
				List<Function> list = q.list();
				return list;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Function> findAccountFunctionsForAdmin(String accountId, String auditOrgId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Function>>() {
			
			@Override
			public List<Function> doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuilder sb = new StringBuilder("select o.* from s_function o where o.invalid = 0");
				sb.append(" order by o.hierarchy, o.sortCode");
				SQLQuery q = session.createSQLQuery(sb.toString());
				q.addEntity(Function.class);
				List<Function> list = q.list();
				return list;
			}
		});
	}

}
