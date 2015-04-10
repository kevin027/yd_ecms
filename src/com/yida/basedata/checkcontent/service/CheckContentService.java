package com.yida.basedata.checkcontent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.LogAudit;
import com.tools.utils.StringUtils;
import com.yida.basedata.checkcontent.entity.CheckContent;
import com.yida.basedata.checkcontent.vo.ListCheckContentForm;
import com.yida.core.base.service.BaseService;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service("checkContentService")
@Scope("singleton")
@Lazy(false)
public class CheckContentService extends BaseService {
	
	public List<CheckContent> listCheckContent() {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(checkContentDao.getTableName() + " o where o.invalid=0");
		String fromSql = fromBuilder.toString();
		List<CheckContent> list = checkContentDao.findListBySql("select o.* " + fromSql + " order by o.sort", null, null);
		return list;
	}
	
	
	public List<CheckContent> listCheckContent(ListCheckContentForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(checkContentDao.getTableName() + " o where 1=1");
		
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
			int total = checkContentDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<CheckContent> list = checkContentDao.findListBySql("select o.* " + fromSql + " order by o.sort", params, pageInfo);
		return list;
	}
	
	
	public List<CheckContent> listValidCheckContent(ListCheckContentForm query,
			PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(checkContentDao.getTableName() + " o where o.invalid=0");
		
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
			int total = checkContentDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<CheckContent> list = checkContentDao.findListBySql("select o.* " + fromSql + " order by o.sort", params, pageInfo);
		return list;
	}

	
	@Transactional
	@LogAudit(opType="新增",module="审查内容",description="新增基础数据-审查内容:${arg0}")
	public CheckContent saveCheckContent(CheckContent checkContent) {
		checkContent.setId(null);
		
		if (this.checkContentDao.columnValueIsExists("name", checkContent.getName(), "id", checkContent.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		if (this.checkContentDao.columnValueIsExists("sort", checkContent.getSort(), "id", checkContent.getId())) {
			throw new IllegalStateException("已经存在相同序号。");
		}
		
		this.checkContentDao.persist(checkContent);
		this.setVa(true, checkContent.getName());
		return checkContent;
	}

	
	@Transactional
	@LogAudit(opType="修改",module="审查内容",description="修改基础数据-审查内容:${arg0}")
	public void updateCheckContent(CheckContent checkContent) {
		
		if (this.checkContentDao.columnValueIsExists("name", checkContent.getName(), "id", checkContent.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		if (this.checkContentDao.columnValueIsExists("sort", checkContent.getSort(), "id", checkContent.getId())) {
			throw new IllegalStateException("已经存在相同序号。");
		}
		
		this.checkContentDao.merge(checkContent);
		this.setVa(true, checkContent.getName());
	}

	
	@Transactional
	@LogAudit(opType="删除",module="审查内容",description="删除基础数据-审查内容:${arg0};ID:${arg1}")
	public void delCheckContentById(String id) {
		CheckContent checkContent = this.checkContentDao.get(id);
		if (null == checkContent) {
			throw new EntityNotFoundException(CheckContent.class, id);
		}
		
		try {
			this.checkContentDao.remove(checkContent);
			this.setVa(true, checkContent.getName(),id);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	
	public CheckContent getCheckContentById(String checkContentId) {
		return this.checkContentDao.get(checkContentId);
	}

	
	public CheckContent getCheckContentByName(String checkContentName) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(checkContentDao.getTableName() + " o where o.invalid=0");
		fromBuilder.append(" and o.name='"+checkContentName+"' ");
		String fromSql = fromBuilder.toString();
		List<CheckContent> list = checkContentDao.findListBySql("select o.* " + fromSql + " order by o.sort");
		CheckContent ck=null;
		if(list!=null && list.size()>0){
			ck = list.get(0);
		}
		return ck;
	}
}
