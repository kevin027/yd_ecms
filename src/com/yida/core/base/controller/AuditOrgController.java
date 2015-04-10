package com.yida.core.base.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.common.ztree.JsonListResultForZtree;
import com.yida.core.common.ztree.ZtreeHelper;

@Controller
@RequestMapping("/auditOrg")
public class AuditOrgController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "core/auditOrg/jsp/main";
	}
	
	@RequestMapping("listAuditOrg")
	public String listAuditOrg() {
		List<AuditOrg> auditOrgs = auditOrgService.listAuditOrg(this.queryOrg, this.pageInfo);
		List<String> excludePropertys = Arrays.asList( "departments" );
		jsonText = StringUtils.toJsonArrayExcludeProperty(auditOrgs, excludePropertys);
		return "listAuditOrg";
	}
	
	private String selAuditOrgIds;
	
	@JsonListResultForZtree
	@RequestMapping("listAuditOrgForSelect")
	public String listAuditOrgForSelect() {
		try {
			Set<String> checkAuditOrgIdSet = new HashSet<String>();
			if (null != selAuditOrgIds) {
				for (String selAuditOrgId : selAuditOrgIds.split(",")) {
					checkAuditOrgIdSet.add(selAuditOrgId);
				}
			}
			List<AuditOrg> auditOrgs = auditOrgService.listAuditOrg(this.queryOrg, this.pageInfo);
			jsonText = StringUtils.toJsonArray(ZtreeHelper.toZtreeData("0", auditOrgs, checkAuditOrgIdSet));
		} catch (Exception e) {
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
			jsonText = "[]";
		}
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("addAuditOrgPage")
	public String addAuditOrgPage() {
		return "addAuditOrgPage";
	}
	
	@RequestMapping("saveAuditOrg")
	public String saveAuditOrg() {
		return null;
	}
	
}
