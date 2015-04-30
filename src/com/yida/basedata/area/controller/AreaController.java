package com.yida.basedata.area.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.basedata.area.entity.Area;
import com.yida.basedata.area.vo.ListAreaForm;
import com.yida.core.base.controller.BaseController;
import com.tools.sys.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "basedata/area/jsp/main";
	}
	
	@RequestMapping("listArea")
	public String listArea(ListAreaForm queryArea,PageInfo pageInfo) {
		try {
			if (null == queryArea) {
				queryArea = new ListAreaForm();
			}
			List<Area> list = this.areaService.listArea(queryArea, pageInfo);
			List<String> excludeProperties = Arrays.asList("parent");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayExcludeProperty(list, excludeProperties) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
	}
	
	@RequestMapping("addArea") 
	public String addArea() {
		return "basedata/area/jsp/addArea";
	}
	
	@RequestMapping("saveArea")
	public String saveArea(Area area) {
		JSONObject result = new JSONObject();
		try {
			if(area.getParent()!=null&&"".equals(area.getParent().getId())){
				area.setParent(null);
			}
			Area saveEntity = this.areaService.saveArea(area);
			result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
			result.put("saveId", saveEntity.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("modArea") 
	public String modArea(Area area) {
		area = areaService.getAreaById(area.getId());
		return "basedata/area/jsp/modArea";
	}
	
	@RequestMapping("updateArea")
	public String updateArea(Area area) {
		JSONObject result = new JSONObject();
		try {
			if(area.getParent()!=null&&"".equals(area.getParent().getId())){
				area.setParent(null);
			}
			this.areaService.updateArea(area);
			result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("delArea")
	public String delArea(String areaId) {
		JSONObject result = new JSONObject();
		try {
			this.areaService.delAreaById(areaId);
			result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}

}
