package com.yida.core.base.service;

import com.yida.basedata.area.dao.AreaDao;
import com.yida.basedata.audittype.dao.AuditTypeDao;
import com.yida.basedata.checkcontent.dao.CheckContentDao;
import com.yida.basedata.checkitems.dao.CheckItemDao;
import com.yida.basedata.major.dao.MajorDao;
import com.yida.core.base.dao.*;
import com.yida.file.dao.BusinessFileDao;
import com.yida.file.dao.FileDao;
import com.yida.file.dao.FileOpConfigDao;
import com.yida.file.dao.FileOpLogDao;
import com.yida.log.dao.LogDao;
import com.yida.mail.dao.MailAttachDao;
import com.yida.mail.dao.MailDao;
import com.yida.mail.dao.MailPartyDao;

import javax.annotation.Resource;

/**
 * @描述 所有dao集合
 * @author Kevin
 *
 */
public class BaseServiceProperties {

	public @Resource SqlCommonDao sqlCommonDao;
	public @Resource AccountDao accountDao;
	public @Resource RoleDao roleDao;
	public @Resource StaffDao staffDao;
	public @Resource OrgDao orgDao;
	public @Resource AuditOrgDao auditOrgDao;
	public @Resource DepartmentDao departmentDao;
	public @Resource FunctionDao functionDao;
	public @Resource MailDao mailDao;
	public @Resource MailPartyDao mailPartyDao;
	public @Resource MailAttachDao mailAttachDao;
	public @Resource AreaDao areaDao;
	public @Resource AuditTypeDao auditTypeDao;
	public @Resource CheckContentDao checkContentDao;
	public @Resource CheckItemDao checkItemDao;
	public @Resource MajorDao majorDao;
	public @Resource BusinessFileDao businessFileDao;
	public @Resource FileDao fileDao;
	public @Resource FileOpLogDao fileOpLogDao;
	public @Resource FileOpConfigDao fileOpConfigDao;
	public @Resource LogDao logDao;

}
