package com.yida.core.base.controller;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.vo.ListStaffForm;
import com.yida.core.base.vo.SaveStaffForm;
import com.yida.core.common.easyui.JsonListResultForEasyUI;

@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "core/staff/jsp/main";
	}
	
	@JsonListResultForEasyUI("tree")
	@RequestMapping("listStaff")
	public String listStaff() {
		try {
			if (null == queryStaff) {
				queryStaff = new ListStaffForm();
			}
			queryStaff.setAuditOrgId(super.getCurrentAuditOrgId());
			List<Staff> list = this.staffService.listStaff(queryStaff, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "sex", "email", "phone", "roleNames", "orgNames", "createDate", "isOrg");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
			System.out.println(jsonText);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("listStaffForSelect")
	public String listStaffForSelect() {
		try {
			List<Staff> list = this.staffService.listStaff(queryStaff, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "sex", "email", "phone");
			jsonText = "{\"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
			System.out.println(jsonText);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addStaff")
	public String addStaff() {
		if (StringUtils.isMeaningFul(super.getCurrentAuditOrgId())) {
			
		}
		return "core/staff/jsp/addStaff";
	}
	
	@RequestMapping("saveStaff")
	public String saveStaff() {
		JSONObject result = new JSONObject();
		try {
			staffTableModifyLock.writeLock().lock();
			staff = this.staffService.saveStaff(saveStaffForm);
			result.put(SysConstant.AJAX_SUCCESS, "保存成功。");
			result.put("saveId", staff.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "保存失败：" + e.getMessage());
		} finally {
			staffTableModifyLock.writeLock().unlock();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modStaff")
	public String modStaff() {
		try {
			staff = this.staffService.getStaffById(staffId);
			this.saveStaffForm = new SaveStaffForm(staff);
			if (null == staff) {
				throw new IllegalStateException("没有找到的人员信息");
			}
		} catch (Exception e) {
			return "error";
		}
		return "core/staff/jsp/modStaff";
	}
	
	@RequestMapping("updateStaff")
	public String updateStaff() {
		JSONObject result = new JSONObject();
		try {
			staffTableModifyLock.writeLock().lock();
			staff = this.staffService.updateStaff(saveStaffForm);
			result.put(SysConstant.AJAX_SUCCESS, "更新成功。");
			result.put("staffId", staff.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "更新失败：" + e.getMessage());
		} finally {
			staffTableModifyLock.writeLock().unlock();
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("deleteStaff")
	public String deleteStaff() {
		JSONObject result = new JSONObject();
		try {
			this.staffService.deleteStaffById(staffId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	private List<String> staffIds;
	@RequestMapping("deleteStaffs")
	public String deleteStaffs() {
		JSONObject result = new JSONObject();
		try {
			this.staffService.deleteStaffByIds(staffIds);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		} 
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
}
