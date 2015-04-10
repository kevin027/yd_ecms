package com.yida.file.vo;

import java.io.File;

public class OutcomeFileOpForm {
	
	private String outcomeFileId;
	private String fileName;
	private String fileContentType;
	
	private File file;
	private String businessId;
	private String stepId;
	private String remark;
	
	private String refModule;
	private String refRecordId;
	
	public FileUploadForm toFileUploadForm() {
		FileUploadForm ff = new FileUploadForm();
		ff.setRefModule(refModule);
		ff.setRefRecordId(refRecordId);
		ff.setUpload(file);
		ff.setUploadFileName(fileName);
		ff.setUploadContentType(fileContentType);
		ff.setCreateStepId(stepId);
		ff.setRemark(remark);
		return ff;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getRefModule() {
		return refModule;
	}
	public void setRefModule(String refModule) {
		this.refModule = refModule;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getOutcomeFileId() {
		return outcomeFileId;
	}
	public void setOutcomeFileId(String outcomeFileId) {
		this.outcomeFileId = outcomeFileId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefRecordId() {
		return refRecordId;
	}

	public void setRefRecordId(String refRecordId) {
		this.refRecordId = refRecordId;
	}
	
}
