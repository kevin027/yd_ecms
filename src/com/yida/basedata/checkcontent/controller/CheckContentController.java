package com.yida.basedata.checkcontent.controller;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.checkcontent.entity.CheckContent;
import com.yida.core.base.controller.BaseController;

@Controller
@RequestMapping("/checkContent")
public class CheckContentController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "business/basedata/checkContent/jsp/main";
	}
	
	@RequestMapping("listCheckContent")
	public String listCheckContent() {
		try {
			List<CheckContent> list = this.checkContentService.listCheckContent(queryCheckContent, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark", "sort");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addCheckContent") 
	public String addCheckContent() {
		return "business/basedata/checkContent/jsp/addCheckContent";
	}
	
	@RequestMapping("saveCheckContent")
	public String saveCheckContent() {
		JSONObject result = new JSONObject();
		try {
			CheckContent saveEntity = this.checkContentService.saveCheckContent(checkContent);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modCheckContent") 
	public String modCheckContent() {
		checkContent = checkContentService.getCheckContentById(checkContent.getId());
		return "business/basedata/checkContent/jsp/modCheckContent";
	}
	
	@RequestMapping("updateCheckContent")
	public String updateCheckContent() {
		JSONObject result = new JSONObject();
		try {
			this.checkContentService.updateCheckContent(checkContent);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("delCheckContent")
	public String delCheckContent() {
		JSONObject result = new JSONObject();
		try {
			this.checkContentService.delCheckContentById(checkContentId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
}
