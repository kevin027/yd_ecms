package com.yida.file.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="file_info")
public class FileInfo {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition = "VARCHAR(32)")
	private String id;
	
	@Column(name="name", columnDefinition="NVARCHAR(200)")
	private String name; // 文件名称
	
	@Column(name="suffix", columnDefinition="NVARCHAR(20)")
	private String suffix; // 文件后缀
	
	@Column(name="save_path", columnDefinition="NVARCHAR(800)")
	private String savePath; // 文件保存路径（相对路径）
	
	@Column(name="ref_module", columnDefinition="VARCHAR(20)")
	private String refModule; // 配置Id
	
	@Column(name="ref_record_id", columnDefinition="VARCHAR(32)")
	private String refRecordId; // 关联的记录ID
	
	@Column(name="is_hide")
	private Boolean isHide; // 记录的可见性
	
	@Column(name="create_date")
	private Date createDate; // 记录生成日期
	
	@Column(name="create_user_id", columnDefinition="varchar(32)")
	private String createUserId;
	
	@Column(name="create_step_id", columnDefinition="varchar(32)")
	private String createStepId;
	
	@Column(name="remark", columnDefinition="varchar(1000)")
	private String remark; // 备注
	
	@OneToMany(cascade={CascadeType.REMOVE}, mappedBy = "file", fetch=FetchType.LAZY)
	@OrderBy("opDate desc")
	private List<FileOpLog> opLogs; // 文件的操作记录
	
	@Transient
	private Integer version; // 版本号
	
	@Transient
	private String createStepName; // 步骤名称
	
	@Transient
	private String createUserName; // 上传用户名称
	
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
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getRefModule() {
		return refModule;
	}
	public void setRefModule(String module) {
		this.refModule = module;
	}
	public String getRefRecordId() {
		return refRecordId;
	}
	public void setRefRecordId(String refRecordId) {
		this.refRecordId = refRecordId;
	}
	public Boolean getIsHide() {
		return isHide;
	}
	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}
	public List<FileOpLog> getOpLogs() {
		return opLogs;
	}
	public void setOpLogs(List<FileOpLog> opLogs) {
		this.opLogs = opLogs;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreateStepName() {
		return createStepName;
	}

	public void setCreateStepName(String createStepName) {
		this.createStepName = createStepName;
	}

	public String getCreateStepId() {
		return createStepId;
	}

	public void setCreateStepId(String createStepId) {
		this.createStepId = createStepId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
}
