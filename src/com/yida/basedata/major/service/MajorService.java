package com.yida.basedata.major.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.LogAudit;
import com.tools.utils.StringUtils;
import com.yida.basedata.major.entity.Major;
import com.yida.basedata.major.vo.ListMajorForm;
import com.yida.core.base.service.BaseService;
import com.tools.sys.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service("majorService")
@Scope("singleton")
@Lazy(false)
public class MajorService extends BaseService {
	
	public List<Major> listMajor(ListMajorForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(majorDao.getTableName() + " o where 1=1 and o.parent_id is null");
		
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
			int total = majorDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<Major> list = majorDao.findListBySql("select o.* " + fromSql + " order by o.id", params, pageInfo);
		for (Major major : list) {
			major.setState("open");
			initMajor(major);
		}
		return list;
	}
	
	private void initMajor(Major major){
		StringBuilder fromBuilder = new StringBuilder("select o.* from ").append(majorDao.getTableName() + " o where 1=1 and o.parent_id=?");
		List<Major> children=majorDao.findListBySql(fromBuilder.toString(),new Object[]{major.getId()});
		if(children.isEmpty()) return;
		major.setChildren(children);
		if(!children.isEmpty()){
			for(Major m:children){
				m.setState("open");
				this.initMajor(m);
			}
		}
	}
	
	
	public List<Major> listValidMajor(ListMajorForm query,
			PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(majorDao.getTableName() + " o where o.invalid=0");
		
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
			int total = majorDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<Major> list = majorDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}

	
	@Transactional
	@LogAudit(opType="新增",module="专业类型",description="新增基础数据-专业类型:${arg0}")
	public Major saveMajor(Major major) {
		major.setId(null);
		
		if (this.majorDao.columnValueIsExists("name", major.getName(), "id", major.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		if(major.getParent()!=null && StringUtils.isMeaningFul(major.getParent().getId())){
			Major parent=this.majorDao.get(major.getParent().getId());
			major.setParent(parent);
		}
		this.majorDao.persist(major);
		this.setVa(true, major.getName());
		return major;
	}

	
	@Transactional
	@LogAudit(opType="更新",module="专业类型",description="更新基础数据-专业类型:${arg0}")
	public void updateMajor(Major major) {
		
		if (this.majorDao.columnValueIsExists("name", major.getName(), "id", major.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		if(major.getParent()!=null && StringUtils.isMeaningFul(major.getParent().getId())){
			Major parent=this.majorDao.get(major.getParent().getId());
			major.setParent(parent);
		}
		this.majorDao.merge(major);
		this.setVa(true, major.getName());
	}

	
	@Transactional
	@LogAudit(opType="删除",module="专业类型",description="删除基础数据-专业类型:${arg0};ID:${arg1}")
	public void delMajorById(String id) {
		Major major = this.majorDao.get(id);
		if (null == major) {
			throw new EntityNotFoundException(Major.class, id);
		}
		
		try {
			this.majorDao.remove(major);
			this.setVa(true, major.getName(),id);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	/**
	 * 根据ID删除相关的节点
	 * @param majorId
	 */
	
	@Transactional
	@LogAudit(opType="删除",module="专业类型",description="删除基础数据-(根据ID删除相关的节点)专业类型节点:${arg0};ID:${arg1}")
	public void delMajorTree(String majorId){
		Major major = this.majorDao.get(majorId);
		if (null == major) {
			throw new EntityNotFoundException(Major.class, majorId);
		}
		majorDao.delMajorTree(major);
		this.setVa(true, major.getName(),majorId);
		
	}
	
	
	public Major getMajorById(String majorId) {
		return this.majorDao.get(majorId);
	}

	
}
