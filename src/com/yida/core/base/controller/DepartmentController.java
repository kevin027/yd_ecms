package com.yida.core.base.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Department;
import com.yida.core.common.ztree.JsonListResultForZtree;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {
	
	@JsonListResultForZtree
	@RequestMapping("listDepartmentForSelect")
	public String listDepartmentForSelect() {
		try {
			Set<String> checkDepartmentIdSet = new HashSet<String>();
			if (null != selDepartmentIds) {
				for (String selDepartmentId : selDepartmentIds.split(",")) {
					checkDepartmentIdSet.add(selDepartmentId);
				}
			}
			
			List<Department> list = departmentService.listDepartment(this.queryDep, null);
			List<String> includePropertys = Arrays.asList("id", "name");
			jsonText = "{\"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("add")
	public void add() {
		try {
			this.departmentService.saveDepartment(department);
		} catch (Exception e) {
			
		}
	}
	
	@RequestMapping("delete")
	public void delete() {
		try {
			this.departmentService.deleteDepartmentByDepartmentId(departmentId);
		} catch (Exception e) {
			
		}
	}

}
