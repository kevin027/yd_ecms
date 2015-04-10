package com.yida.basedata.area.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.LogAudit;
import com.tools.utils.StringUtils;
import com.yida.basedata.area.entity.Area;
import com.yida.basedata.area.vo.ListAreaForm;
import com.yida.core.base.service.BaseService;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service("areaService")
@Scope("singleton")
@Lazy(false)
public class AreaService extends BaseService {
	
	public List<Area> listArea(ListAreaForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(areaDao.getTableName() + " o where 1=1 and o.parent_id is null ");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String name = StringUtils.notNull(query.getName()).trim();
			if (0 < name.length()) {
				fromBuilder.append(" and o.name like ?");
				paramList.add("%" + name + "%");
			}
			
			Boolean invalid = query.getInvalid();
			if (null != invalid) {
				fromBuilder.append(" and o.invalid = ?");
				paramList.add(query.getInvalid());
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = areaDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		List<Area> list = areaDao.findListBySql("select o.* " + fromSql + " order by o.id", params, pageInfo);
		for (Area area : list) {
			area.setState("open");
			this.initArea(area);
		}
		return list;
	}
	
	/**
	 * 获取所有子节点
	 * @param area 地区
	 */
	public void initArea(Area area){
		StringBuilder fromBuilder = new StringBuilder("select o.* from ").append(areaDao.getTableName() + " o where 1=1 and o.parent_id=?");
		List<Area> children=areaDao.findListBySql(fromBuilder.toString(),new Object[]{area.getId()});
		if(children.isEmpty()) return;
		area.setChildren(children);
		if(!children.isEmpty()){
			for(Area a:children){
				a.setState("open");
				this.initArea(a);
			}
		}
	}
	
	
	public List<Area> listValidArea(ListAreaForm query,
			PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(areaDao.getTableName() + " o where o.invalid=0");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String name = StringUtils.notNull(query.getName()).trim();
			if (0 < name.length()) {
				fromBuilder.append(" and o.name like ?");
				paramList.add("%" + name + "%");
			}
			
			Boolean invalid = query.getInvalid();
			if (null != invalid) {
				fromBuilder.append(" and o.invalid = ?");
				paramList.add(query.getInvalid());
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = areaDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<Area> list = areaDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}

	
	@Transactional
	@LogAudit(opType="新增",module="地区管理",description="新增基础数据-地区:${arg0}")
	public Area saveArea(Area area) {
		area.setId(null);
		if (this.areaDao.columnValueIsExists("name", area.getName(), "id", area.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		if(area.getParent()!=null && StringUtils.isMeaningFul(area.getParent().getId())){
			Area parent=areaDao.get(area.getParent().getId());
			area.setParent(parent);
		}
		this.areaDao.persist(area);
		this.setVa(true,area.getName());
		return area;
	}

	
	@Transactional
	@LogAudit(opType="修改",module="地区管理",description="修改基础数据-地区:${arg0}")
	public void updateArea(Area area) {
		if (this.areaDao.columnValueIsExists("name", area.getName(), "id", area.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		if(area.getParent()!=null && StringUtils.isMeaningFul(area.getParent().getId())){
			Area parent=areaDao.get(area.getParent().getId());
			area.setParent(parent);
		}
		this.areaDao.merge(area);
		this.setVa(true,area.getName());
	}

	
	@Transactional
	@LogAudit(opType="删除",module="地区管理",description="删除基础数据-地区:${arg0};${arg1}")
	public void delAreaById(String id) {
		Area area = this.areaDao.get(id);
		if (null == area) {
			throw new EntityNotFoundException(Area.class, id);
		}
		try {
			this.areaDao.delAreaTree(area);
			this.setVa(true,area.getName(),area.getName());
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	/* (non-Javadoc)
	 * @see com.yida.basedata.area.service.IAreaService#getAreaById(java.lang.String)
	 */
	
	public Area getAreaById(String areaId) {
		return areaDao.get(areaId);
	}

/*	@Transactional
	private String getMaxAreaId(String pid) {
		String parentId = "11";
		String saveId = "";
		//获取此父节点中子节点的最大ID
		String maxId = areaDao.findMaxPid(pid);
		//新增父节点
		if("".equals(pid)){
			//如果存在最大ID,则当前ID为maxId+1,如果不存在最大ID,则创建2位数ID(11)
			saveId = StringUtils.hasText(maxId)&&!maxId.equals("null") ? (Integer.valueOf(maxId)+1)+"" : parentId;
		}else{
			saveId = StringUtils.hasText(maxId)&&!maxId.equals("null") ? (Integer.valueOf(maxId)+1)+"" : pid+"01";
		}
		return saveId;
	}*/

}
