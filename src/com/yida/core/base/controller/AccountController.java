package com.yida.core.base.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.vo.ListAccountForm;
import com.yida.core.base.vo.ListRoleForm;
import com.yida.core.base.vo.ModifyPasswordForm;
import com.yida.core.base.vo.SaveAccountForm;
import com.yida.core.interceptors.Permission;

/**
 * 账号模块
 */
@Controller
@RequestMapping("account")
public class AccountController extends BaseController {
	
	/**
	 * 账号管理的主页面
	 */
	@RequestMapping("main")
	@Permission(code="MANAGE_ACCOUNT")
	public String main() {
		return "core/account/jsp/main";
	}
	
	/**
	 * 账号管理的主页面-查询操作（账号列表）
	 */
	@RequestMapping("listAccount")
	public String listAccount() {
		try {
			if (null == queryAccount) {
				queryAccount = new ListAccountForm();
			}
			queryAccount.setAuditOrgId(super.getCurrentAuditOrgId());
			List<Account> list = this.accountService.listAccount(queryAccount, pageInfo);
			
			List<String> includePropertys = Arrays.asList("id", "accounts", "invalid", "createDate", "roleNames", "staffName");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 账号管理主页-新增操作
	 */
	@RequestMapping("addAccount") 
	public String addAccount(HttpServletRequest request) {
		ListRoleForm roleForm = new ListRoleForm();
		roleForm.setAuditOrgId(super.getCurrentAuditOrgId());
		request.setAttribute("roles", roleService.listRole(roleForm, null));
		return "core/account/jsp/addAccount";
	}
	
	/**
	 * 账号新增页面-保存操作
	 */
	@RequestMapping("saveAccount")
	public String saveAccount() {
		JSONObject result = new JSONObject();
		try {
			if (null != super.getCurrentAccount()) {
				saveAccountForm.setAuditOrgId(super.getCurrentAuditOrgId());
			}
			this.accountService.saveAccount(saveAccountForm);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 账号管理主页面-修改操作
	 */
	@RequestMapping("modAccount") 
	public String modAccount(HttpServletRequest request) {
		ListRoleForm roleForm = new ListRoleForm();
		roleForm.setAuditOrgId(super.getCurrentAuditOrgId());
		request.setAttribute("roles", roleService.listRole(roleForm, null));
		saveAccountForm = new SaveAccountForm(this.accountService.getAccountById(accountId));
		return "core/account/jsp/modAccount";
	}
	
	/**
	 * 账号修改页面-保存修改操作
	 */
	@RequestMapping("updateAccount")
	public String updateAccount() {
		JSONObject result = new JSONObject();
		try {
			account = accountService.updateAccount(saveAccountForm);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 账号管理主页面-删除操作
	 */
	@RequestMapping("delAccount")
	public String delAccount() {
		JSONObject result = new JSONObject();
		try {
			account = accountService.delAccountById(accountId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modAccountPassword")
	public String modAccountPassword() {
		JSONObject result = new JSONObject();
		try {
			if (null == modifyPasswordForm) {
				modifyPasswordForm = new ModifyPasswordForm();
			}
			modifyPasswordForm.setId(super.getCurrentAccount().getId());
			accountService.modAccountPassword(modifyPasswordForm);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
			super.getCurrentAccount().setPassword(modifyPasswordForm.getNewPassword());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("resetAccountPassword")
	public String resetAccountPassword() {
		JSONObject result = new JSONObject();
		try {
			if (null == modifyPasswordForm) {
				modifyPasswordForm = new ModifyPasswordForm();
			}
			modifyPasswordForm.setId(super.getCurrentAccount().getId());
			accountService.resetAccountPassword(this.accountId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "重置成功。");
			super.getCurrentAccount().setPassword(modifyPasswordForm.getNewPassword());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "重置失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
}
