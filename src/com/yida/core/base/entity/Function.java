package com.yida.core.base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.yida.core.common.ztree.IZtreeData;

@Entity
@Table(name="s_function")
public class Function implements Serializable, IZtreeData<Function> {

	private static final long serialVersionUID = 8105193940947277075L;
	
	public enum Type {
		MENU("菜单"),BUTTON("按钮"),DATA("数据"),PURVIEW("其它");
		String chinese;
		Type(String chinese) {
			this.chinese = chinese;
		}
		public String getChinese() {
			return chinese;
		}
	}

	@Id
	@Column(name="id", columnDefinition="nvarchar(20)")
	private String id;
	
	@Column(name="name", columnDefinition="nvarchar(20)", nullable=false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type", columnDefinition="VARCHAR(20)", nullable=false)
	private Type type;
	
	@Column(name="invalid", columnDefinition="tinyint", nullable=false)
	private Boolean invalid;
	
	@Column(name="hierarchy", columnDefinition="tinyint", nullable=false)
	private Integer hierarchy;
	
	@Column(name="isSys", columnDefinition="tinyint", nullable=false)
	private Boolean isSys;
	
	@Column(name="sortCode", length=2, columnDefinition="varchar(10)")
	private String sortCode;
	
	@Column(name="code", columnDefinition="varchar(100)")
	private String code;
	
	@Column(name="icon", columnDefinition="varchar(50)")
	private String icon;
	
	@Column(name="href", columnDefinition="varchar(100)")
	private String href;
	
	@Column(name="groups", columnDefinition="varchar(100)")
	private String groups;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="parentId", nullable=true)
	private Function parent;
	
	@OneToMany(targetEntity=Function.class, cascade={CascadeType.ALL}, mappedBy = "parent", fetch=FetchType.LAZY)  
	@OrderBy("id")  
	private List<Function> children;
	
	@ManyToMany(mappedBy="functions", fetch=FetchType.LAZY)
	private Set<Role> roles;
	
	public Function() {
		
	}
	
	public Function(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public Function getParent() {
		return parent;
	}

	public void setParent(Function parent) {
		this.parent = parent;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Boolean getIsSys() {
		return isSys;
	}

	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
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
		Function other = (Function) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public List<Function> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<Function> children) {
		this.children = children;
	}
	
}
