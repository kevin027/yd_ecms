package com.yida.log.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.log.entity.Log;
import com.yida.log.vo.ListLogForm;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	
	private ListLogForm query;
	
	@RequestMapping("main")
	public String main() {
		return "log/jsp/main";
	}
	
	@RequestMapping("listLog")
	public String listLog() {
		try {
			List<Log> list = this.logService.listLog(query, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "operator", "opDate", "opType", "module", "description", "ip");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysConstant.JSON_RESULT_PAGE;
	}

}
