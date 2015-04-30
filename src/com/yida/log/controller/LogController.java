package com.yida.log.controller;

import com.tools.sys.PageInfo;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.log.entity.Log;
import com.yida.log.vo.ListLogForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "log/jsp/main";
	}

    @ResponseBody
	@RequestMapping("listLog")
	public String listLog(ListLogForm query,PageInfo pageInfo) {
		try {
			List<Log> list = this.logService.listLog(query, pageInfo);
			List<String> includePropertys = Arrays.asList("id", "operator", "opDate", "opType", "module", "description", "ip");
			jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
	}

}
