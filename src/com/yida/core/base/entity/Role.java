package com.yida.core.base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "s_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", columnDefinition = "VARCHAR(32)")
	private String id;
	
	@Column(name="name", columnDefinition = "NVARCHAR(50)", nullable=true)
	private String name;
	
	@Column(name="remark", columnDefinition = "NVARCHAR(400)", nullable=true)
	private String remark;
	
	@Column(name="invalid")
	private Boolean invalid;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="auditOrg", nullable=true)
	private AuditOrg auditOrg;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="roles")
	private Set<Account> accounts;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="mp_role_function", joinColumns=@JoinColumn(name="roleId"), inverseJoinColumns=@JoinColumn(name="function_id"))
	@OrderBy("hierarchy asc, sortCode asc")
	private List<Function> functions;
	
	public Role () {
		
	}
	
	public Role(String id) {
		super();
		this.id = id;
	}
	
	// 用于easyui表格显示
	public String getAccountNames() {
		if (null != accounts && 0 < accounts.size()) {
			StringBuilder sb = new StringBuilder();
			for (Account a : accounts) {
				sb.append(a.getAccounts() + ",");
			}
			return sb.substring(0, sb.length()-1);
		}
		return null;
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
		Role other = (Role) obj;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	public AuditOrg getAuditOrg() {
		return auditOrg;
	}

	public void setAuditOrg(AuditOrg auditOrg) {
		this.auditOrg = auditOrg;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
