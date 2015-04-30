package com.yida.core.base.service;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Department;
import com.yida.core.base.entity.Org;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.ListDepartmentForm;
import com.yida.core.base.vo.SaveDepartmentForm;
import com.tools.sys.PageInfo;
import com.yida.core.exception.EntityNotFoundException;
import com.yida.core.exception.NameRepeatException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("departmentService")
@Scope("singleton")
@Lazy(true)
public class DepartmentService extends BaseService {
	
	/**
	 * 根据机构表的ID获取某机构下的所有部门
	 * @param auditOrgId
	 * @return
	 */
	public List<Department> getDepartmentByAuditOrgId(String auditOrgId) {
		return departmentDao.getDepartmentByAuditOrgId(auditOrgId);
	}

	/**
	 * 根据部门表的ID获取某部门下的所有直接从属关系的子部门
	 * @param departmentId
	 * @return
	 */
	public List<Department> getSubDepartmentByDepartmentId(String departmentId) {
		return departmentDao.getSubDepartmentByDepartmentId(departmentId);
	}

	/**
	 * 根据部门表的ID获取某部门下的所有部门，包括非直接从属关系的子部门。
	 * @param departmentId
	 * @return
	 */
	public List<Department> getAllSubDepartmentByDepartmentId(String departmentId) {
		return departmentDao.getAllSubDepartmentByDepartmentId(departmentId);
	}

	/**
	 * 根据部门ID来获取ID对象
	 * @param departmentId
	 * @return
	 */
	public Department getDepartmentById(String departmentId) {
		return departmentDao.get(departmentId);
	}

	/**
	 * 新增部门记录
	 * @param department
	 */
	public void saveDepartment(Department department) {
		boolean isExists = departmentDao.getDepartmentNameIsExists(department.getName()
				, null == department.getParent() ? null : department.getParent().getId(), null);
		if (null == department.getParent()) {
			throw new IllegalStateException("必须指定部门所属的机构。");
		}
		if (isExists) {
			throw new NameRepeatException(Department.class, department.getName());
		}
		this.departmentDao.persist(department);
	}

	/**
	 * 根据部门ID删除部门
	 * @param departmentId
	 */
	@Transactional
	public void deleteDepartmentByDepartmentId(String departmentId) {
		Org e = departmentDao.get(departmentId);
		
		// 检查数据是否存在
		if (null == e) {
			throw new EntityNotFoundException(Department.class, departmentId);
		}
		
		// 检查是否存在关联部门
		if (null != e.getChildren() && 0 < e.getChildren().size()) {
			throw new IllegalStateException("部门下仍存在关联的部门数据，暂不能删除。");
		}
		
		// 检查是否存在关联人员
		if (null != e.getStaffs() && 0 < e.getStaffs().size()) {
			throw new IllegalStateException("部门下仍存在关联的人员数据，暂不能删除。");
		}
		
		e.setParent(null);

		this.orgDao.remove(e);
	}

	
	public List<Department> listDepartment(ListDepartmentForm query, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder("from " + departmentDao.getTableName() + " o inner join s_org org on o.id = org.id where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (null != query) {
			if (StringUtils.isMeaningFul(query.getAuditOrgId())) {
				sb.append(" and o.auditOrgId = ?");
				params.add(query.getAuditOrgId());
			}
			
			if (StringUtils.isMeaningFul(query.getOrgId())) {
				Org org = this.orgDao.get(query.getOrgId());
				if (null != org) {
					sb.append(" and o.id in (");
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
					sb.append(")");
				}
			}
		}
		if (null != pageInfo) {
			int total = departmentDao.findCountBySql("select count(o.id) " + sb, params.toArray());
			pageInfo.setTotalResult(total);
		}
		List<Department> list = departmentDao.findListBySql("select o.* " + sb.toString() + " order by org.hierarchy", params.toArray(), pageInfo);
		return list;
	}

	/**
	 * 更新部门记录
	 * @param department
	 */
	@Transactional
	public void updateDepartment(Department department) {
		this.departmentDao.merge(department);
	}

	
	@Transactional
	public Department saveDepartment(SaveDepartmentForm saveDepartmentForm) {
		Department dept = validImportField(saveDepartmentForm);
		dept.setHierarchy(dept.getParent().getHierarchy().intValue() + 1);
		this.departmentDao.persist(dept);
		return dept;
	}
	
	
	@Transactional
	public Department updateDepartment(SaveDepartmentForm saveDepartmentForm) {
		Department dept = validImportField(saveDepartmentForm);
		Department old = this.departmentDao.get(dept.getId());
		old.setParent(dept.getParent());
		old.setName(dept.getName());
		old.setCode(dept.getCode());
		old.setSortCode(dept.getSortCode());
		old.setLeader(dept.getLeader());
		return old;
	}

	@Transactional
	private Department validImportField(SaveDepartmentForm saveDepartmentForm) {
		Department c = new Department();
		
		if (StringUtils.isMeaningFul(saveDepartmentForm.getId())) {
			c.setId(saveDepartmentForm.getId());
		}
		
		// 检查部门编码为空和重复的情况
		String code = StringUtils.notNull(saveDepartmentForm.getCode()).trim();
		if (0 == code.length()) {
			throw new IllegalStateException("部门编码不能为空。");
		}
		boolean codeIsExists = orgDao.orgPropertiyIsExists("code", code, c.getId());
		if (codeIsExists) {
			throw new IllegalStateException("部门编码已经被使用。");
		}
		c.setCode(code);
		
		// 检查部门排序为空的情况
		String sortCode = StringUtils.notNull(saveDepartmentForm.getSortCode()).trim();
		if (0 == sortCode.length()) {
			throw new IllegalStateException("部门排序不能为空。");
		}
		c.setSortCode(sortCode);
		
		// 获取父节点
		Org parent = this.orgDao.get(saveDepartmentForm.getParentId());
		if (null == parent) {
			throw new IllegalStateException("没有指定父级组织机构。");
		}
		c.setParent(parent);
		
		// 检查部门名称为空和重复的情况
		String departmentName = StringUtils.notNull(saveDepartmentForm.getName()).trim();
		if (0 == departmentName.length()) {
			throw new IllegalStateException("部门名称不能为空。");
		}
		if (null != parent.getChildren() && 0 < parent.getChildren().size()) {
			for (Org sb : parent.getChildren()) {
				if (!sb.getId().equals(c.getId()) && sb.getName().equals(departmentName)) {
					throw new IllegalStateException("该机构下已经存在该部门。");
				}
			}
		}
		c.setName(departmentName);
		
		String leaderId = StringUtils.notNull(saveDepartmentForm.getLeaderId()).trim();
		if (0 == leaderId.length()) {
			c.setLeader(null);
		} else {
			c.setLeader(new Staff(leaderId));
		}
		return c;
	}
	
}
