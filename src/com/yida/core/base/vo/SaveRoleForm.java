package com.yida.core.base.vo;

import java.io.Serializable;

import com.yida.core.base.entity.Role;

public class SaveRoleForm implements Serializable {
	
	private static final long serialVersionUID = 7792748822450353516L;
	
	private String roleId;
	private String roleName;
	private Boolean invalid;
	private String remark;
	private String auditOrgId;
	
	public SaveRoleForm() {
		
	}
	
	public SaveRoleForm(Role r) {
		roleId = r.getId();
		roleName = r.getName();
		remark = r.getRemark();
		invalid = r.getInvalid();
		if (null != r.getAuditOrg()) {
			this.auditOrgId = r.getAuditOrg().getId();
		}
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Boolean getInvalid() {
		return invalid;
	}
	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditOrgId() {
		return auditOrgId;
	}

	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}
}
