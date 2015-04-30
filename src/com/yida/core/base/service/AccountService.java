package com.yida.core.base.service;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Org;
import com.yida.core.base.entity.Role;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.ListAccountForm;
import com.yida.core.base.vo.ModifyPasswordForm;
import com.yida.core.base.vo.SaveAccountForm;
import com.tools.sys.PageInfo;
import com.yida.core.exception.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("accountService")
@Scope("singleton")
@Lazy(true)
public class AccountService extends BaseService {
	
	public Account getAccountById(String id) {
		return accountDao.get(id);
	}

	
	public Account getAccountByVerifyPassword(String accounts, String password) {
		return accountDao.getAccountByVerifyPassword(accounts, password);
	}

	
	@Transactional
	public Account handleLogin(String accounts,String password) {
		Account loginAccount = accountDao.getAccountByVerifyPassword(accounts, password);
		if (null != loginAccount) {
			//Cookie accountsCookie = new Cookie("accounts", loginForm.getAccounts());
			///accountsCookie.setMaxAge((int) Timer.ONE_DAY);
			//accountsCookie.setPath(request.getContextPath());
			//response.addCookie(accountsCookie);
		} else {
            System.out.println("账号或密码不正确。");
		}
		return loginAccount;
	}

	
	public List<Account> listAccount(ListAccountForm query, PageInfo page) {
		StringBuffer sb = new StringBuffer("from " + accountDao.getTableName() + " o where o.type <> 0");
		List<Object> paramList = new ArrayList<Object>();
		
		if (null != query) {
			String accounts = StringUtils.notNull(query.getAccounts());
			if (0 < accounts.length()) {
				sb.append(" and o.accounts like ?");
				paramList.add("%" + accounts + "%");
			}
			
			if (null != query.getCreateDateFrom()) {
				sb.append(" and o.createDate > ?");
				paramList.add(query.getCreateDateFrom());
			}
			
			if (null != query.getCreateDateTo()) {
				Calendar delayCalendar = Calendar.getInstance();
				delayCalendar.setTime(query.getCreateDateTo());
				delayCalendar.add(Calendar.DAY_OF_YEAR, 1);
				sb.append(" and o.createDate < ?");
				paramList.add(delayCalendar.getTime());
			}
			
			String auditOrgId = StringUtils.notNull(query.getAuditOrgId()).trim();
			if (0 < auditOrgId.length()) {
				sb.append(" and (o.auditOrgId = ? or o.staffId in (select s.id from s_staff s inner join mp_staff_org mp on s.id = mp.staffId where dbo.getAuditOrgIdByOrgId(mp.orgId) = ?))");
				paramList.add(auditOrgId);
				paramList.add(auditOrgId);
			}
			
			String staffName = StringUtils.notNull(query.getStaffName()).trim();
			if (0 < staffName.length()) {
				sb.append(" and o.staffId in (select s.id from s_staff s where name like ?)");
				paramList.add("%" + staffName + "%");
			}
		}
		
		String fromSql = sb.toString();
		Object[] params = paramList.toArray();
		if (null != page) {
			int total = accountDao.findCountBySql("select count(o.id) " + fromSql, params);
			page.setTotalResult(total);
		}
		List<Account> list = accountDao.findListBySql("select o.* " + fromSql + " order by o.accounts", params, page);
		return list;
	}

	
	@Transactional
	public Account updateAccount(SaveAccountForm saveAccountForm) {
		Account account = this.validImportField(saveAccountForm);
		Account old = this.accountDao.get(account.getId());
		old.setAccounts(account.getAccounts());
		old.setInvalid(account.getInvalid());
		old.setRoles(account.getRoles());
		old.setStaff(account.getStaff());
		return old;
	}

	
	@Transactional
	public Account saveAccount(SaveAccountForm saveAccountForm) {
		Account o = this.validImportField(saveAccountForm);
		// 初始化新建账号属性的默认值。
		o.setPassword(this.getDefaultPassword());
		o.setType(Account.Type.STAFF);
		o.setCreateDate(new Date());
		this.accountDao.persist(o);
		return o;
	}
	
	@Transactional
	private Account validImportField(SaveAccountForm saveAccountForm) {
		
		Account o = new Account();
		o.setInvalid(saveAccountForm.getInvalid());
		o.setPassword(saveAccountForm.getPassword());
		
		String id = saveAccountForm.getId();
		if (StringUtils.isMeaningFul(id)) {
			o.setId(id);
		}
				
		// 账号名称处理
		String accounts = StringUtils.notNull(saveAccountForm.getAccounts()).trim();
		if (0 == accounts.length()) {
			throw new IllegalStateException("账号不能为空");
		}
		if (accountDao.accountIsExists(accounts, o.getId())) {
			throw new IllegalStateException("账号已经被使用");
		}
		o.setAccounts(accounts);
		
		if (StringUtils.isMeaningFul(saveAccountForm.getStaffId())) {
			// o.setStaff(new Staff(saveAccountForm.getStaffId()));
			Staff staff = this.staffDao.get(saveAccountForm.getStaffId());
			o.setStaff(staff);
			
			// 如果关联的人员已经有归属机构，将账号所属机构设置为人员所属机构。
			if (0 < staff.getOrgs().size()) {
				Org auditOrg = this.orgDao.getRootOrgByOrgId(staff.getOrgs().get(0).getId());
				o.setAuditOrgId(auditOrg.getId());
			}
		} else {
			o.setAuditOrgId(saveAccountForm.getAuditOrgId());
		}
		
		if (StringUtils.isMeaningFul(saveAccountForm.getRoleIds())) {
			String[] roleIds = saveAccountForm.getRoleIds().split(",");
			Set<Role> roles = new HashSet<>(roleIds.length);
			for (String roleId : roleIds) {
                if(StringUtils.isMeaningFul(roleId)) {
                    roles.add(this.roleDao.get(roleId));
                }
			}
			o.setRoles(roles);
		}
		return o;
	}

	
	@Transactional
	public Account delAccountById(String accountId) {
		Account e = accountDao.get(accountId);
		
		// 检查数据是否存在
		if (null == e) {
			throw new EntityNotFoundException(Account.class, accountId);
		}
		
		e.getRoles().clear();
		e.setStaff(null);
		
		// 移除数据
		this.accountDao.remove(e);
		return e;
	}

	/**
	 * 修改账户密码操作
	 * @param modifyPasswordForm
	 * @return
	 */
	@Transactional
	public void modAccountPassword(ModifyPasswordForm modifyPasswordForm) {
		if (!StringUtils.isMeaningFul(modifyPasswordForm.getId())) {
			throw new IllegalStateException("参数有误，不能确定要修改账号，请与管理员联系。");
		}
		
		Account e = accountDao.get(modifyPasswordForm.getId());
		if (null == e) {
			throw new IllegalStateException("没有找到对应的账号，请写管理员联系。");
		}
		
		String oldPassword = StringUtils.notNull(modifyPasswordForm.getOldPassword()).trim();
		if (!oldPassword.equals(e.getPassword())) {
			throw new IllegalStateException("原密码不正确。");
		}

		String newPassword = StringUtils.notNull(modifyPasswordForm.getNewPassword()).trim();
		if (!StringUtils.isMeaningFul(newPassword)) {
			throw new IllegalStateException("新密码规则不正确。");
		}

		e.setPassword(newPassword);
	}

	/**
	 * 重置账户密码
	 * @param accountId
	 */
	@Transactional
	public void resetAccountPassword(String accountId) {
		Account account = this.accountDao.get(accountId);
		account.setPassword(this.getDefaultPassword());
	}

	public String getDefaultPassword() {
		return "123";
	}

}
