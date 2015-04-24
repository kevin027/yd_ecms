package com.yida.basedata.audittype.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.audittype.entity.AuditType;
import com.yida.basedata.audittype.vo.ListAuditTypeForm;
import com.yida.core.base.controller.BaseController;
import com.yida.core.common.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/auditType")
public class AuditTypeController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "basedata/auditType/jsp/main";
	}
	
	@RequestMapping("listAuditType")
	public String listAuditType(ListAuditTypeForm queryAuditType,PageInfo pageInfo) {
		try {
			List<AuditType> list = this.auditTypeService.listAuditType(queryAuditType, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
	}
	
	@RequestMapping("addAuditType") 
	public String addAuditType() {
		return "basedata/auditType/jsp/addAuditType";
	}
	
	@RequestMapping("saveAuditType")
	public String saveAuditType(AuditType auditType) {
		JSONObject result = new JSONObject();
		try {
			AuditType saveEntity = this.auditTypeService.saveAuditType(auditType);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("modAuditType") 
	public String modAuditType(String id) {
		AuditType auditType = auditTypeService.getAuditTypeById(id);
		return "basedata/auditType/jsp/modAuditType";
	}
	
	@RequestMapping("updateAuditType")
	public String updateAuditType(AuditType auditType) {
		JSONObject result = new JSONObject();
		try {
			this.auditTypeService.updateAuditType(auditType);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("delAuditType")
	public String delAuditType(String auditTypeId) {
		JSONObject result = new JSONObject();
		try {
			this.auditTypeService.delAuditTypeById(auditTypeId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}

}
