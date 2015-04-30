package com.yida.basedata.checkitems.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.LogAudit;
import com.tools.utils.StringUtils;
import com.yida.basedata.checkitems.entity.CheckItem;
import com.yida.basedata.checkitems.vo.ListCheckItemForm;
import com.yida.core.base.service.BaseService;
import com.tools.sys.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service("checkItemService")
@Scope("singleton")
@Lazy(false)
public class CheckItemService extends BaseService {
	
	public List<CheckItem> listCheckItem(ListCheckItemForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(checkItemDao.getTableName() + " o where 1=1");
		
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
			int total = checkItemDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<CheckItem> list = checkItemDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}
	
	
	public List<CheckItem> listValidCheckItem(ListCheckItemForm query,
			PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(checkItemDao.getTableName() + " o where o.invalid=0");
		
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
			int total = checkItemDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<CheckItem> list = checkItemDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}

	
	@Transactional
	@LogAudit(opType="新增",module="审查事项",description="新增基础数据-审查事项:${arg0}")
	public CheckItem saveCheckItem(CheckItem checkItem) {
		checkItem.setId(null);
		
		if (this.checkItemDao.columnValueIsExists("name", checkItem.getName(), "id", checkItem.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		
		this.checkItemDao.persist(checkItem);
		this.setVa(true, checkItem.getName());
		return checkItem;
	}

	
	@Transactional
	@LogAudit(opType="修改",module="审查事项",description="修改基础数据-审查事项:${arg0}")
	public void updateCheckItem(CheckItem checkItem) {
		
		if (this.checkItemDao.columnValueIsExists("name", checkItem.getName(), "id", checkItem.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		
		this.checkItemDao.merge(checkItem);
		this.setVa(true, checkItem.getName());
	}

	
	@Transactional
	@LogAudit(opType="删除",module="审查事项",description="删除基础数据-审查事项:${arg0};ID:${arg1}")
	public void delCheckItemById(String id) {
		CheckItem checkItem = this.checkItemDao.get(id);
		if (null == checkItem) {
			throw new EntityNotFoundException(CheckItem.class, id);
		}
		
		try {
			this.checkItemDao.remove(checkItem);
			this.setVa(true, checkItem.getName(),id);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	
	public CheckItem getCheckItemById(String checkItemId) {
		return this.checkItemDao.get(checkItemId);
	}

	
}
