package com.yida.core.base.controller;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Department;
import com.yida.core.base.vo.ListDepartmentForm;
import com.yida.core.common.ztree.JsonListResultForZtree;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @ResponseBody
	@JsonListResultForZtree
	@RequestMapping("listDepartmentForSelect")
	public String listDepartmentForSelect(ListDepartmentForm query,String selDepartmentIds) {
		try {
			Set<String> checkDepartmentIdSet = new HashSet<String>();
			if (null != selDepartmentIds) {
				for (String selDepartmentId : selDepartmentIds.split(",")) {
					checkDepartmentIdSet.add(selDepartmentId);
				}
			}
			
			List<Department> list = departmentService.listDepartment(query, null);
			List<String> includePropertys = Arrays.asList("id", "name");
			jsonText = "{\"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
		}
		return jsonText;
	}
	
	@RequestMapping("add")
	public void add(Department department) {
		try {
			this.departmentService.saveDepartment(department);
		} catch (Exception e) {
			
		}
	}
	
	@RequestMapping("delete")
	public void delete(String departmentId) {
		try {
			this.departmentService.deleteDepartmentByDepartmentId(departmentId);
		} catch (Exception e) {
			
		}
	}

}
