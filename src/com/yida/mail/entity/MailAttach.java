package com.yida.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mail_attach")
public class MailAttach {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition="VARCHAR(32)")
	private String id;
	
	/**
	 * 所关联的邮件的ID
	 */
	@Column(name="mail_id", columnDefinition="VARCHAR(32)")
	private String mailId;
	
	/**
	 * 上传文件的格式
	 */
	@Column(name="upload_file_type", columnDefinition="VARCHAR(20)")
	private String uploadFileType;
	
	/**
	 * 上传文件的名称
	 */
	@Column(name="upload_file_name", columnDefinition="NVARCHAR(200)")
	private String uploadFileName;
	
	/**
	 * 上传文件的大小
	 */
	@Column(name="upload_file_size")
	private Long uploadFileSize;
	
	/**
	 * 文件保存在服务器中的位置
	 */
	@Column(name="save_file_path", columnDefinition="NVARCHAR(400)")
	private String saveFilePath;
	
	/**
	 * 文件保存在服务器中的名称
	 */
	@Column(name="save_file_name", columnDefinition="NVARCHAR(200)")
	private String saveFileName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getUploadFileType() {
		return uploadFileType;
	}

	public void setUploadFileType(String uploadFileType) {
		this.uploadFileType = uploadFileType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public Long getUploadFileSize() {
		return uploadFileSize;
	}

	public void setUploadFileSize(Long uploadFileSize) {
		this.uploadFileSize = uploadFileSize;
	}
	
}
