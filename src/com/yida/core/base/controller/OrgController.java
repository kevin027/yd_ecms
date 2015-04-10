package com.yida.core.base.controller;

import java.util.ArrayList;
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
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.entity.Department;
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Org;
import com.yida.core.base.entity.Role;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.SaveAuditOrgForm;
import com.yida.core.base.vo.SaveDepartmentForm;
import com.yida.core.common.ztree.JsonListResultForZtree;
import com.yida.core.common.ztree.ZtreeData;
import com.yida.core.common.ztree.ZtreeHelper;

@Controller
@RequestMapping("/org")
public class OrgController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "core/org/jsp/main";
	}
	
	/**
	 * 组织机构树，一次性加载，不包含人员节点，任何节点不显示checkbox
	 * 用于和组织结构选择的有关页面。
	 * @return
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeForOrgSelectWithoutCheckBox")
	public String listOrgTreeForOrgSelectWithoutCheckBox() {
		try {
			List<Org> list = this.orgService.getAuditOrgs(
					Account.Type.ADMIN == super.getCurrentAccount().getType() ? null : super.getCurrentAuditOrgId(),true);
			// List<Org> list = this.orgService.getAuditOrgs(super.getCurrentAuditOrgId());
			List<String> includeProps = Arrays.asList("id", "name", "pid", "isOrg", "nocheck", "open", "iconSkin", "fullName", "children");
			jsonText = StringUtils.toJsonArrayIncludeProperty(ZtreeHelper.orgTreeWithoutCheckbox(null, list, false), includeProps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 组织机构树，一次性加载，包含人员节点，任何节点不显示checkbox
	 * 用于组织机构维护页面
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeWithoutCheckBox")
	public String listOrgTreeWithoutCheckBox() {
		try {
			List<Org> list = this.orgService.getAuditOrgs(
					Account.Type.ADMIN == super.getCurrentAccount().getType() ? null : super.getCurrentAuditOrgId(),false);
			List<String> includeProps = Arrays.asList("id", "name", "pid", "isOrg", "nocheck", "open", "iconSkin", "fullName", "children");
			jsonText = StringUtils.toJsonArrayIncludeProperty(ZtreeHelper.orgTreeWithoutCheckbox(null, list, true), includeProps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 组织机构树，一次性加载，不包含人员节点，所有节点不显示checkbox
	 * 用于新建人员页面选择所属部门
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeForOrgSelect2")
	public String listOrgTreeForOrgSelect2() {
		try {
			Set<String> checkOrgNodeIds = new HashSet<String>();
			if (null != selOrgIds) {
				for (String selOrgId : selOrgIds.split(",")) {
					checkOrgNodeIds.add(selOrgId);
				}
			}
			List<Org> list = this.orgService.getAuditOrgs(super.getCurrentAuditOrgId(),false);
			List<String> includeProps = Arrays.asList("id", "name", "pid", "isOrg", "open", "iconSkin", "children", "checked");
			jsonText = StringUtils.toJsonArrayIncludeProperty(ZtreeHelper.orgTreeForOrgSelect(null, list, checkOrgNodeIds), includeProps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 组织机构树，一次性加载，不包含人员节点，所有节点显示checkbox
	 * 用于新建人员页面选择所属部门
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeForOrgSelect")
	public String listOrgTreeForOrgSelect() {
		try {
			Set<String> checkOrgNodeIds = new HashSet<String>();
			if (null != selOrgIds) {
				for (String selOrgId : selOrgIds.split(",")) {
					checkOrgNodeIds.add(selOrgId);
				}
			}
			List<Org> list = this.orgService.getAuditOrgs(super.getCurrentAuditOrgId(),false);
			List<String> includeProps = Arrays.asList("id", "name", "pid", "open", "iconSkin", "fullName", "children");
			jsonText = StringUtils.toJsonArrayIncludeProperty(ZtreeHelper.orgTreeWithoutCheckbox(null, list, false), includeProps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 组织机构树，一次性加载，包含人员节点。用于选择人员，只在人员节点显示checkbox
	 * @return
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeForStaffSelect")
	public String listOrgTreeForStaffSelect() {
		try {
			Set<String> checkStaffIdSet = new HashSet<String>();
			if (null != selRoleId) {
				Role selRole = roleService.getRoleById(selRoleId);
				if (null != selRole && null != selRole.getFunctions()) {
					Set<Account> selStaffs = selRole.getAccounts();
					for (Account selStaff : selStaffs) {
						checkStaffIdSet.add(selStaff.getId());
					}
				}
			}
			List<Org> list = this.orgService.getAuditOrgs(super.getCurrentAuditOrgId(),true);
			List<String> includeProps = Arrays.asList("id", "name", "pid", "checked", "nocheck", "isParent", "open", "iconSkin", "children", "fullName");
			jsonText = StringUtils.toJsonArrayIncludeProperty(ZtreeHelper.orgTreeForStaffSelect(null, list, checkStaffIdSet), includeProps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 组织机构树，一次性加载，只包含机构和账号节点节点。用于选择账号，只在账号节点显示checkbox
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeForAccountSelect")
	public String listOrgTreeForAccountSelect() {
		try {
			Set<String> checkAccountIds = new HashSet<String>();
			if (null != selRoleId) {
				Role selRole = roleService.getRoleById(selRoleId);
				if (null != selRole && null != selRole.getAccounts()) {
					Set<Account> selAccounts = selRole.getAccounts();
					for (Account selAccount : selAccounts) {
						checkAccountIds.add(selAccount.getId());
					}
				}
			}
			List<Org> list = this.orgService.getAuditOrgs(super.getCurrentAuditOrgId(),true);
			List<String> includeProps = Arrays.asList("id", "name", "pid", "checked", "nocheck", "isParent", "open", "iconSkin", "fullName", "children");
			jsonText = StringUtils.toJsonArrayIncludeProperty(ZtreeHelper.orgTreeForAccountSelect(null, list, checkAccountIds), includeProps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 部门机构树节点异步加载（包含人员）
	 * @return
	 */
	@JsonListResultForZtree
	@RequestMapping("listOrgTreeNode")
	public String listOrgTreeNode(HttpServletRequest request) {
		try {
			String orgId = request.getParameter("id");
			List<ZtreeData> list = new ArrayList<ZtreeData>();
			
			// 加入部门节点
			List<Org> orgs = orgService.findOrgByParentId(orgId, false);
			list.addAll(ZtreeHelper.orgToZtreeData(orgId, orgs));
			
			// 加入人员节点
			List<Staff> staffs = staffService.findStaffByOrgId(orgId);
			list.addAll(ZtreeHelper.staffToZtreeData(orgId, staffs));
			
			// 转为json格式
		    jsonText = StringUtils.toJsonArray(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("orgTreeNodeInfo")
	public String orgTreeNodeInfo() {
		try {
			Org org = this.orgService.getOrgById(orgId, true);
			List<Function> funs = super.getCurrentAccount().getFunctions();
			if (org instanceof AuditOrg) {
				this.auditOrg = (AuditOrg) org;
				this.saveAuditOrgForm = new SaveAuditOrgForm(auditOrg);
				if (!funs.contains(new Function("900402"))) {
					return "core/org/jsp/viewAuditOrg";
				} else {
					return "core/org/jsp/modAuditOrg";
				}
			} else if (org instanceof Department) {
				this.department = (Department) org;
				this.saveDepartmentForm = new SaveDepartmentForm(department);
				if (!funs.contains(new Function("900405"))) {
					return "core/org/jsp/viewDepartment";
				} else {
					return "core/org/jsp/modDepartment";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 新增机构信息
	 */
	@RequestMapping("addAuditOrg")
	public String addAuditOrg() {
		return "core/org/jsp/addAuditOrg";
	}
	
	/**
	 * 保存机构信息
	 */
	@RequestMapping("saveAuditOrg")
	public String saveAuditOrg() {
		JSONObject result = new JSONObject();
		try {
			AuditOrg auditOrg = this.auditOrgService.saveAuditOrg(saveAuditOrgForm);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", auditOrg.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 新增部门信息
	 */
	@RequestMapping("addDepartment")
	public String addDepartment() {
		if (StringUtils.isMeaningFul(orgId)) {
			this.saveDepartmentForm = new SaveDepartmentForm();
			this.saveDepartmentForm.setParentId(orgId);
		}
		return "core/org/jsp/addDepartment";
	}
	
	/**
	 * 保存部门信息
	 */
	@RequestMapping("saveDepartment")
	public String saveDepartment() {
		JSONObject result = new JSONObject();
		try {
			Department department = this.departmentService.saveDepartment(saveDepartmentForm);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", department.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 删除机构信息
	 */
	@RequestMapping("delAuditOrg")
	public String delAuditOrg() {
		JSONObject result = new JSONObject();
		try {
			this.auditOrgService.deleteAuditOrgByAuditOrgId(orgId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch(Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 删除部门信息
	 */
	@RequestMapping("delDepartment")
	public String delDepartment() {
		JSONObject result = new JSONObject();
		try {
			this.departmentService.deleteDepartmentByDepartmentId(orgId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch(Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 更新机构信息
	 * @return
	 */
	@RequestMapping("updateAuditOrg")
	public String updateAuditOrg() {
		JSONObject result = new JSONObject();
		try {
			this.auditOrgService.updateAuditOrg(saveAuditOrgForm);
			result.put(SysConstant.AJAX_SUCCESS, "更新成功。");
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "更新失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 更新部门信息
	 * @return
	 */
	@RequestMapping("updateDepartment")
	public String updateDepartment() {
		JSONObject result = new JSONObject();
		try {
			this.departmentService.updateDepartment(saveDepartmentForm);
			result.put(SysConstant.AJAX_SUCCESS, "更新成功。");
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "更新失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 判断当前登录人员是否有权限添加机构
	 */
	@RequestMapping("canAddAuditOrg")
	public String canAddAuditOrg() {
		JSONObject result = new JSONObject();
		Account loginAccount = super.getCurrentAccount();
		if (null != loginAccount
				&& Account.Type.ADMIN == loginAccount.getType()) {
			result.put(SysConstant.AJAX_SUCCESS, Boolean.TRUE);
		} else {
			result.put(SysConstant.AJAX_SUCCESS, Boolean.FALSE);
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 判断当前登录人员是否有权限删除机构
	 */
	@RequestMapping("canDelAuditOrg")
	public String canDelAuditOrg() {
		JSONObject result = new JSONObject();
		Account loginAccount = super.getCurrentAccount();
		if (null != loginAccount
				&& Account.Type.ADMIN == loginAccount.getType()) {
			result.put(SysConstant.AJAX_SUCCESS, Boolean.TRUE);
		} else {
			result.put(SysConstant.AJAX_SUCCESS, Boolean.FALSE);
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}

}
