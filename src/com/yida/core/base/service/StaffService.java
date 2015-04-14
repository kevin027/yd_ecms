package com.yida.core.base.service;

import com.tools.utils.Regexp;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.entity.Org;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.ListStaffForm;
import com.yida.core.base.vo.SaveStaffForm;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StaffService extends BaseService {
	
	
	public Map<String, String> getAllStaffIdAndName() {
		return this.staffDao.getAllStaffIdAndName();
	}

	/**
	 * 获取人员列表
	 * @param query
	 * @param pageInfo
	 * @return
	 */
	public List<Staff> listStaff(ListStaffForm query, PageInfo pageInfo) {
		StringBuffer sb = new StringBuffer("from " + staffDao.getTableName() + " o where 1=1");
		List<Object> params = new ArrayList<Object>();
		
		if (null != query) {
			
			if (StringUtils.isMeaningFul(query.getAuditOrgId())) {
				sb.append(" and o.id in (select mp.staffId from mp_staff_org mp where dbo.getAuditOrgIdByOrgId(mp.orgId) = ?)");
				params.add(query.getAuditOrgId());
			}

			if (StringUtils.isMeaningFul(query.getStaffName())) {
				sb.append(" and o.name like ?");
				params.add("%" + query.getStaffName().trim() + "%");
			}
			
			if (null != query.getCreateDateFrom()) {
				sb.append(" and o.createDate > ?");
				params.add(query.getCreateDateFrom());
			}
			
			if (null != query.getCreateDateTo()) {
				Calendar delayCalendar = Calendar.getInstance();
				delayCalendar.setTime(query.getCreateDateTo());
				delayCalendar.add(Calendar.DAY_OF_YEAR, 1);
				sb.append(" and o.createDate < ?");
				params.add(delayCalendar.getTime());
			}
			
			if (null != query.getOrgId() && !"".equals(query.getOrgId())) {
				Org org = this.orgDao.get(query.getOrgId());
				if (null != org) {
					sb.append(" and o.id in (select mp.staffId from mp_staff_org mp where mp.orgId in (");
					List<Org> stack = new ArrayList<Org>();
					stack.add(0, org);
					while (!stack.isEmpty()) {
						org = stack.remove(0);
						if (null != org.getChildren() && 0 < org.getChildren().size()) {
							stack.addAll(0, org.getChildren());
						}
						if (0 == stack.size()) {
							sb.append("?");
						} else {
							sb.append("?,");
						}
						params.add(org.getId());
					}
					sb.append("))");
				}
			}
			
		}
		
		if (null != pageInfo) {
			int total = staffDao.findCountBySql("select count(distinct o.id) " + sb, params.toArray());
			pageInfo.setTotalResult(total);
		}
		List<Staff> list = staffDao.findListBySql("select o.* " + sb.toString(), params.toArray(), pageInfo);
		return list;
	}

	/**
	 * 跟据人员ID获取人员对象
	 * @param staffId
	 * @return
	 */
	public Staff getStaffById(String staffId) {
		return this.staffDao.get(staffId);
	}
	
	/**
	 * 保存人员
	 * @param saveStaffForm
	 */
	@Transactional
	private Staff validImportField(SaveStaffForm saveStaffForm) {
		Staff staff = new Staff();
		
		// 用户ID
		if (StringUtils.isMeaningFul(saveStaffForm.getStaffId())) {
			staff.setId(saveStaffForm.getStaffId());
		}
		
		// 用户名处理
		String staffName = StringUtils.notNull(saveStaffForm.getName()).trim();
		if (0 == staffName.length()) {
			throw new IllegalStateException("名字不能为空");
		}
		if (staffDao.staffNameIsExists(saveStaffForm.getName(), saveStaffForm.getStaffId())) {
			throw new IllegalStateException("名字已经被使用");
		}
		staff.setName(staffName);
		
		// 电子邮件处理
		String email = StringUtils.notNull(saveStaffForm.getEmail()).trim();
		if (0 != email.length()) {
			if (!Regexp.EMAIL.validate(email)) {
				throw new IllegalStateException("电子邮箱格式错误");
			}
		}
		staff.setEmail(email);
		
		// 手机号码处理
		String phone = StringUtils.notNull(saveStaffForm.getPhone()).trim();
		if (0 != phone.length()) {
			if (!Regexp.PHONE_NO.validate(phone)) {
				throw new IllegalStateException("手机号码格式错误");
			}
		}
		staff.setPhone(phone);
		
		// 所属组织机构
		if (StringUtils.isMeaningFul(saveStaffForm.getOrgIds())) {
			String[] tempOrgId = saveStaffForm.getOrgIds().split(",");
			if (1 == tempOrgId.length) {
				throw new IllegalStateException("请选择一个所属部门。");
			}
			List<Org> list = new ArrayList<Org>(tempOrgId.length);
			list.add(new Org(tempOrgId[tempOrgId.length-1]));
			staff.setOrgs(list);
		} else {
			throw new IllegalStateException("没有确定所属的部门机构");
		}
		
		staff.setSex(saveStaffForm.getSex());
		staff.setRemark(saveStaffForm.getRemark());
		return staff;
	}

	@Transactional
	public Staff saveStaff(SaveStaffForm saveStaffForm) {
		if (!StringUtils.isMeaningFul(saveStaffForm.getStaffId())) {
			Staff staff = this.validImportField(saveStaffForm);
			staff.setCreateDate(new Date());
			staff.setInvalid(Boolean.FALSE);
			this.staffDao.persist(staff);
			return staff;
		}
		throw new IllegalStateException("提交数据有误，已经包含人员ID");
	}

	/**
	 * 修改人员信息
	 * @param saveStaffForm
	 */
	@Transactional
	public Staff updateStaff(SaveStaffForm saveStaffForm) {
		if (StringUtils.isMeaningFul(saveStaffForm.getStaffId())) {
			Staff staff = this.validImportField(saveStaffForm);
			Staff old = this.staffDao.get(saveStaffForm.getStaffId());

			old.setName(staff.getName());
			old.setSex(staff.getSex());
			old.setEmail(staff.getEmail());
			old.setPhone(staff.getPhone());
			old.setRemark(staff.getRemark());
			old.setOrgs(staff.getOrgs());
			return staff;
		}
		throw new IllegalStateException("提交数据有误，没有包含人员ID");
	}

	/**
	 * 根据ID删除人员信息
	 * @param staffId
	 */
	@Transactional
	public void deleteStaffById(String staffId) {
		Staff staff = staffDao.get(staffId);
		if (null == staff) {
			throw new EntityNotFoundException(Staff.class, staffId);
		}
		staff.setOrgs(null);
		staff.setAccounts(null);
		staffDao.remove(staff);
	}

	/**
	 * 根据ID删除人员信息(复选)
	 * @param staffIds
	 */
	@Transactional
	public void deleteStaffByIds(List<String> staffIds) {
		if (null == staffIds) throw new IllegalStateException("");
		if (null != staffIds) {
			for (String staffId : staffIds) {
				if (StringUtils.isMeaningFul(staffId)) {
					this.deleteStaffById(staffId);
				}
			}
		}
	}

	/**
	 * 根据组织机构ID获取人员信息
	 * @param orgId
	 * @return
	 */
	public List<Staff> findStaffByOrgId(String orgId) {
		return staffDao.findStaffByOrgId(orgId);
	}

	
	public AuditOrg getAuditOrgForStaff(Staff loginStaff) {
		if (null != loginStaff && null != loginStaff.getOrgs() && 0 < loginStaff.getOrgs().size()) {
			Org org = loginStaff.getOrgs().get(0);
			if (null == org) return null;
			while (null != org.getParent()) {
				org = org.getParent();
			}
			return auditOrgDao.get(org.getId());
		}
		return null;
	}

	
	public List<Staff> listStaffByStaffIds(String staffIds) {
		if (!StringUtils.isMeaningFul(staffIds)) return new ArrayList<>(0);
		return this.staffDao.listStaffByStaffIds(staffIds);
	}
}
