package com.yida.file.vo;

import java.util.Date;

public class OutcomeFileInfo {
	
	private String id;
	private String fileName;
	private String opMan;
	private Date opDate;
	private String stepName;
	private String businessId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOpMan() {
		return opMan;
	}
	public void setOpMan(String opMan) {
		this.opMan = opMan;
	}
	public Date getOpDate() {
		return opDate;
	}
	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
}
