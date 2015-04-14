package com.yida.core.base.dao;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Staff;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StaffDao extends BaseDao<Staff, String> {

	
	public boolean staffNameIsExists(String name, String... excludeStaffIds) {
		List<String> params = new ArrayList<String>(2);
		StringBuffer hql = new StringBuffer("select count(o.id) from " + getCanonicalName() + " o where o.name = ?");
		params.add(name);
		if (null != excludeStaffIds && 0 != excludeStaffIds.length) {
			for (String e : excludeStaffIds) {
				if (StringUtils.isMeaningFul(e)) {
					hql.append(" and id <> ?");
					params.add(e);
				}
			}
		}
		return 0 < this.findCountByHql(hql.toString(), params.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<Staff> findStaffByOrgId(final String orgId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Staff>>() {
			
			@Override
			public List<Staff> doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sb = new StringBuilder("select o.*, a.* from s_staff o inner join s_account a on o.id = a.id inner join mp_staff_org mp on o.id = mp.staffId");
				sb.append(" where mp.orgId = ?");
				sb.append(" and mp.orgId in (");
				sb.append("select top 1 mp1.orgId from mp_staff_org mp1 inner join s_org o1 on mp1.orgId = o1.id where mp1.staffId = mp.staffId order by o1.hierarchy desc");
				sb.append(")");
				SQLQuery query = session.createSQLQuery(sb.toString());
				query.addEntity(Staff.class);
				query.setParameter(0, orgId);
				return query.list();
			}
		});
	}

	
	public List<Staff> listStaffByStaffIds(String staffIds) {
		if (!StringUtils.isMeaningFul(staffIds)) return new ArrayList<>(0);
		
		StringBuilder sqlBuilder = new StringBuilder("select o.* from " + getTableName() + " o where o.id in (");
		String[] ids = staffIds.split(",");
		List<Object> paramList = new ArrayList<Object>(ids.length);
		for (String id : ids) {
			id = id.trim();
			if (StringUtils.isMeaningFul(id)) {
				sqlBuilder.append("?,");
				paramList.add(id);
			}
		}
		sqlBuilder.setLength(sqlBuilder.length()-1);
		sqlBuilder.append(")");
		return findListBySql(sqlBuilder.toString(), paramList.toArray());
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getAllStaffIdAndName() {
		List<Object[]> os = this.getHibernateTemplate().executeFind(new HibernateCallback<List<Object[]>>(){
			
			@Override
			public List<Object[]> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "select id, name from s_staff";
				SQLQuery query = session.createSQLQuery(sql);
				
				List<Object[]> os = query.list();
				
				return os;
			}
		});
		
		Map<String, String> maps = new ConcurrentHashMap<String, String>();
		for (Object[] o : os) {
			maps.put((String)o[0], (String)o[1]);
		}
		return maps;
	}

}
