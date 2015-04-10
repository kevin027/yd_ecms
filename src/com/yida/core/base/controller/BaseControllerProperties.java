package com.yida.core.base.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;

import com.yida.basedata.Cache;
import com.yida.basedata.area.entity.Area;
import com.yida.basedata.area.service.AreaService;
import com.yida.basedata.area.vo.ListAreaForm;
import com.yida.basedata.audittype.entity.AuditType;
import com.yida.basedata.audittype.service.AuditTypeService;
import com.yida.basedata.audittype.vo.ListAuditTypeForm;
import com.yida.basedata.checkcontent.entity.CheckContent;
import com.yida.basedata.checkcontent.service.CheckContentService;
import com.yida.basedata.checkcontent.vo.ListCheckContentForm;
import com.yida.basedata.checkitems.entity.CheckItem;
import com.yida.basedata.checkitems.service.CheckItemService;
import com.yida.basedata.checkitems.vo.ListCheckItemForm;
import com.yida.basedata.major.entity.Major;
import com.yida.basedata.major.service.MajorService;
import com.yida.basedata.major.vo.ListMajorForm;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.entity.Department;
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.service.AccountService;
import com.yida.core.base.service.AuditOrgService;
import com.yida.core.base.service.BaseService;
import com.yida.core.base.service.DepartmentService;
import com.yida.core.base.service.FunctionService;
import com.yida.core.base.service.OrgService;
import com.yida.core.base.service.RoleService;
import com.yida.core.base.service.StaffService;
import com.yida.core.base.service.ZtreeDataTransformService;
import com.yida.core.base.vo.ListAccountForm;
import com.yida.core.base.vo.ListAuditOrgForm;
import com.yida.core.base.vo.ListDepartmentForm;
import com.yida.core.base.vo.ListRoleForm;
import com.yida.core.base.vo.ListStaffForm;
import com.yida.core.base.vo.LoginForm;
import com.yida.core.base.vo.ModifyPasswordForm;
import com.yida.core.base.vo.SaveAccountForm;
import com.yida.core.base.vo.SaveAuditOrgForm;
import com.yida.core.base.vo.SaveDepartmentForm;
import com.yida.core.base.vo.SaveRoleForm;
import com.yida.core.base.vo.SaveStaffForm;
import com.yida.core.common.PageInfo;
import com.yida.file.entity.BusinessFile;
import com.yida.file.service.BusinessFileService;
import com.yida.file.service.FileService;
import com.yida.file.vo.ListBusinessFileForm;
import com.yida.log.service.LogService;
import com.yida.mail.entity.Mail;
import com.yida.mail.service.MailService;
import com.yida.mail.vo.ListMailQuery;
import com.yida.mail.vo.MailInfo;

/**
 * @描述 所有的service、entity、property；无需在每个controller类中重复写入
 * @author Kevin
 *
 */
public class BaseControllerProperties {
	
	//service注入
	public @Resource Cache cache;
	public @Resource LogService logService;
	public @Resource BaseService baseService;
	public @Resource AccountService accountService;
	public @Resource AuditOrgService auditOrgService;
	public @Resource DepartmentService departmentService;
	public @Resource RoleService roleService;
	public @Resource FunctionService functionService;
	public @Resource ZtreeDataTransformService ztreeDataTransformService;
	public @Resource StaffService staffService;
	public @Resource OrgService orgService;
	public @Resource MailService mailService;
	public @Resource AreaService areaService;
	public @Resource AuditTypeService auditTypeService;
	public @Resource CheckContentService checkContentService;
	public @Resource CheckItemService checkItemService;
	public @Resource MajorService majorService;
	public @Resource BusinessFileService businessFileService;
	public @Resource FileService fileService;
	
	//系统属性
	public String jsonText = "[]";
	public Logger logger;
	public PageInfo pageInfo;
	public Integer page;
	public Integer rows;
	
	//系统管理的查询条件
	public ListAccountForm queryAccount;
	public ListAuditOrgForm queryOrg;
	public ListDepartmentForm queryDep;
	public ListRoleForm queryRole;
	public ListStaffForm queryStaff;
	public ListMailQuery queryMail;
	public ListAreaForm queryArea;
	public ListAuditTypeForm queryAuditType;
	public ListCheckContentForm queryCheckContent;
	public ListCheckItemForm queryCheckItem;
	public ListMajorForm queryMajor;
	public ListBusinessFileForm query;
	
	public SaveAccountForm saveAccountForm;
	public SaveAuditOrgForm saveAuditOrgForm;
	public SaveDepartmentForm saveDepartmentForm;
	public SaveRoleForm saveRoleForm;
	public SaveStaffForm saveStaffForm;
	
	public ModifyPasswordForm modifyPasswordForm;

	//参数项
	public String selDepartmentIds;
	public String departmentId;
	public String accountId;
	public String selRoleId;//选择的角色，用于确定功能树选中默认的节点
	public String selAuditOrgId;
	public String roleId;
	public String staffId;
	public String selOrgIds;//部门机构选择树默认选中节点，以逗号为分隔符。
	public String orgId;
	public String selRoleIds;
	public String accountIds;
	public String functionIds;
	public String areaId;
	public String auditTypeId;
	public String checkContentId;
	public String checkItemId;
	public String majorId;
	public String businessFileId;
	
	//mail
	public String mailId;
	public String downloadFileName;
	public List<File> upload;
	public List<String> uploadFileName;
	public List<String> uploadContentType;
	
	
	//对象属性
	public Account account;
	public Department department;
	public Function function;
	public Staff staff;
	public AuditOrg auditOrg;
	public Mail mail;
	public MailInfo mailInfo;
	public Area area;
	public AuditType auditType;
	public CheckContent checkContent;
	public CheckItem checkItem;
	public Major major;
	public BusinessFile businessFile;
	public InputStream downloadInputStream;
	
	//-------------------------get...-------set...----------------------------//
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public List<File> getUpload() {
		return upload;
	}
	public void setUpload(List<File> upload) {
		this.upload = upload;
	}
	public List<String> getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public List<String> getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	
}
