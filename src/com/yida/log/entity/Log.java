package com.yida.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "s_log")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", columnDefinition="BIGINT")
	private Long id;

	@Column(name="operator", columnDefinition="NVARCHAR(50)", nullable=true)
	private String operator;//操作人
	
	@Column(name="ip", columnDefinition="NVARCHAR(100)", nullable=true)
	private String ip;//操作ip
	
	@Column(name="op_date", columnDefinition="datetime")
	private Date opDate;//操作时间
	
	@Column(name="op_type", columnDefinition="NVARCHAR(100)", nullable=true)
	private String opType;//操作类型
	
	@Column(name="module", columnDefinition="NVARCHAR(255)", nullable=true)
	private String module;//操作模块
	
	@Column(name="description", columnDefinition="NVARCHAR(400)", nullable=true)
	private String description;//详细内容

	@Override
	public String toString() {
		return "Log [operator=" + operator + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
