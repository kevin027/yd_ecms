package com.yida.core.base.controller;

import com.tools.sys.PageInfo;
import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.vo.ListAccountForm;
import com.yida.core.base.vo.ListRoleForm;
import com.yida.core.base.vo.ModifyPasswordForm;
import com.yida.core.base.vo.SaveAccountForm;
import com.yida.core.interceptors.Permission;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

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
    @ResponseBody
	@RequestMapping("listAccount")
	public String listAccount(PageInfo pageInfo,ListAccountForm query) {
		try {
			if (null == query) {
                query = new ListAccountForm();
			}
            query.setAuditOrgId(super.getCurrentAuditOrgId());
			List<Account> list = this.accountService.listAccount(query, pageInfo);
			
			List<String> includePropertys = Arrays.asList("id", "accounts", "invalid", "createDate", "roleNames", "staffName");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
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
    @ResponseBody
	@RequestMapping("saveAccount")
	public String saveAccount(SaveAccountForm form) {
		JSONObject result = new JSONObject();
		try {
			if (null != super.getCurrentAccount()) {
				form.setAuditOrgId(super.getCurrentAuditOrgId());
			}
			this.accountService.saveAccount(form);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	/**
	 * 账号管理主页面-修改操作
	 */
	@RequestMapping("modAccount") 
	public String modAccount(HttpServletRequest request,String accountId) {
		ListRoleForm roleForm = new ListRoleForm();
		roleForm.setAuditOrgId(super.getCurrentAuditOrgId());
		request.setAttribute("roles", roleService.listRole(roleForm, null));
        request.setAttribute("form",new SaveAccountForm(this.accountService.getAccountById(accountId)));
		return "core/account/jsp/modAccount";
	}
	
	/**
	 * 账号修改页面-保存修改操作
	 */
    @ResponseBody
	@RequestMapping("updateAccount")
	public String updateAccount(SaveAccountForm form) {
		JSONObject result = new JSONObject();
		try {
			Account account = accountService.updateAccount(form);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return jsonText;
	}
	
	/**
	 * 账号管理主页面-删除操作
	 */
    @ResponseBody
	@RequestMapping("delAccount")
	public String delAccount(String accountId) {
		JSONObject result = new JSONObject();
		try {
			Account account = accountService.delAccountById(accountId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return jsonText;
	}

    @ResponseBody
	@RequestMapping("modAccountPassword")
	public String modAccountPassword(ModifyPasswordForm form) {
		JSONObject result = new JSONObject();
		try {
			if (null == form) {
				form = new ModifyPasswordForm();
			}
			form.setId(super.getCurrentAccount().getId());
			accountService.modAccountPassword(form);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
			super.getCurrentAccount().setPassword(form.getNewPassword());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return jsonText;
	}

    @ResponseBody
	@RequestMapping("resetAccountPassword")
	public String resetAccountPassword(ModifyPasswordForm form,String accountId) {
		JSONObject result = new JSONObject();
		try {
			if (null == form) {
                form = new ModifyPasswordForm();
			}
            form.setId(super.getCurrentAccount().getId());
			accountService.resetAccountPassword(accountId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "重置成功。");
			super.getCurrentAccount().setPassword(form.getNewPassword());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "重置失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return jsonText;
	}
	
}
