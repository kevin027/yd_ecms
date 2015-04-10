package com.yida.core.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "s_account")
public class Account implements Serializable, Comparable<Account> {
	
	private static final long serialVersionUID = 1338569976216929519L;
	
	public enum Type {
		ADMIN,
		STAFF;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition="VARCHAR(32)")
	protected String id;

	@Column(name="accounts", columnDefinition="NVARCHAR(50)", unique=true)
	protected String accounts;

	@Column(name="password", columnDefinition="NVARCHAR(20)")
	protected String password;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="type", columnDefinition="tinyint")
	private Type type;
	
	@Column(name="invalid", columnDefinition="tinyint")
	protected Boolean invalid;
	
	@Column(name="createDate")
	protected Date createDate;
	
	@ManyToMany(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinTable(name="mp_account_role", joinColumns=@JoinColumn(name="accountId"), inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="staffId", nullable=true)
	private Staff staff;
	
	@Column(name="auditOrgId", columnDefinition="VARCHAR(32)")
	private String auditOrgId; // 所属机构，默认为创建人所在的机构，如果绑定了新用户后会变成新用户所在的机构
	
	@Column(name="ikey", columnDefinition="VARCHAR(80)")
	private String ikey;
	
	@Transient
	private List<Function> functions;
	
	@Transient
	private String defaultAuditOrgId;
	
	@Transient
	private String currentAuditOrgId;
	
	@Transient
	private boolean isLock;
	
	public String getRoleNames() {
		if (null == roles || 0 == roles.size()) return null;
		StringBuilder sb = new StringBuilder();
		for (Role r : roles) {
			sb.append(r.getName()).append(",");
		}
		return sb.substring(0, sb.length()-1);
	}
	
	public String getShowName() {
		if (Type.STAFF == this.getType()) {
			if (null != staff) return staff.getName();
		}
		else if (Type.ADMIN == this.getType()) {
			return "";
		}
		return null;
	}
	
	public String getStaffName() {
		if (null != staff) {
			return staff.getName();
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Account o) {
		return 1;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShowPhoto() {
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String account) {
		this.accounts = account;
	}
	
	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean isValid) {
		this.invalid = isValid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getDefaultAuditOrgId() {
		return defaultAuditOrgId;
	}

	public void setDefaultAuditOrgId(String defaultAuditOrgId) {
		this.defaultAuditOrgId = defaultAuditOrgId;
	}

	public String getCurrentAuditOrgId() {
		return currentAuditOrgId;
	}

	public void setCurrentAuditOrgId(String currentAuditOrgId) {
		this.currentAuditOrgId = currentAuditOrgId;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public String getIkey() {
		return ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	public String getAuditOrgId() {
		return auditOrgId;
	}

	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}

}
