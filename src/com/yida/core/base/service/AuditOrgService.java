package com.yida.core.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.ListAuditOrgForm;
import com.yida.core.base.vo.SaveAuditOrgForm;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;
import com.yida.core.exception.NameRepeatException;

@Service("auditOrgService")
@Scope("singleton")
@Lazy(true)
public class AuditOrgService extends BaseService {

	public List<AuditOrg> listAllAuditOrg() {
		List<AuditOrg> list = this.auditOrgDao.getAllAuditOrg();
		return list;
	}
	
	
	@Transactional
	public void saveAuditOrg(AuditOrg auditOrg) {
		boolean isExists = auditOrgDao.getAuditOrgNameIsExists(auditOrg.getName(), null);
		if (isExists) {
			throw new NameRepeatException(AuditOrg.class, auditOrg.getName());
		}
		this.auditOrgDao.persist(auditOrg);
	}
	
	
	@Transactional
	public void updateAuditOrg(AuditOrg auditOrg) {
		boolean isExists = auditOrgDao.getAuditOrgNameIsExists(auditOrg.getName(), auditOrg.getId());
		if (isExists) {
			throw new NameRepeatException(AuditOrg.class, auditOrg.getName());
		}
		auditOrgDao.merge(auditOrg);
	}
	
	@Transactional
	public void deleteAuditOrgByAuditOrgId(String auditOrgId) {
		AuditOrg e = auditOrgDao.get(auditOrgId);
		
		// 检查数据是否存在
		if (null == e) {
			throw new EntityNotFoundException(AuditOrg.class, auditOrgId);
		}
		
		// 检查是否存在关联部门
		if (null != e.getChildren() && 0 < e.getChildren().size()) {
			throw new IllegalStateException("机构下仍存在关联的部门数据，暂不能删除。");
		}
		
		// 检查是否存在关联人员
		if (null != e.getStaffs() && 0 < e.getStaffs().size()) {
			throw new IllegalStateException("部门下仍存在关联的人员数据，暂不能删除。");
		}
		
		// 移除数据
		this.auditOrgDao.remove(e);
	}

	
	public List<AuditOrg> listAuditOrg(ListAuditOrgForm query, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder("from " + auditOrgDao.getTableName() + " o where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (null != query) {
			
		}
		if (null != pageInfo) {
			int total = auditOrgDao.findCountBySql("select count(o.id) " + sb, params.toArray());
			pageInfo.setTotalResult(total);
		}
		List<AuditOrg> list = auditOrgDao.findListBySql("select o.* " + sb.toString(), params.toArray(), pageInfo);
		return list;
	}

	
	@Transactional
	public AuditOrg saveAuditOrg(SaveAuditOrgForm saveAuditOrgForm) {
		AuditOrg auditOrg = validImportField(saveAuditOrgForm);
		auditOrg.setHierarchy(1);
		this.auditOrgDao.persist(auditOrg);
		return auditOrg;
	}
	
	
	@Transactional
	public AuditOrg updateAuditOrg(SaveAuditOrgForm saveAuditOrgForm) {
		AuditOrg auditOrg = validImportField(saveAuditOrgForm);
		AuditOrg old = this.auditOrgDao.get(auditOrg.getId());
		old.setName(auditOrg.getName());
		old.setCode(auditOrg.getCode());
		old.setSortCode(auditOrg.getSortCode());
		old.setLeader(auditOrg.getLeader());
		return old;
	}
	
	@Transactional
	private AuditOrg validImportField(SaveAuditOrgForm saveAuditOrgForm) {
		AuditOrg c = new AuditOrg();
		
		if (StringUtils.isMeaningFul(saveAuditOrgForm.getId())) {
			c.setId(saveAuditOrgForm.getId());
		}
		
		// 检查机构编码为空和重复的情况
		String code = StringUtils.notNull(saveAuditOrgForm.getCode()).trim();
		if (0 == code.length()) {
			throw new IllegalStateException("机构编码不能为空。");
		}
		boolean codeIsExists = orgDao.orgPropertiyIsExists("code", code, c.getId());
		if (codeIsExists) {
			throw new IllegalStateException("机构编码已经被使用。");
		}
		c.setCode(code);
		
		// 检查机构排序为空的情况
		String sortCode = StringUtils.notNull(saveAuditOrgForm.getSortCode()).trim();
		if (0 == sortCode.length()) {
			throw new IllegalStateException("机构排序不能为空。");
		}
		c.setSortCode(sortCode);
		
		// 检查机构名称为空和重复的情况
		String auditOrgName = StringUtils.notNull(saveAuditOrgForm.getName()).trim();
		if (0 == auditOrgName.length()) {
			throw new IllegalStateException("机构名称不能为空。");
		}
		boolean nameIsExists = orgDao.orgPropertiyIsExists("name", auditOrgName, c.getId());
		if (nameIsExists) {
			throw new IllegalStateException("机构名称已经被使用。");
		}
		c.setName(auditOrgName);
		
		String leaderId = StringUtils.notNull(saveAuditOrgForm.getLeaderId()).trim();
		if (0 == leaderId.length()) {
			c.setLeader(null);
		} else {
			c.setLeader(new Staff(leaderId));
		}
		
		return c;
	}

	
	public List<AuditOrg> findAssociateAuditOrgByAccountId(String accountId) {
		return this.auditOrgDao.findAssociateAuditOrgByAccountId(accountId);
	}

	
	public AuditOrg getHeadAuditOrg() {
		return this.auditOrgDao.getHeadAuditOrg();
	}
	
}
