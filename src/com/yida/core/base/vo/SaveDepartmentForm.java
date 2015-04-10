package com.yida.core.base.vo;

import com.yida.core.base.entity.Department;

public class SaveDepartmentForm {
	
	private String id;
	private String name;
	private String parentId;
	private String code;
	private String sortCode;
	private String leaderId;
	private String leaderName;
	private Integer isOrg;
	
	public SaveDepartmentForm() {
		
	}
	
	public SaveDepartmentForm(Department dept) {
		this.id = dept.getId();
		this.name = dept.getName();
		this.code = dept.getCode();
		this.sortCode = dept.getSortCode();
		this.isOrg = dept.getIsOrg();
		if (null != dept.getLeader()) {
			this.leaderId = dept.getLeader().getId();
			this.leaderName = dept.getLeader().getName();
		}
		if (null != dept.getParent()) {
			this.parentId = dept.getParent().getId();
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

	public Integer getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(Integer isOrg) {
		this.isOrg = isOrg;
	}
	
}
