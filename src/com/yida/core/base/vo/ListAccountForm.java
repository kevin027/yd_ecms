package com.yida.core.base.vo;

import java.util.Date;

public class ListAccountForm {
	private String accounts;
	private Date createDateFrom;
	private Date createDateTo;
	private String auditOrgId;
	private String staffName;
	
	public String getAccounts() {
		return accounts;
	}
	public void setAccounts(String accounts) {
		this.accounts = accounts;
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
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getAuditOrgId() {
		return auditOrgId;
	}
	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}
}
