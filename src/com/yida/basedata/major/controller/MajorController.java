package com.yida.basedata.major.controller;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.major.entity.Major;
import com.yida.core.base.controller.BaseController;

@Controller
@RequestMapping("/major")
public class MajorController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "business/basedata/major/jsp/main";
	}
	
	@RequestMapping("listMajor")
	public String listMajor() {
		try {
			List<Major> list = this.majorService.listMajor(queryMajor, pageInfo);
			List<String> excludeProperties = Arrays.asList("experts","parent");
			String property = StringUtils.toJsonArrayExcludeProperty(list, excludeProperties);
			if(pageInfo == null){
				jsonText = property;
				
			}else{
				jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + property + "}";
			}
			System.out.println(jsonText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addMajor") 
	public String addMajor() {
		return "business/basedata/major/jsp/addMajor";
	}
	
	@RequestMapping("saveMajor")
	public String saveMajor() {
		JSONObject result = new JSONObject();
		try {
			if(major.getParent()!=null && "".equals(major.getParent().getId())){
				major.setParent(null);
			}
			Major saveEntity = this.majorService.saveMajor(major);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modMajor") 
	public String modMajor() {
		major = majorService.getMajorById(major.getId());
		return "business/basedata/major/jsp/modMajor";
	}
	
	@RequestMapping("updateMajor")
	public String updateMajor() {
		JSONObject result = new JSONObject();
		try {
			if(major.getParent()!=null && "".equals(major.getParent().getId())){
				major.setParent(null);
			}
			this.majorService.updateMajor(major);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("delMajor")
	public String delMajor() {
		JSONObject result = new JSONObject();
		try {
			this.majorService.delMajorTree(majorId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}

}
