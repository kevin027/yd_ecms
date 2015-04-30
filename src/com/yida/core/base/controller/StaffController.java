package com.yida.core.base.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.ListStaffForm;
import com.yida.core.base.vo.SaveStaffForm;
import com.tools.sys.PageInfo;
import com.yida.core.common.easyui.JsonListResultForEasyUI;
import com.yida.core.interceptors.Permission;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {
	
	@RequestMapping("main")
    @Permission(code = "MANAGE_STAFF")
	public String main() {
		return "core/staff/jsp/main";
	}

    @ResponseBody
	@JsonListResultForEasyUI("tree")
	@RequestMapping("listStaff")
	public String listStaff(PageInfo pageInfo,ListStaffForm query) {
		try {
			if (null == query) {
				query = new ListStaffForm();
			}
			query.setAuditOrgId(super.getCurrentAuditOrgId());
			List<Staff> list = this.staffService.listStaff(query, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "sex", "email", "phone", "roleNames", "orgNames", "createDate", "isOrg");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
			System.out.println(jsonText);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jsonText;
	}

    @ResponseBody
	@RequestMapping("listStaffForSelect")
	public String listStaffForSelect(PageInfo pageInfo,ListStaffForm query) {
		try {
			List<Staff> list = this.staffService.listStaff(query, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "sex", "email", "phone");
			jsonText = "{\"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
			System.out.println(jsonText);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jsonText;
	}
	
	@RequestMapping("addStaff")
	public String addStaff() {
		if (StringUtils.isMeaningFul(super.getCurrentAuditOrgId())) {
			
		}
		return "core/staff/jsp/addStaff";
	}

    @ResponseBody
	@RequestMapping("saveStaff")
	public String saveStaff(SaveStaffForm form) {
		JSONObject result = new JSONObject();
		try {
			staffTableModifyLock.writeLock().lock();
			Staff staff = this.staffService.saveStaff(form);
			result.put(SysConstant.AJAX_SUCCESS, "保存成功。");
			result.put("saveId", staff.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "保存失败：" + e.getMessage());
		} finally {
			staffTableModifyLock.writeLock().unlock();
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("modStaff")
	public String modStaff(HttpServletRequest request,String staffId) {
		try {
			Staff staff = this.staffService.getStaffById(staffId);
			request.setAttribute("form",new SaveStaffForm(staff));
			if (null == staff) {
				throw new IllegalStateException("没有找到的人员信息");
			}
		} catch (Exception e) {
			return "error";
		}
		return "core/staff/jsp/modStaff";
	}

    @ResponseBody
	@RequestMapping("updateStaff")
	public String updateStaff(SaveStaffForm form) {
		JSONObject result = new JSONObject();
		try {
			staffTableModifyLock.writeLock().lock();
			Staff staff = this.staffService.updateStaff(form);
			result.put(SysConstant.AJAX_SUCCESS, "更新成功。");
			result.put("staffId", staff.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "更新失败：" + e.getMessage());
		} finally {
			staffTableModifyLock.writeLock().unlock();
		}
		jsonText = result.toString();
		return jsonText;
	}

    @ResponseBody
	@RequestMapping("deleteStaff")
	public String deleteStaff(String staffId) {
		JSONObject result = new JSONObject();
		try {
			this.staffService.deleteStaffById(staffId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return jsonText;
	}

    @ResponseBody
	@RequestMapping("deleteStaffs")
	public String deleteStaffs(List<String> staffIds) {
		JSONObject result = new JSONObject();
		try {
			this.staffService.deleteStaffByIds(staffIds);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return jsonText;
	}
	
}
