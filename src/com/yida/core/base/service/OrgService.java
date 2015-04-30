package com.yida.core.base.service;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Org;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("orgService")
@Scope("singleton")
@Lazy(true)
public class OrgService extends BaseService {
	
	/**
	 * 获取机构级别的组织机构
	 * @param auditOrgId 
	 * @return
	 */
	public List<Org> getAuditOrgs(String auditOrgId,Boolean b) {
		return orgDao.getAuditOrgs(auditOrgId,b);
	}

	/**
	 * 根据父级组织机构节点ID，查找其子级组织机构。
	 * @param parentId 机构节点ID，为空时则说明从根节点开始查找
	 * @param withCascadeChildren 获取的子级组织机构是否包含级联的子组织机构
	 * @return 树结构的Org列表对象，列表中关联的所有对象非持久化对象。
	 */
	public List<Org> findOrgByParentId(String parentId, boolean withCascadeChildren) {
		List<Org> list = null;
		if (!StringUtils.isMeaningFul(parentId)) {
			list = this.orgDao.getAuditOrgs(null,null);
		} else {
			Org parent = this.orgDao.get(parentId);
			if (null != parent) {
				list = parent.getChildren();
			}
		}
		if (null == list) return new ArrayList<Org>();
		if (!withCascadeChildren) {
			for (Org o : list) {
				o.setChildren(null);
			}
		}
		return list;
	}

	/**
	 * 根据组织机构Id获取对应的组织机构
	 * @param orgId
	 * @param withCascadeChildren // 获取的机构是否也包含级联的子组织机构
	 * @return
	 */
	public Org getOrgById(String orgId, boolean withCascadeChildren) {
		Org org = this.orgDao.get(orgId);
		if (!withCascadeChildren) {
			org.setChildren(null);
		}
		return org;
	}

	/**
	 * 根据部门主任ID找出所管辖的部门
	 * @param leaderId
	 * @return
	 */
	public List<Org> listOrgByLeaderId(String leaderId) {
		return this.orgDao.listOrgByLeaderId(leaderId);
	}

}
