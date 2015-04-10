package com.yida.core.base.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.AuditOrg;

@Repository
public class AuditOrgDao extends BaseDao<AuditOrg, String> {

	
	public List<AuditOrg> getAllAuditOrg() {
		return null;
	}

	
	public boolean getAuditOrgNameIsExists(String auditOrgName, String excludeAuditOrgId) {
		List<Object> params = new ArrayList<Object>(2);
		StringBuilder hql = new StringBuilder("select count(id) from " + getSimpleName() + " where name = ?");
		params.add(auditOrgName);
		if (null != excludeAuditOrgId) {
			hql.append(" and id <> ?");
			params.add(excludeAuditOrgId);
		}
		Long count = (Long) getHibernateTemplate().find(hql.toString(), params.toArray()).get(0);
		return 0 < count;
	}

	
	public boolean getAuditOrgCodeIsExists(String auditOrgCode, String excludeAuditOrgId) {
		List<Object> params = new ArrayList<Object>(2);
		StringBuilder hql = new StringBuilder("select count(id) from " + getSimpleName() + " where code = ?");
		params.add(auditOrgCode);
		if (null != excludeAuditOrgId) {
			hql.append(" and id <> ?");
			params.add(excludeAuditOrgId);
		}
		Long count = (Long) getHibernateTemplate().find(hql.toString(), params.toArray()).get(0);
		return 0 < count;
	}

	@SuppressWarnings("unchecked")
	
	public List<AuditOrg> findAssociateAuditOrgByAccountId(final String accountId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<AuditOrg>>() {
			
			@Override
			public List<AuditOrg> doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuilder sb = new StringBuilder();
				sb.append("select distinct c.*, o.* from s_audit_org c inner join s_org o on c.id = o.id");
				sb.append(" where exists (select a.id from s_account a where a.type = ? and a.id = ?)");
				sb.append(" or c.id in (select dbo.getAuditOrgIdByOrgId(org_id) from s_account a inner join s_staff s on a.staff_id = s.id inner join mp_staff_org mpso on mpso.staff_id = s.id where a.id = ?)");
				sb.append(" or c.id in (select r.audit_org_id from s_account a inner join mp_account_role mar on a.id = mar.account_id inner join s_role r on mar.role_id = r.id where a.id = ?)");
				SQLQuery query = session.createSQLQuery(sb.toString());
				query.addEntity(AuditOrg.class);
				query.setParameter(0, Account.Type.ADMIN.ordinal());
				query.setParameter(1, accountId);
				query.setParameter(2, accountId);
				query.setParameter(3, accountId);
				return query.list();
			}
		});
	}

	
	public AuditOrg getHeadAuditOrg() {
		String sql = "select c.*, o.* from s_audit_org c inner join s_org o on c.id = o.id where c.is_head = 1";
		List<AuditOrg> list = this.findListBySql(sql);
		return (0 == list.size() ? null : list.get(0));
	}
	
}
