package com.yida.core.base.vo;

import com.yida.core.base.entity.AuditOrg;

public class SaveAuditOrgForm {
	
	private String id;
	private String name;
	private String parentId;
	private String code;
	private String sortCode;
	
	private String leaderId;
	private String leaderName;
	
	public SaveAuditOrgForm() {
		
	}
	
	public SaveAuditOrgForm(AuditOrg auditOrg) {
		this.id = auditOrg.getId();
		this.name = auditOrg.getName();
		this.code = auditOrg.getCode();
		this.sortCode = auditOrg.getSortCode();
		if (null != auditOrg.getLeader()) {
			this.leaderId = auditOrg.getLeader().getId();
			this.leaderName = auditOrg.getLeader().getName();
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
}
