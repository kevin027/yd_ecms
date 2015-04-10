package com.yida.core.base.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Role;
import com.yida.core.base.vo.ListRoleForm;
import com.yida.core.base.vo.SaveRoleForm;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service("roleService")
@Scope("singleton")
@Lazy(true)
public class RoleService extends BaseService {
	
	/**
	 * 根据角色ID获取角色记录
	 * @param selRoleId
	 * @return
	 */
	public Role getRoleById(String selRoleId) {
		return roleDao.get(selRoleId);
	}

	/**
	 * 根据流程角色ID删除角色记录及角色关联其它表的关联关系
	 * @param roleId
	 */
	@Transactional
	public void deleteRoleByRoleId(String roleId) {
		Role e = roleDao.get(roleId);
		
		// 检查数据是否存在
		if (null == e) {
			throw new EntityNotFoundException(Role.class, roleId);
		}
		
		// 取消和功能的关联关系
		e.getFunctions().clear();
		
		// 取消和账号的关联关系
		for (Account a : e.getAccounts()) {
			a.getRoles().remove(e);
		}
		
		// 移除数据
		this.roleDao.remove(e);
	}

	/**
	 * 根据角色ID更新Role所包含的权限
	 * @param roleId
	 * @param functions
	 */
	@Transactional
	public void updateFunctionsForRole(String roleId, String functionIds) {
		Role e = roleDao.get(roleId);
		
		// 检查数据是否存在
		if (null == e) {
			throw new EntityNotFoundException(Role.class, roleId);
		}
		
		// 更新角色包含的权限
		if (null != functionIds && !"".equals(functionIds)) {
			functionIds = functionIds.replaceAll("^,|,$", "");
			String[] fids = functionIds.split(",");
			List<Function> fs = new ArrayList<Function>(fids.length);
			for (String fid : fids) {
				if (StringUtils.isMeaningFul(fid)) {
					fs.add(new Function(fid));
				}
			}
			e.setFunctions(fs);
		} else {
			e.setFunctions(null);
		}
	}
	
	/**
	 * 根据角色ID更新Role所包含的账号
	 * @param roleId
	 * @param accounts
	 */
	@Transactional
	public void updateAccountsForRole(String roleId, String accountIds) {
		Role e = roleDao.get(roleId);
		
		// 检查数据是否存在
		if (null == e) {
			throw new EntityNotFoundException(Role.class, roleId);
		}
		
		// 移除操作角色当前关联的账号
		for (Account a : e.getAccounts()) {
			a.getRoles().remove(e);
		}
		
		// 添加操作角色新的关联的账号
		if (null != accountIds && !"".equals(accountIds)) {
			accountIds = accountIds.replaceAll("^,|,$", "");
			String[] sids = accountIds.split(",");
			Set<Account> ss = new HashSet<Account>(sids.length);
			Account account = null;
			for (String sid : sids) {
				if (StringUtils.isMeaningFul(sid)) {
					account = accountDao.get(sid);
					if (null != account) {
						account.getRoles().add(e);
					}
				}
			}
			e.setAccounts(ss);
		} 
	}

	/**
	 * 获取所有的角色
	 * @return
	 */
	public List<Role> listRole(ListRoleForm query, PageInfo page) {
		StringBuilder sb = new StringBuilder("from s_role o where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (null != query) {
			
			String roleName = StringUtils.notNull(query.getRoleName());
			if (!roleName.equals("")) {
				sb.append(" and o.name like ?");
				params.add("%" + query.getRoleName() + "%");
			}
			
			String staffName = StringUtils.notNull(query.getStaffName());
			if (!staffName.equals("")) {
				sb.append(" and exists (select s.id from s_staff s inner join mp_staff_role mp_sr on s.id = mp_sr.staff_id and mp_sr.role_id = o.id and s.name like ?)");
				params.add("%" + query.getStaffName() + "%");
			}
			
			if (null != query.getInvalid()) {
				sb.append(" and o.invalid = ?");
				params.add(query.getInvalid());
			}
			
			if (null != query.getAuditOrgId() && !"".equals(query.getAuditOrgId())) {
				sb.append(" and o.audit_org_id = ?");
				params.add(query.getAuditOrgId());
			}
			
		}
		
		if (null != page) {
			int total = roleDao.findCountBySql("select count(distinct o.id) " + sb, params.toArray());
			page.setTotalResult(total);
		}
		
		List<Role> list = roleDao.findListBySql("select o.* " + sb.toString() + " order by o.name", params.toArray(), page);
		return list;
	}

	/**
	 * 获取某个账户所拥有的所有角色
	 * @param account
	 * @return
	 */
	public List<Role> listRoleByAccountId(String accountId) {
		List<Role> list = roleDao.listRoleByAccountId(accountId);
		return list;
	}

	/**
	 * 根据表单保存角色信息
	 * @param saveRoleForm
	 */
	@Transactional
	public Role saveRole(SaveRoleForm saveRoleForm) {
		Role r = validImportField(saveRoleForm);
		roleDao.persist(r);
		return r;
	}

	/**
	 * 根据表单更新角色信息
	 * @param saveRoleForm
	 */
	@Transactional
	public Role updateRole(SaveRoleForm saveRoleForm) {
		Role r = validImportField(saveRoleForm);
		Role old = roleDao.get(r.getId());
		old.setName(r.getName());
		old.setInvalid(r.getInvalid());
		old.setRemark(r.getRemark());
		return old;
	}

	@Transactional
	private Role validImportField(SaveRoleForm roleForm) {
		Role r = new Role();
		if (StringUtils.isMeaningFul(roleForm.getRoleId())) {
			r.setId(roleForm.getRoleId());
		}
		
		// 账号检查
		String roleName = StringUtils.notNull(roleForm.getRoleName()).trim();
		if (0 == roleName.length()) {
			throw new IllegalStateException("角色名称不能为空。");
		}
		if (roleDao.roleNameIsExists(roleName, roleForm.getRoleId())) {
			throw new IllegalStateException("角色名称已经存在。");
		}
		
		if (!StringUtils.isMeaningFul(roleForm.getAuditOrgId())) {
			throw new IllegalStateException("没有指定角色所属机构。");
		} 
		r.setAuditOrg(new AuditOrg(roleForm.getAuditOrgId()));
		
		r.setName(roleName);
		r.setInvalid(roleForm.getInvalid());
		r.setRemark(roleForm.getRemark());
		return r;
	}
	
}
