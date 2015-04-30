package com.yida.file.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.tools.sys.PageInfo;
import com.yida.file.entity.BusinessFile;
import com.yida.file.entity.FileInfo;
import com.yida.file.vo.ListBusinessFileForm;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/file")
public class BusinessFileController extends BaseController {
	
	//business/basedata/businessFile/jsp/main.jsp
	
	@RequestMapping("listBusinessFile")
	public String listBusinessFile(ListBusinessFileForm query,PageInfo pageInfo) {
		try {
			List<BusinessFile> list = this.businessFileService.listBusinessFile(query, pageInfo);
			List<String> excludePropertys = Arrays.asList("refModule", "upload", "uploadFileName", "uploadContentType", "receiveStaff", "keeper");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayExcludeProperty(list, excludePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
	}
	
	@RequestMapping("addBusinessFile") 
	public String addBusinessFile() {
        BusinessFile businessFile = new BusinessFile();
		businessFile.setReceiveStaff(super.getCurrentStaff());
		businessFile.setKeeper(super.getCurrentStaff());
		return "file/businessFile/jsp/addBusinessFile";
	}
	
	@RequestMapping("saveBusinessFile")
	public String saveBusinessFile(BusinessFile businessFile) {
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
		return jsonText;
	}
	
	@RequestMapping("modBusinessFile") 
	public String modBusinessFile(HttpServletRequest request,String id) {
		BusinessFile businessFile = businessFileService.getBusinessFileById(id);
		List<FileInfo> fileInfos = this.fileService.listFile(businessFile.getRefModule(), businessFile.getId(), null);
		request.setAttribute("fileInfos", fileInfos);
		return "file/businessFile/jsp/modBusinessFile";
	}
	
	@RequestMapping("updateBusinessFile")
	public String updateBusinessFile(BusinessFile businessFile) {
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
		return jsonText;
	}
	
	@RequestMapping("delBusinessFile")
	public String delBusinessFile(String id) {
		JSONObject result = new JSONObject();
		try {
			this.businessFileService.delBusinessFileById(id);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除成功，" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}

}
