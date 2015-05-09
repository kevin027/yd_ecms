package com.yida.core.base.entity;

import com.yida.core.common.ztree.IZtreeData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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

    /**
     * 功能名称
     */
	@Column(name="name", columnDefinition="nvarchar(20)", nullable=false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type", columnDefinition="VARCHAR(20)", nullable=false)
	private Type type;

    /**
     * 功能是否隐藏，超级管理员可以通过设置该字段来控制系统是否拥有该功能。
     */
    @Column(name="isHide")
    private Boolean isHide;

    /**
     * 功能是否有效，系统管理员可以通过设置该字段来控制用户能否使用该功能。
     */
	@Column(name="invalid", columnDefinition="tinyint", nullable=false)
	private Boolean invalid;

    /**
     * 功能所属的层级，用数字代表所属的层级，从1开始。该字段属于冗余，方便查询，提高效率。
     */
	@Column(name="hierarchy", columnDefinition="tinyint", nullable=false)
	private Integer hierarchy = Integer.valueOf(1);
	
	@Column(name="isSys", columnDefinition="tinyint", nullable=false)
	private Boolean isSys;

    /**
     * 功能排序码，针对兄弟节点功能的而言的排序码，和层级字段配合可以生成树状顺序的列表。
     */
	@Column(name="sortCode", length=2, columnDefinition="varchar(10)")
	private String sortCode;

    /**
     * 功能编码，用于编码人员获取指定功能，属于编码辅助字段。
     */
	@Column(name="code", columnDefinition="varchar(100)")
	private String code;

    /**
     * 功能代表的图标信息
     */
	@Column(name="icon", columnDefinition="varchar(50)")
	private String icon;

    /**
     * 功能请求的资源地址
     */
	@Column(name="href", columnDefinition="varchar(100)")
	private String href;

    /**
     * 功能的分组标识，针对兄弟节点功能的而言的分组标识，属于编码辅助字段。
     */
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
