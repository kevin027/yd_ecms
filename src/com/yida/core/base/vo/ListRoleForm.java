package com.yida.core.base.vo;


public class ListRoleForm {
	
	private String auditOrgId;
	private Boolean invalid;
	private String roleName;
	private String staffName;
	private String orgId;
	
	public String getAuditOrgId() {
		return auditOrgId;
	}
	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}
	public Boolean getInvalid() {
		return invalid;
	}
	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
