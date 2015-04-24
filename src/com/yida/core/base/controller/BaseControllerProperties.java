package com.yida.core.base.controller;

import com.yida.basedata.Cache;
import com.yida.basedata.area.service.AreaService;
import com.yida.basedata.area.vo.ListAreaForm;
import com.yida.basedata.audittype.service.AuditTypeService;
import com.yida.basedata.audittype.vo.ListAuditTypeForm;
import com.yida.basedata.checkcontent.service.CheckContentService;
import com.yida.basedata.checkcontent.vo.ListCheckContentForm;
import com.yida.basedata.checkitems.service.CheckItemService;
import com.yida.basedata.checkitems.vo.ListCheckItemForm;
import com.yida.basedata.major.service.MajorService;
import com.yida.basedata.major.vo.ListMajorForm;
import com.yida.core.base.service.*;
import com.yida.core.base.vo.*;
import com.yida.core.common.PageInfo;
import com.yida.desktop.service.DesktopService;
import com.yida.file.service.BusinessFileService;
import com.yida.file.service.FileService;
import com.yida.file.vo.ListBusinessFileForm;
import com.yida.log.service.LogService;
import com.yida.mail.entity.Mail;
import com.yida.mail.service.MailService;
import com.yida.mail.vo.ListMailQuery;
import com.yida.mail.vo.MailInfo;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @描述 所有的service、entity、property；无需在每个controller类中重复写入
 * @author Kevin
 *
 */
public class BaseControllerProperties {
	
	//service注入
	public @Resource Cache cache;
    public @Resource DesktopService desktopService;
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

	//mail
	public String mailId;
	public String downloadFileName;
	public List<File> upload;
	public List<String> uploadFileName;
	public List<String> uploadContentType;
	
	
	//对象属性
	public Mail mail;
	public MailInfo mailInfo;

	
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
