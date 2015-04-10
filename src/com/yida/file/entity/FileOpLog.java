package com.yida.file.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.yida.core.base.entity.Staff;

@Entity
@Table(name="file_op_log")
public class FileOpLog {
	
	public enum OpType {
		UPLOAD,DOWNLOAD,HIDE;
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition = "VARCHAR(32)")
	private String id;
	
    @ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="op_man")
	private Staff opMan; // 操作人
	
    @Column(name="op_date")
	private Date opDate; // 操作时间
	
    @Enumerated(EnumType.STRING)
    @Column(name="op_type", columnDefinition="varchar(20)")
	private OpType opType; // 操作类型
    
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="file_info_id")
    private FileInfo file; // 关联的文件信息
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Staff getOpMan() {
		return opMan;
	}
	public void setOpMan(Staff opMan) {
		this.opMan = opMan;
	}
	public Date getOpDate() {
		return opDate;
	}
	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}
	public OpType getOpType() {
		return opType;
	}
	public void setOpType(OpType opType) {
		this.opType = opType;
	}
	public FileInfo getFile() {
		return file;
	}
	public void setFile(FileInfo file) {
		this.file = file;
	}
	
}
