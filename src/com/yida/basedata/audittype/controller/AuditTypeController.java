package com.yida.basedata.audittype.controller;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.audittype.entity.AuditType;
import com.yida.core.base.controller.BaseController;

@Controller
@RequestMapping("/auditType")
public class AuditTypeController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "business/basedata/auditType/jsp/main";
	}
	
	@RequestMapping("listAuditType")
	public String listAuditType() {
		try {
			List<AuditType> list = this.auditTypeService.listAuditType(queryAuditType, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addAuditType") 
	public String addAuditType() {
		return "business/basedata/auditType/jsp/addAuditType";
	}
	
	@RequestMapping("saveAuditType")
	public String saveAuditType() {
		JSONObject result = new JSONObject();
		try {
			AuditType saveEntity = this.auditTypeService.saveAuditType(auditType);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modAuditType") 
	public String modAuditType() {
		auditType = auditTypeService.getAuditTypeById(auditType.getId());
		return "business/basedata/auditType/jsp/modAuditType";
	}
	
	@RequestMapping("updateAuditType")
	public String updateAuditType() {
		JSONObject result = new JSONObject();
		try {
			this.auditTypeService.updateAuditType(auditType);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("delAuditType")
	public String delAuditType() {
		JSONObject result = new JSONObject();
		try {
			this.auditTypeService.delAuditTypeById(auditTypeId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}

}
