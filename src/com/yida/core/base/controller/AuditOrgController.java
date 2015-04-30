package com.yida.core.base.controller;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.vo.ListAuditOrgForm;
import com.tools.sys.PageInfo;
import com.yida.core.common.ztree.JsonListResultForZtree;
import com.yida.core.common.ztree.ZtreeHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/auditOrg")
public class AuditOrgController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "core/auditOrg/jsp/main";
	}
	
	@RequestMapping("listAuditOrg")
	public String listAuditOrg(ListAuditOrgForm query,PageInfo pageInfo) {
		List<AuditOrg> auditOrgs = auditOrgService.listAuditOrg(query, pageInfo);
		List<String> excludePropertys = Arrays.asList( "departments" );
		jsonText = StringUtils.toJsonArrayExcludeProperty(auditOrgs, excludePropertys);
		return "listAuditOrg";
	}
	
	@ResponseBody
    @JsonListResultForZtree
	@RequestMapping("listAuditOrgForSelect")
	public String listAuditOrgForSelect(ListAuditOrgForm query,PageInfo pageInfo,String selAuditOrgIds) {
		try {
			Set<String> checkAuditOrgIdSet = new HashSet<String>();
			if (null != selAuditOrgIds) {
				for (String selAuditOrgId : selAuditOrgIds.split(",")) {
					checkAuditOrgIdSet.add(selAuditOrgId);
				}
			}
			List<AuditOrg> auditOrgs = auditOrgService.listAuditOrg(query, pageInfo);
			jsonText = StringUtils.toJsonArray(ZtreeHelper.toZtreeData("0", auditOrgs, checkAuditOrgIdSet));
		} catch (Exception e) {
			logger.error("listAuditOrgForSelect获取机构信息失败", e);
			jsonText = "[]";
		}
		return jsonText;
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
