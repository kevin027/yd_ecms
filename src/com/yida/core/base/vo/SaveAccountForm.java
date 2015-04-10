package com.yida.core.base.vo;

import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Role;

public class SaveAccountForm {
	private String id;
	private String accounts;
	private String password;
	private Boolean invalid;
	private String roleIds;
	private String staffId;
	
	private String staffName;
	private String auditOrgId;
	
	public SaveAccountForm() {
		
	}
	
	public SaveAccountForm(Account account) {
		this.setId(account.getId());
		this.setAccounts(account.getAccounts());
		this.setPassword(account.getPassword());
		this.setInvalid(account.getInvalid());
		
		if (null != account.getRoles() && 0 < account.getRoles().size()) {
			StringBuilder sb = new StringBuilder();
			for (Role r : account.getRoles()) {
				sb.append(r.getId()).append(",");
			}
			this.setRoleIds(sb.substring(0, sb.length()-1));
		}
		
		if (null != account.getStaff()) {
			this.setStaffId(account.getStaff().getId());
			this.setStaffName(account.getStaff().getName());
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccounts() {
		return accounts;
	}
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	public Boolean getInvalid() {
		return invalid;
	}
	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
