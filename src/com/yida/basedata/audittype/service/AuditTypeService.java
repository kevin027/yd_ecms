package com.yida.basedata.audittype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.LogAudit;
import com.tools.utils.StringUtils;
import com.yida.basedata.audittype.entity.AuditType;
import com.yida.basedata.audittype.vo.ListAuditTypeForm;
import com.yida.core.base.service.BaseService;
import com.tools.sys.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service("auditTypeService")
@Scope("singleton")
@Lazy(false)
public class AuditTypeService extends BaseService {
	
	public List<AuditType> listAuditType(ListAuditTypeForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(auditTypeDao.getTableName() + " o where 1=1");
		
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
			int total = auditTypeDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<AuditType> list = auditTypeDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}
	
	
	public List<AuditType> listValidAuditType(ListAuditTypeForm query,
			PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(auditTypeDao.getTableName() + " o where o.invalid=0");
		
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
			int total = auditTypeDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<AuditType> list = auditTypeDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}

	
	@Transactional
	@LogAudit(opType="新增",module="评审类型",description="新增基础数据-评审类型:${arg0}")
	public AuditType saveAuditType(AuditType auditType) {
		auditType.setId(null);
		
		if (this.auditTypeDao.columnValueIsExists("name", auditType.getName(), "id", auditType.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		this.auditTypeDao.persist(auditType);
		this.setVa(true, auditType.getName());
		return auditType;
	}

	
	@Transactional
	@LogAudit(opType="修改",module="评审类型",description="修改基础数据-评审类型:${arg0}")
	public void updateAuditType(AuditType auditType) {
		
		if (this.auditTypeDao.columnValueIsExists("name", auditType.getName(), "id", auditType.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		
		this.auditTypeDao.merge(auditType);
		this.setVa(true, auditType.getName());
	}

	
	@Transactional
	@LogAudit(opType="删除",module="评审类型",description="删除基础数据-评审类型:${arg0};ID:${arg1}")
	public void delAuditTypeById(String id) {
		AuditType auditType = this.auditTypeDao.get(id);
		if (null == auditType) {
			throw new EntityNotFoundException(AuditType.class, id);
		}
		
		try {
			this.auditTypeDao.remove(auditType);
			this.setVa(true, auditType.getName(),id);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	
	public AuditType getAuditTypeById(String auditTypeId) {
		return this.auditTypeDao.get(auditTypeId);
	}

	
}
