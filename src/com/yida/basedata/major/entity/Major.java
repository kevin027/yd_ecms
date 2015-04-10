package com.yida.basedata.major.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 专业类型
 * @author Administrator
 *
 */
@Entity
@Table(name = "db_major")
public class Major implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition="VARCHAR(32)")
	private String id;

	@Column(name="name", columnDefinition="NVARCHAR(50)", nullable=true, unique=true)
	private String name;
	
	@Transient
	private String text;
	
	@Column(name="invalid", columnDefinition="TINYINT", nullable=false)
	private Boolean invalid;
	
	@Column(name="remark", columnDefinition="NVARCHAR(400)", nullable=true)
	private String remark;
	
	private @Transient String state;
	
	@ManyToOne(cascade={}, fetch=FetchType.LAZY, optional=true, targetEntity=Major.class)
	@JoinColumn(name="parent_id", nullable=true)
	private Major parent;
	
	@OneToMany(targetEntity=Major.class, cascade={CascadeType.ALL}, mappedBy = "parent", fetch=FetchType.LAZY)  
	@OrderBy("id")
	private List<Major> children;

	@Override
	public String toString() {
		return "Major [name=" + name + "]";
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
		Major other = (Major) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Major> getChildren() {
		return children;
	}

	public void setChildren(List<Major> children) {
		this.children = children;
	}

	public Major getParent() {
		return parent;
	}

	public void setParent(Major parent) {
		this.parent = parent;
	}

	public String getText() {
		return name;
	}

	public void setText(String text) {
		this.text = text;
	}

}
