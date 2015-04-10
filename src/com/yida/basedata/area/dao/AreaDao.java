package com.yida.basedata.area.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.yida.basedata.area.entity.Area;
import com.yida.core.base.dao.BaseDao;

@Repository
@Scope("singleton")
@Lazy(false)
public class AreaDao extends BaseDao<Area, String> {
/*	@Override
	public String findMaxPid(String parentId) {
		Session session = null;
		try{
			session = this.getHibernateSession();
			Object maxid = session.createCriteria(Area.class)
					.add(Restrictions.eq("parent.id", parentId))
					.setProjection(Projections.projectionList().add(Projections.max("id") ) )
					.uniqueResult() ;
			return maxid!= null? maxid.toString():"";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}*/
	
	/**
	 * @param area
	 */
	public void delAreaTree(Area area){
		List<Object> params = new ArrayList<>();
		String delSql = " delete From Area where id = ? ";
		params.add(area.getId());
		String hql = "select Count(id) From Area where parent_id = ? ";
		int childSize = this.findCountByHql(hql, new Object[]{area.getId()});
		//如果选中父节点,则连子节点一起删除
		if(childSize > 0){
			delSql += " or parent_id = ?";
			params.add( area.getId());
		}
		this.getHibernateTemplate().bulkUpdate(delSql,params.toArray());
	}

	/**
	 * @param type
	 * 	2:省的id长度,4:市的id长度;6:区域的id长度
	 * @return
	 */
	public List<Area> getAreaList(String pid) {
		List<Object> params = new ArrayList<>();
		String sql = " select a.* from " + this.getTableName() + " a where 1=1  ";
		if(StringUtils.hasText(pid)){
			sql += " and a.parent_id = ?";
			params.add(pid);
		}else{
			sql += " and a.parent_id is null";
		}
		List<Area> list = this.findListBySql(sql, params.toArray());
		return list;
	}
	
}
