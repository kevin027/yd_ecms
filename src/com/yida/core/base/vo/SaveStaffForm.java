package com.yida.core.base.vo;

import java.util.List;

import com.yida.core.base.entity.Org;
import com.yida.core.base.entity.Staff;
import com.yida.core.common.enums.Sex;

public class SaveStaffForm {

	private String staffId;
	private String name;
	private Sex sex;
	private String email;
	private String phone;
	private String remark;
	private String orgIds;
	
	public SaveStaffForm() {
		
	}
	
	public SaveStaffForm(Staff staff) {
		if (null != staff) {
			this.staffId = staff.getId();
			this.name = staff.getName();
			this.sex = staff.getSex();
			this.email = staff.getEmail();
			this.phone = staff.getPhone();
			this.remark = staff.getRemark();
			
			List<Org> orgs = staff.getOrgs();
			if (null != orgs && 0 < orgs.size()) {
				Org org = orgs.get(orgs.size()-1);
				StringBuilder sb = new StringBuilder(org.getId());
				while(null != org.getParent()) {
					org = org.getParent();
					sb.insert(0, ",");
					sb.insert(0, org.getId());
				}
				this.orgIds = sb.toString();
			}
		}
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
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}
	
}
