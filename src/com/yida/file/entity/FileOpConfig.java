package com.yida.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="file_op_config")
public class FileOpConfig {
	
	@Id
	@Column(name="module", columnDefinition="varchar(40)")
	private String module; // 模块Id
	
	@Column(name="module_name", columnDefinition="varchar(40)")
	private String moduleName; // 模块名
	
	@Column(name="ref_entity", columnDefinition="varchar(40)")
	private String refEntity; // 关联实体表名
	
	@Column(name="ref_table", columnDefinition="varchar(40)")
	private String refTable; // 关联数据库表名
	
	@Column(name="upload_path_resolver", columnDefinition="varchar(150)")
	private String uploadPathResolver; // 上传路径解析器
	
	@Column(name="refuse_file_type", columnDefinition="varchar(150)")
	private String refuseFileType; // 文件类型黑名单
	
	@Column(name="accept_file_type", columnDefinition="varchar(150)")
	private String acceptFileType; // 文件类型白名单

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRefEntity() {
		return refEntity;
	}

	public void setRefEntity(String refEntity) {
		this.refEntity = refEntity;
	}

	public String getRefTable() {
		return refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public String getUploadPathResolver() {
		return uploadPathResolver;
	}

	public void setUploadPathResolver(String uploadPathResolver) {
		this.uploadPathResolver = uploadPathResolver;
	}

	public String getRefuseFileType() {
		return refuseFileType;
	}

	public void setRefuseFileType(String refuseFileType) {
		this.refuseFileType = refuseFileType;
	}

	public String getAcceptFileType() {
		return acceptFileType;
	}

	public void setAcceptFileType(String acceptFileType) {
		this.acceptFileType = acceptFileType;
	}
}
