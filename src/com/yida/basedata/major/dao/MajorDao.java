package com.yida.basedata.major.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.basedata.major.entity.Major;
import com.yida.core.base.dao.BaseDao;

@Repository
@Scope("singleton")
@Lazy(false)
public class MajorDao extends BaseDao<Major, String> {
	public void delMajorTree(Major major){
		List<Object> params = new ArrayList<>();
		String delSql = " delete From Major where id = ? ";
		params.add(major.getId());
		String hql = "select count(id) From Major where parent_id = ? ";
		int childSize = this.findCountByHql(hql, new Object[]{major.getId()});
		//如果选中父节点,则连子节点一起删除
		if(childSize > 0){
			delSql += " or parent_id = ?";
			params.add( major.getId());
		}
		this.getHibernateTemplate().bulkUpdate(delSql,params.toArray());
	}

	
	public List<Major> getMajorList(String[] ids) {
		List<Object> params = new ArrayList<>();
		String sql = "Select o.* from " + this.getTableName() + " o where 1=1 and o.id in (";
		for (int i = 0; i < ids.length; i++) {
			sql += i==0? "?" : ",?" ;
			params.add(ids[i]);
		}
		sql+=")";
		return this.findListBySql(sql,params.toArray());
	}
	
	
}
