package com.yida.mail.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.Page;
import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.core.common.PageInfo;
import com.yida.mail.entity.Mail;
import com.yida.mail.entity.MailParty;
import com.yida.mail.entity.MailParty.PartyType;
import com.yida.mail.vo.MailInfo;

@Controller
@RequestMapping("/mail")
public class MailController extends BaseController {
	
	@RequestMapping("main")
	public String main(HttpServletRequest request) {
		request.setAttribute("sp", request.getParameter("sp"));
		return "business/mail/jsp/main";
	}
	
	/**
	 * 收件箱
	 */
	@RequestMapping("showInboxMailInfo")
	public String showInboxMailInfo(HttpServletRequest request) {
		Page<MailInfo> page = this.mailService.listInboxMail(super.getCurrentStaff().getId(), queryMail, getPage(), getRows());
		request.setAttribute("list", page.getRows());
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(getPage());
		pageInfo.setMaxResult(getRows());
		pageInfo.setTotalResult(page.getTotal());
		request.setAttribute("pageInfo", pageInfo);
		return "business/mail/jsp/inbox";
	}
	
	/**
	 * 发件箱
	 */
	@RequestMapping("showOutboxMailInfo")
	public String showOutboxMailInfo(HttpServletRequest request) {
		Page<MailInfo> p = this.mailService.listOutboxMail(super.getCurrentStaff().getId(), queryMail, getPage(), getRows());
		request.setAttribute("list", p.getRows());
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(getPage());
		pageInfo.setMaxResult(getRows());
		pageInfo.setTotalResult(p.getTotal());
		request.setAttribute("pageInfo", pageInfo);
		return "business/mail/jsp/outbox";
	}
	
	@RequestMapping("showRecycleMailInfo") 
	public String showRecycleMailInfo(HttpServletRequest request) {
		Page<MailInfo> p = this.mailService.listRecycleMail(super.getCurrentStaff().getId(), queryMail, getPage(), getRows());
		request.setAttribute("list", p.getRows());
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(getPage());
		pageInfo.setMaxResult(getRows());
		pageInfo.setTotalResult(p.getTotal());
		request.setAttribute("pageInfo", pageInfo);
		return "business/mail/jsp/recycle";
	}
	
	/**
	 * 显示邮件列表
	 */
	@RequestMapping("listMailInfo")
	public String listMailInfo(HttpServletRequest request) {
		int flag = Integer.parseInt(request.getParameter("flag"));
		String partyId = super.getCurrentStaff().getId();
		pageInfo = new PageInfo();
		pageInfo.setCurrentPage(getPage());
		pageInfo.setMaxResult(getRows());
		
		Page<?> p = null;
		if (0 == flag) {
			p = this.mailService.listDraftMail(partyId, queryMail, getPage(), getRows());
		} else if (1 == flag) {
			p = this.mailService.listOutboxMail(partyId, queryMail, getPage(), getRows());
		} else if (2 == flag) {
			p = this.mailService.listInboxMail(partyId, queryMail, getPage(), getRows());
		} else if (3 == flag) {
			p = this.mailService.listRecycleMail(partyId, queryMail, getPage(), getRows());
		}
		pageInfo.setTotalResult(p.getTotal());
		request.setAttribute("list", p.getRows());
		request.setAttribute("page", pageInfo);
		return "listMailInfo";
	}
	
	/**
	 * 写邮件
	 */
	@RequestMapping("writeMail")
	public String writeMail(HttpServletRequest request) {
		
		// 转发
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		
		if ("forward".equals(type)) {
			mail = this.mailService.initForwardMailInfo(mailId, super.getCurrentStaff().getId());
			mailId = mail.getId();
			return "business/mail/jsp/writePage";
		} 
		
		// 回复
		if ("reply".equals(type)) {
			mail = this.mailService.initReplyMailInfo(mailId, super.getCurrentStaff().getId());
			mailId = mail.getId();
			return "business/mail/jsp/writePage";
		}
		
		// 直接打开
		if (StringUtils.isMeaningFul(mailId)) {
			mail = this.mailService.getMailById(mailId);
		} else {
			mailId = StringUtils.uuid();
			mail = new Mail();
			mail.setId(mailId);
		}
		return "business/mail/jsp/writePage";
	}
	
	/**
	 * 发送邮件
	 */
	@RequestMapping("sendMail")
	public String sendMail(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String toPartyIds = request.getParameter("toPartyIds");
			String ccPartyIds = request.getParameter("ccPartyIds");
			String[] toPartyIdArray = toPartyIds.split(",");
			String[] ccPartyIdArray = ccPartyIds.split(",");
			Set<MailParty> idSet = new HashSet<MailParty>(toPartyIdArray.length + ccPartyIdArray.length);
			List<MailParty> list = new ArrayList<MailParty>(toPartyIdArray.length + ccPartyIdArray.length);
			for (String id : toPartyIdArray) {
				if (StringUtils.isMeaningFul(id) && !idSet.contains(id)) {
					MailParty mp = new MailParty();
					mp.setPartyId(id);
					mp.setType(PartyType.TO);
					list.add(mp);
				}
			}
			for (String id : ccPartyIdArray) {
				if (StringUtils.isMeaningFul(id) && !idSet.contains(id)) {
					MailParty mp = new MailParty();
					mp.setId(null);
					mp.setPartyId(id);
					mp.setType(PartyType.CC);
					list.add(mp);
				}
			}
			mail.setSenderId(super.getCurrentStaff().getId());
			mail.setReceivers(list);
			this.mailService.sendMail(mail);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "发送成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "发送失败：" + e.getMessage());
		}
		return super.jsonResultView(result);
	}
	
	/**
	 * 读邮件
	 */
	@RequestMapping("readMail")
	public String readMail() {
		this.mail = this.mailService.readMail(mailId, getCurrentStaff().getId());
		mail.setSenderName(this.staffService.getStaffById(mail.getSenderId()).getName());
		return "business/mail/jsp/readPage";
	}
	
	/**
	 * 删除邮件
	 */
	@RequestMapping("delMail")
	public String delMail() {
		JSONObject result = new JSONObject();
		try {
			this.mailService.delMail(mailId, super.getCurrentStaff().getId());
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除失败：" + e.getMessage());
		}
		return super.jsonResultView(result);
	}
	
	/**
	 * 收件箱中删除
	 * @return
	 */
	@RequestMapping("delFromInbox")
	public String delFromInbox(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String ids = request.getParameter("ids");
			if (StringUtils.isMeaningFul(ids)) {
				String[] mailIds = ids.split(",");
				List<String> tempMailIds = new ArrayList<String>(mailIds.length);
				for (String id : mailIds) {
					if (StringUtils.isMeaningFul(id)) {
						tempMailIds.add(id);
					}
				}
				this.mailService.delFromInbox(tempMailIds, super.getCurrentStaff().getId());
			}
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除失败：" + e.getMessage());
		}
		return super.jsonResultView(result);
	}
	
	/**
	 * 发送件中删除
	 * @return
	 */
	@RequestMapping("delFromOutbox")
	public String delFromOutbox(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String ids = request.getParameter("ids");
			if (StringUtils.isMeaningFul(ids)) {
				String[] mailIds = ids.split(",");
				List<String> tempMailIds = new ArrayList<String>(mailIds.length);
				for (String id : mailIds) {
					if (StringUtils.isMeaningFul(id)) {
						tempMailIds.add(id);
					}
				}
				this.mailService.delFromOutbox(tempMailIds, super.getCurrentStaff().getId());
			}
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除失败：" + e.getMessage());
		}
		return super.jsonResultView(result);
	}
	
	/**
	 * 回收站中删除
	 * @return
	 */
	@RequestMapping("delFromRecycle")
	public String delFromRecycle(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String ids = request.getParameter("ids");
			if (StringUtils.isMeaningFul(ids)) {
				String[] mailIds = ids.split(",");
				List<String> tempMailIds = new ArrayList<String>(mailIds.length);
				for (String id : mailIds) {
					if (StringUtils.isMeaningFul(id)) {
						tempMailIds.add(id);
					}
				}
				this.mailService.delFromOutbox(tempMailIds, super.getCurrentStaff().getId());
			}
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "删除成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "删除失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 回收站中还原操作
	 * @return
	 */
	@RequestMapping("recycleFromRecycle")
	public String recycleFromRecycle(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String ids = request.getParameter("ids");
			if (StringUtils.isMeaningFul(ids)) {
				String[] mailIds = ids.split(",");
				List<String> tempMailIds = new ArrayList<String>(mailIds.length);
				for (String id : mailIds) {
					if (StringUtils.isMeaningFul(id)) {
						tempMailIds.add(id);
					}
				}
				this.mailService.resetFromRecycle(tempMailIds, super.getCurrentStaff().getId());
			}
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "操作成功。");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "操作失败：" + e.getMessage());
		}
		return super.jsonResultView(result);
	}

}
