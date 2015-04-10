package com.yida.core.base.controller;

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
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Role;
import com.yida.core.common.ztree.JsonListResultForZtree;
import com.yida.core.common.ztree.ZtreeData;
import com.yida.core.common.ztree.ZtreeHelper;

@Controller
@RequestMapping("/fun")
public class FunctionController extends BaseController {

	@RequestMapping("main")
	public String main(HttpServletRequest request) {
		try {
			List<Function> funs = this.functionService.getFunctionTree();
			List<ZtreeData> list = ZtreeHelper.toZtreeData("0", funs, null);
			request.setAttribute("ztreeNodes", StringUtils.toJsonArray(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "core/function/jsp/main";
	}
	
	/**
	 * 功能树，用于角色授权
	 */
	@JsonListResultForZtree
	@RequestMapping("listFunctionForSelect")
	public String listFunctionForSelect() {
		try {
			Set<String> checkFunctionIdSet = new HashSet<String>();
			if (null != selRoleId) {
				Role selRole = roleService.getRoleById(selRoleId);
				if (null != selRole && null != selRole.getFunctions()) {
					List<Function> selFunctions = selRole.getFunctions();
					for (int i = 0; i < selFunctions.size(); i++) {
						checkFunctionIdSet.add(selFunctions.get(i).getId());
					}
				}
			}
			List<Function> rl = functionService.listToFunctionTree(super.getCurrentAccount().getFunctions(), null);
			jsonText = StringUtils.toJsonArray(ZtreeHelper.toZtreeData(null, rl, checkFunctionIdSet));
		} catch (Exception e) {
			logger.error("listRoleForSelect获取角色信息失败", e);
			jsonText = "[]";
		} 
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addFunction")
	public String addFunction(HttpServletRequest request) {
		if (null != function && null != function.getParent() && StringUtils.isMeaningFul(function.getParent().getId())) {
			Function parent = this.functionService.getById(function.getParent().getId());
			function.setParent(parent);
		}
		request.setAttribute("functionTypeList", Function.Type.values());
		return "core/function/jsp/addFunction";
	}
	
	@RequestMapping("saveFunction")
	public String saveFunction() {
		JSONObject result = new JSONObject();
		try {
			Function fs = this.functionService.saveFunction(function);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", fs.getId());
			
			// 调整当前登录人员的权限
			try {
				Account loginAccount = super.getCurrentAccount();
				loginAccount.setFunctions(this.functionService.findAccountFunctions(loginAccount));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modFunction")
	public String modFunction(HttpServletRequest request) {
		function = this.functionService.getById(function.getId());
		/*
		if (null != function && null != function.getParent() && null != function.getParent().getId()) {
			Function parent = this.functionService.getById(function.getParent().getId());
			function.setParent(parent);
		}*/
		request.setAttribute("functionTypeList", Function.Type.values());
		return "core/function/jsp/modFunction";
	}
	
	@RequestMapping("updateFunction")
	public String updateFunction() {
		JSONObject result = new JSONObject();
		try {
			this.functionService.updateFunction(function);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
			e.printStackTrace();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("deleteFunction")
	public String deleteFunction() {
		JSONObject r = new JSONObject();
		try {
			// 根据ID删除功能
			this.functionService.delFunctionById(function.getId());
			
			// 调整当前登录人员的权限
			try {
				Account loginAccount = super.getCurrentAccount();
				loginAccount.setFunctions(this.functionService.findAccountFunctions(loginAccount));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			r.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			e.printStackTrace();
			r.put(SysConstant.AJAX_ERROR, "删除失败，" + e.getMessage());
		}
		jsonText = r.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
}
