package com.yida.core.base.entity;

import com.yida.core.common.ztree.IZtreeData;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="s_org")
@Inheritance(strategy=InheritanceType.JOINED)
public class Org implements Serializable, IZtreeData<Org>{
	
	protected static final long serialVersionUID = 8713608106558908558L;
	
	@Id
	@Column(name="id", columnDefinition="VARCHAR(32)")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	protected String id;
	
	@Column(name="name", columnDefinition = "nvarchar(50)", nullable = false)
	protected String name;
	
	@Column(name="code", columnDefinition="varchar(20)", unique=true, nullable=false)
	protected String code;
	
	@Column(name="hierarchy", columnDefinition="tinyint", nullable=false)
	protected Integer hierarchy;
	
	@Column(name="sortCode", columnDefinition="varchar(10)", nullable=false)
	protected String sortCode;
	
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="parentId")
	protected Org parent;
	
	@OneToMany(mappedBy="parent", cascade={CascadeType.PERSIST,CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@OrderBy("sortCode")
	protected List<Org> children;
	
	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="leaderId", columnDefinition="varchar(32)")
	protected Staff leader;
	
	@ManyToMany(mappedBy="orgs", cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	protected Set<Staff> staffs;
	
	@Column(name="isOrg", columnDefinition="tinyint", nullable=true)
	protected Integer isOrg;
	
	public Org() {}
	
	public Org(String id) {
		this.id = id;
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
		Org other = (Org) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String getFullName() {
		StringBuilder sb = new StringBuilder(this.name);
		Org cur = this.getParent();
		while (null != cur) {
			sb.insert(0, "->").insert(0, cur.getName());
			cur = cur.getParent();
		}
		return sb.toString();
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

	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	@Override
	public List<Org> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<Org> children) {
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public Staff getLeader() {
		return leader;
	}

	public void setLeader(Staff leader) {
		this.leader = leader;
	}

	public Integer getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(Integer isOrg) {
		this.isOrg = isOrg;
	}
	
}
