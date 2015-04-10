package com.yida.basedata.checkitems.controller;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.checkitems.entity.CheckItem;
import com.yida.core.base.controller.BaseController;

@Controller
@RequestMapping("/checkItem")
public class CheckItemController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "business/basedata/checkItem/jsp/main";
	}
	
	@RequestMapping("listCheckItem")
	public String listCheckItem() {
		try {
			List<CheckItem> list = this.checkItemService.listCheckItem(queryCheckItem, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addCheckItem") 
	public String addCheckItem() {
		return "business/basedata/checkItem/jsp/addCheckItem";
	}
	
	@RequestMapping("saveCheckItem")
	public String saveCheckItem() {
		JSONObject result = new JSONObject();
		try {
			CheckItem saveEntity = this.checkItemService.saveCheckItem(checkItem);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("modCheckItem") 
	public String modCheckItem() {
		checkItem = checkItemService.getCheckItemById(checkItem.getId());
		return "business/basedata/checkItem/jsp/modCheckItem";
	}
	
	@RequestMapping("updateCheckItem")
	public String updateCheckItem() {
		JSONObject result = new JSONObject();
		try {
			this.checkItemService.updateCheckItem(checkItem);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("delCheckItem")
	public String delCheckItem() {
		JSONObject result = new JSONObject();
		try {
			this.checkItemService.delCheckItemById(checkItemId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}

}
