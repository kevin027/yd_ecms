package com.yida.basedata.checkitems.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.checkitems.entity.CheckItem;
import com.yida.basedata.checkitems.vo.ListCheckItemForm;
import com.yida.core.base.controller.BaseController;
import com.yida.core.common.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/checkItem")
public class CheckItemController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "basedata/checkItem/jsp/main";
	}
	
	@RequestMapping("listCheckItem")
	public String listCheckItem(ListCheckItemForm queryCheckItem,PageInfo pageInfo) {
		try {
			List<CheckItem> list = this.checkItemService.listCheckItem(queryCheckItem, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
	}
	
	@RequestMapping("addCheckItem") 
	public String addCheckItem() {
		return "basedata/checkItem/jsp/addCheckItem";
	}
	
	@RequestMapping("saveCheckItem")
	public String saveCheckItem(CheckItem checkItem) {
		JSONObject result = new JSONObject();
		try {
			CheckItem saveEntity = this.checkItemService.saveCheckItem(checkItem);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("modCheckItem") 
	public String modCheckItem(String id) {
		CheckItem checkItem = checkItemService.getCheckItemById(id);
		return "basedata/checkItem/jsp/modCheckItem";
	}
	
	@RequestMapping("updateCheckItem")
	public String updateCheckItem(CheckItem checkItem) {
		JSONObject result = new JSONObject();
		try {
			this.checkItemService.updateCheckItem(checkItem);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("delCheckItem")
	public String delCheckItem(String checkItemId) {
		JSONObject result = new JSONObject();
		try {
			this.checkItemService.delCheckItemById(checkItemId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}

}
