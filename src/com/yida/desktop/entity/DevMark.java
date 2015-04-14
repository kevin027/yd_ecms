package com.yida.desktop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "s_dev_mark")
public class DevMark {
	
	@Id
	@Column(name = "id", columnDefinition = "int")
	private Integer id;
	
	@Column(name = "markContent", columnDefinition = "NVARCHAR(1000)")
	private String markContent;
	
	@Column(name = "markDate")
	private Date markDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMarkContent() {
		return markContent;
	}
	public void setMarkContent(String markContent) {
		this.markContent = markContent;
	}
	public Date getMarkDate() {
		return markDate;
	}
	public void setMarkDate(Date markDate) {
		this.markDate = markDate;
	}

}
