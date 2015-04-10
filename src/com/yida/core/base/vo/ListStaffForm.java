package com.yida.core.base.vo;

import java.util.Date;

public class ListStaffForm {
	private String staffName;
	private Date createDateFrom;
	private Date createDateTo;
	private String orgId;
	
	private String auditOrgId;
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public Date getCreateDateFrom() {
		return createDateFrom;
	}
	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}
	public Date getCreateDateTo() {
		return createDateTo;
	}
	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAuditOrgId() {
		return auditOrgId;
	}
	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}
}
