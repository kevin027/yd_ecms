package com.yida.core.base.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Role;
import com.yida.core.base.vo.ListRoleForm;
import com.yida.core.base.vo.SaveRoleForm;
import com.yida.core.common.easyui.EasyUIHelper;
import com.yida.core.common.easyui.JsonListResultForEasyUI;
import com.yida.core.interceptors.Permission;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	/**
	 * 角色管理主页面
	 */
	@RequestMapping("main")
	@Permission(code="MANAGE_ROLE")
	public String main() {
		return "core/role/jsp/main";
	}
	
	/**
	 * 角色管理主页面-角色列表（查询操作）
	 */
	@JsonListResultForEasyUI("tree")
	@RequestMapping("listRole")
	public String listRole() {
		try {
			if (null == this.queryRole) {
				this.queryRole = new ListRoleForm();
			}
			this.queryRole.setAuditOrgId(super.getCurrentAuditOrgId());
			List<Role> list = roleService.listRole(this.queryRole, this.pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "invalid", "accountNames", "remark");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
			jsonText = "[]";
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 角色管理主页面-增加操作
	 * @return
	 */
	@RequestMapping("addRole")
	public String addRole(HttpServletRequest request) {
		if (null != super.getCurrentAccount() 
				&& Account.Type.ADMIN == super.getCurrentAccount().getType()) {
			request.setAttribute("auditOrgs", orgService.getAuditOrgs(null,true));
		}
		return "core/role/jsp/addRole";
	}
	
	/**
	 * 角色管理主页面-修改操作
	 * @return
	 */
	@RequestMapping("modRole")
	public String modRole(HttpServletRequest request) {
		if (null != super.getCurrentAccount() 
				&& Account.Type.ADMIN == super.getCurrentAccount().getType()) {
			request.setAttribute("auditOrgs", orgService.getAuditOrgs(null,true));
		}
		Role r = roleService.getRoleById(roleId);
		saveRoleForm = new SaveRoleForm(r);
		return "core/role/jsp/modRole";
	}
	
	/**
	 * 角色管理主页面-删除操作
	 */
	@RequestMapping("delRole")
	public String delRole() {
		JSONObject result = new JSONObject();
		try {
			this.roleService.deleteRoleByRoleId(roleId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 角色增加页面-保存操作
	 * @return
	 */
	@RequestMapping("saveRole")
	public String saveRole() {
		JSONObject result = new JSONObject();
		try {
			// 如果非管理员账号，则自动添加角色所属机构为当前账号的所属机构。
			if (null != super.getCurrentAccount()
					&& Account.Type.ADMIN != super.getCurrentAccount().getType()){
				saveRoleForm.setAuditOrgId(super.getCurrentAuditOrgId());
			}
			Role r = this.roleService.saveRole(saveRoleForm);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", r.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 角色修改页面-保存操作
	 */
	@RequestMapping("updateRole")
	public String updateRole() {
		JSONObject result = new JSONObject();
		try {
			// 如果非管理员账号，则自动添加角色所属机构为当前账号的所属机构。
			if (null != super.getCurrentAccount()
					&& Account.Type.ADMIN != super.getCurrentAccount().getType()){
				saveRoleForm.setAuditOrgId(super.getCurrentAuditOrgId());
			}
			this.roleService.updateRole(saveRoleForm);
			result.put(SysConstant.AJAX_SUCCESS, "更新成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "更新失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 角色管理主页面功能授权操作
	 */
	@RequestMapping("functionEmpower")
	public String functionEmpower() {
		JSONObject result = new JSONObject();
		try {
			this.roleService.updateFunctionsForRole(roleId, functionIds);
			result.put(SysConstant.AJAX_SUCCESS, "授权成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "授权失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 角色管理主页面账号授权操作
	 */
	@RequestMapping("accountEmpower")
	public String accountEmpower() {
		JSONObject result = new JSONObject();
		try {
			this.roleService.updateAccountsForRole(roleId, accountIds);
			result.put(SysConstant.AJAX_SUCCESS, "授权成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "授权失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 获取角色列表供jqueryRole-easyui的树形结构选择。
	 */
	@JsonListResultForEasyUI("tree")
	@RequestMapping("listRoleForSelect")
	public String listRoleForSelect() {
		try {
			ListRoleForm lrf = new ListRoleForm();
			Set<String> checkRoleIdSet = new HashSet<String>();
			if (null != selRoleIds) {
				for (String selRoleId : selRoleIds.split(",")) {
					checkRoleIdSet.add(selRoleId);
				}
			}
			List<Role> rl = roleService.listRole(lrf, null);
			jsonText = EasyUIHelper.roleTree(rl, checkRoleIdSet);
		} catch (Exception e) {
			logger.error("listRoleForSelect获取角色信息失败", e);
			jsonText = "[]";
		} 
		return SysConstant.JSON_RESULT_PAGE;
	}
	
}
