package com.yida.file.vo;

import java.util.Date;

import com.yida.core.base.entity.Staff;

public class ListFileForm {
	
	private String refModule;
	private String refRecordId;
	private String type;
	private Date uploadFromDate;
	private Date uploadToDate;
	
	private Staff uploadMan;
	
	private String stepId;
	
	public String getRefModule() {
		return refModule;
	}
	public void setRefModule(String module) {
		this.refModule = module;
	}
	public String getRefRecordId() {
		return refRecordId;
	}
	public void setRefRecordId(String refId) {
		this.refRecordId = refId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getUploadFromDate() {
		return uploadFromDate;
	}
	public void setUploadFromDate(Date uploadFromDate) {
		this.uploadFromDate = uploadFromDate;
	}
	public Date getUploadToDate() {
		return uploadToDate;
	}
	public void setUploadToDate(Date uploadToDate) {
		this.uploadToDate = uploadToDate;
	}
	public Staff getUploadMan() {
		return uploadMan;
	}
	public void setUploadMan(Staff uploadAccount) {
		this.uploadMan = uploadAccount;
	}
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	
}
