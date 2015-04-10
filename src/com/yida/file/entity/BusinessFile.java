package com.yida.file.entity;

import java.io.File;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.yida.core.base.entity.Staff;

@Entity
@Table(name = "file_business")
public class BusinessFile {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition = "VARCHAR(32)")
	private String id;
	
	@Column(name="name", columnDefinition="NVARCHAR(50)")
	private String name; // 资料名称
	
	@Column(name="business_id", columnDefinition="VARCHAR(32)")
	private String businessId; // 关联任务ID
	
	@Column(name="step_id", columnDefinition="VARCHAR(32)")
	private String stepId; // 关联任务步骤ID
	
	@Column(name="copies", nullable=true)
	private Integer copies = 1; //份数

	@Column(name="is_original", columnDefinition="TINYINT", nullable=true)
	private Boolean isOriginal; //原件

	@Column(name="is_copy", columnDefinition="TINYINT", nullable=true)
	private Boolean isCopy;  //复印件

	@Column(name="is_electronic", columnDefinition="TINYINT", nullable=true)
	private Boolean isElectronic; //电子版

	@Column(name="is_remand", columnDefinition = "TINYINT", nullable=true)
	private Boolean isRemand; // 是否归还
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="receive_staff_id")
	private Staff receiveStaff; // 收件人
	
	@Column(name = "receive_date")
	private Date receiveDate; // 收件日期
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="keepr_id", nullable=true)
	private Staff keeper; // 保管人
	
	@Column(name="providers", columnDefinition="NVARCHAR(200)", nullable=true)
	private String providers; // 提供方
	
	@Column(name="contact_man", columnDefinition="NVARCHAR(100)", nullable=true)
	private String contactMan; // 联系人
	
	@Column(name="contact_phone", columnDefinition="NVARCHAR(200)", nullable=true)
	private String contactPhone; // 联系电话
	
	@Column(name="remark", columnDefinition="NVARCHAR(max)", nullable=true)
	private String remark;
	
	@Transient
	private String refModule;
	
	@Transient
	private File upload;
	
	@Transient
	private String uploadFileName;
	
	@Transient
	private String uploadContentType;
	
	@Transient
	private String receiveStaffName;
	
	@Transient
	private String receiveDateStr;
	
	@Transient
	private String fileId;
	
	public String getReceiveStaffName() {
		if (null != receiveStaff) {
			return receiveStaff.getName();
		}
		return null;
	}
	
	public String getKeeperName() {
		if (null != keeper) {
			return keeper.getName();
		}
		return null;
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

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public Boolean getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(Boolean isOriginal) {
		this.isOriginal = isOriginal;
	}

	public Boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}

	public Boolean getIsElectronic() {
		return isElectronic;
	}

	public void setIsElectronic(Boolean isElectronic) {
		this.isElectronic = isElectronic;
	}

	public Boolean getIsRemand() {
		return isRemand;
	}

	public void setIsRemand(Boolean isRemand) {
		this.isRemand = isRemand;
	}

	public Staff getReceiveStaff() {
		return receiveStaff;
	}

	public void setReceiveStaff(Staff receiveStaff) {
		this.receiveStaff = receiveStaff;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Staff getKeeper() {
		return keeper;
	}

	public void setKeeper(Staff keeper) {
		this.keeper = keeper;
	}

	public String getProviders() {
		return providers;
	}

	public void setProviders(String providers) {
		this.providers = providers;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getRefModule() {
		return refModule;
	}

	public void setRefModule(String refModule) {
		this.refModule = refModule;
	}
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getReceiveDateStr() {
		return receiveDateStr;
	}

	public void setReceiveDateStr(String receiveDateStr) {
		this.receiveDateStr = receiveDateStr;
	}

	public void setReceiveStaffName(String receiveStaffName) {
		this.receiveStaffName = receiveStaffName;
	}
	
	public String getIsOriginal2() {
		return Boolean.TRUE.equals(isOriginal) ? "√" : null;
	}
	
	public String getIsCopy2() {
		return Boolean.TRUE.equals(isCopy) ? "√" : null;
	}
	
	public String getIsElectronic2() {
		return Boolean.TRUE.equals(isElectronic) ? "√" : null;
	}
	
	public String getIsRemand2() {
		return Boolean.TRUE.equals(isRemand) ? "√" : null;
	}
}
