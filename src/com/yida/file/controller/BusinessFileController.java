package com.yida.file.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.file.entity.BusinessFile;
import com.yida.file.entity.FileInfo;

@Controller
@RequestMapping("/file")
public class BusinessFileController extends BaseController {
	
	//business/basedata/businessFile/jsp/main.jsp
	
	@RequestMapping("listBusinessFile")
	public String listBusinessFile() {
		try {
			List<BusinessFile> list = this.businessFileService.listBusinessFile(query, pageInfo);
			List<String> excludePropertys = Arrays.asList("refModule", "upload", "uploadFileName", "uploadContentType", "receiveStaff", "keeper");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayExcludeProperty(list, excludePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addBusinessFile") 
	public String addBusinessFile() {
		if (null == businessFile) {
			businessFile = new BusinessFile();
		}
		businessFile.setReceiveStaff(super.getCurrentStaff());
		businessFile.setKeeper(super.getCurrentStaff());
		return "business/file/businessFile/jsp/addBusinessFile";
	}
	
	@RequestMapping("saveBusinessFile")
	public String saveBusinessFile() {
		JSONObject result = new JSONObject();
		try {
			BusinessFile saveEntity = this.businessFileService.saveBusinessFile(businessFile);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modBusinessFile") 
	public String modBusinessFile(HttpServletRequest request) {
		businessFile = businessFileService.getBusinessFileById(businessFile.getId());
		List<FileInfo> fileInfos = this.fileService.listFile(businessFile.getRefModule(), businessFile.getId(), null);
		request.setAttribute("fileInfos", fileInfos);
		return "business/file/businessFile/jsp/modBusinessFile";
	}
	
	@RequestMapping("updateBusinessFile")
	public String updateBusinessFile() {
		JSONObject result = new JSONObject();
		try {
			this.businessFileService.updateBusinessFile(businessFile);
			result.put(SysConstant.AJAX_REQ_STATUS, SysConstant.AJAX_SUCCESS);
			result.put(SysConstant.AJAX_MSG, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, SysConstant.AJAX_ERROR);
			result.put(SysConstant.AJAX_MSG, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("delBusinessFile")
	public String delBusinessFile() {
		JSONObject result = new JSONObject();
		try {
			this.businessFileService.delBusinessFileById(businessFileId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除成功，" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}

}
