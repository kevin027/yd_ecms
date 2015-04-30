package com.yida.basedata.major.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.major.entity.Major;
import com.yida.basedata.major.vo.ListMajorForm;
import com.yida.core.base.controller.BaseController;
import com.tools.sys.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/major")
public class MajorController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "basedata/major/jsp/main";
	}
	
	@RequestMapping("listMajor")
	public String listMajor(ListMajorForm queryMajor,PageInfo pageInfo) {
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
		return jsonText;
	}
	
	@RequestMapping("addMajor") 
	public String addMajor() {
		return "basedata/major/jsp/addMajor";
	}
	
	@RequestMapping("saveMajor")
	public String saveMajor(Major major) {
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
		return jsonText;
	}
	
	@RequestMapping("modMajor") 
	public String modMajor(String id) {
		Major major = majorService.getMajorById(id);
		return "basedata/major/jsp/modMajor";
	}
	
	@RequestMapping("updateMajor")
	public String updateMajor(Major major) {
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
		return jsonText;
	}
	
	@RequestMapping("delMajor")
	public String delMajor(String majorId) {
		JSONObject result = new JSONObject();
		try {
			this.majorService.delMajorTree(majorId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}

}
