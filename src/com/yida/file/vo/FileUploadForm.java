package com.yida.file.vo;

import java.io.File;

import com.yida.core.base.entity.Staff;

public class FileUploadForm {
	
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	private Staff opMan;
	private String refModule;
	private String refRecordId;
	private String remark;
	
	private String createStepId;
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public Staff getOpMan() {
		return opMan;
	}
	public void setOpMan(Staff opMan) {
		this.opMan = opMan;
	}
	public String getRefModule() {
		return refModule;
	}
	public void setRefModule(String refModule) {
		this.refModule = refModule;
	}
	public String getRefRecordId() {
		return refRecordId;
	}
	public void setRefRecordId(String refRecordId) {
		this.refRecordId = refRecordId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateStepId() {
		return createStepId;
	}
	public void setCreateStepId(String createStepId) {
		this.createStepId = createStepId;
	}
}
