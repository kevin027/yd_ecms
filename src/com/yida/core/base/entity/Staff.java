package com.yida.core.base.entity;

import com.yida.core.common.enums.Sex;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="s_staff")
public class Staff implements Serializable {
	
	private static final long serialVersionUID = 2129452270149167582L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@Column(name="id", columnDefinition="VARCHAR(32)")
	private String id;
	
	@Column(name="name", columnDefinition="nvarchar(50)")
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="sex", columnDefinition="tinyint")
	private Sex sex;
	
	@Column(name="email", columnDefinition="varchar(50)")
	private String email;
	
	@Column(name="phone", columnDefinition="varchar(20)")
	private String phone;
	
	@Column(name="remark", columnDefinition="nvarchar(400)")
	private String remark;
	
	@Column(name="createDate")
	private Date createDate;
	
	@Column(name="invalid")
	protected Boolean invalid;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "staff", fetch=FetchType.LAZY)  
	private Set<Account> accounts;
	
	@ManyToMany(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinTable(name="mp_staff_org", joinColumns = @JoinColumn(name="staffId"), inverseJoinColumns=@JoinColumn(name="orgId"))
	@OrderBy("hierarchy, sortCode")
	private List<Org> orgs;
	
	@Column(name="isOrg", columnDefinition="tinyint", nullable=true)
	protected Integer isOrg;
	
	public String getOrgNames() {
		if (null != orgs && 0 < orgs.size()) {
			Org org = this.orgs.get(orgs.size()-1);
			StringBuilder sb = new StringBuilder(org.getName());
			Org temp = org;
			while(null != temp.getParent()) {
				temp = temp.getParent();
				sb.insert(0, "-");
				sb.insert(0, temp.getName());
			}
			return sb.toString();
		}
		return null;
	}
	
	public Staff() {
		
	}
	
	public Staff(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		Staff other = (Staff) obj;
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

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Org> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}

	public Integer getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(Integer isOrg) {
		this.isOrg = isOrg;
	}
	
}
