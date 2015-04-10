package com.yida.mail.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tools.sys.Page;
import com.tools.sys.SysVariable;
import com.tools.utils.DateUtil;
import com.tools.utils.StringUtils;
import com.yida.core.base.service.BaseService;
import com.yida.mail.controller.MailAttachController;
import com.yida.mail.entity.Mail;
import com.yida.mail.entity.MailAttach;
import com.yida.mail.entity.MailParty;
import com.yida.mail.entity.MailParty.MailPlace;
import com.yida.mail.entity.MailParty.PartyType;
import com.yida.mail.vo.ListMailQuery;
import com.yida.mail.vo.MailInfo;

@Service("mailService")
@Scope("singleton")
@Lazy(false)
public class MailService extends BaseService{
	
	/**
	 * 列出参与者的草稿箱里的邮件
	 * @param senderId 发件人ID
	 * @param query 过滤条件
	 * @param curPage 分页信息，当前面
	 * @param pageSize 分页信息，每页大小
	 * @return
	 */
	public Page<MailInfo> listDraftMail(String senderId, ListMailQuery query, Integer curPage, Integer pageSize) {
		return this.listSendMail(senderId, false, query, curPage, pageSize);
	}
	
	/**
	 * 列出参与者的发件箱里的邮件
	 * @param senderId 发件人ID
	 * @param query 过滤条件
	 * @param curPage 分页信息，当前面
	 * @param pageSize 分页信息，每页大小
	 * @return
	 */
	public Page<MailInfo> listOutboxMail(String senderId, ListMailQuery query, Integer curPage, Integer pageSize) {
		return listSendMail(senderId, true, query, curPage, pageSize);
	}
	
	/**
	 * 列出参与者的收件箱的邮件
	 * @param receiveId 收件人ID
	 * @param query 过滤条件
	 * @param curPage 分页信息，当前面
	 * @param pageSize 分页信息，每页大小
	 * @return
	 */
	public Page<MailInfo> listInboxMail(String receiveId, ListMailQuery query, Integer curPage, Integer pageSize) {
		return this.listReceiveMail(receiveId, MailPlace.INBOX, query, curPage, pageSize);
	}
	
	/**
	 * 列出参与者的回收站的邮件
	 * @param receiveId 收件人ID
	 * @param query 过滤条件
	 * @param curPage 分页信息，当前面
	 * @param pageSize 分页信息，每页大小
	 * @return
	 */
	public Page<MailInfo> listRecycleMail(String receiveId, ListMailQuery query, Integer curPage, Integer pageSize) {
		return this.listReceiveMail(receiveId, MailPlace.RECYCLE, query, curPage, pageSize);
	}
	
	private Page<MailInfo> listReceiveMail(String receiveId, MailPlace place, ListMailQuery query, Integer curPage, Integer pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuilder fb = new StringBuilder(" from mail o");
		
		fb.append(" inner join s_staff s on s.id = o.sender_id");
		
		// 加入参与者的访问权限。
		fb.append(" inner join mail_party mpr on mpr.mail_id = o.id and mpr.party_id = ?");
		paramList.add(receiveId);
		
		// 邮件现处位置
		if (null != place) {
		  fb.append(" and mpr.mail_place = ?");
		  paramList.add(place.toString());
		}
		
		// 必须是正稿
		fb.append(" where o.is_send = 1"); 
		
		if (null != query) {
			if (StringUtils.isMeaningFul(query.getMailId())) {
				fb.append(" and o.id = ?");
				paramList.add(query.getMailId());
			}
			
			if (StringUtils.isMeaningFul(query.getSubject())) {
				fb.append(" and o.subject like ?");
				paramList.add("%" + query.getMailId().trim() + "%");
			}
		}
		
		String fromSql = fb.toString();
		Object[] params = paramList.toArray();
		
		int total = mailDao.findCountBySql("select count(o.id) " + fromSql, params);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select o.id, o.subject, o.content, o.is_send isSend, o.send_date sendDate, o.sender_id senderId");
		sb.append(", o.party_names1 partyNames1, o.party_names2 partyNames2, mpr.is_read isRead, s.id senderId, s.name senderName");
		List<MailInfo> rows = mailDao.findListBySqlAsAliasToBean2(sb.toString() + fromSql + " order by o.send_date desc", MailInfo.class, params, curPage, pageSize);
		
		for (MailInfo mi : rows) {
			mi.setPartys(this.mailPartyDao.listMailPartyByMailId(mi.getId()));
			mi.setAttachs(this.mailAttachDao.listMailAttachByMailId(mi.getId()));
		}
		return Page.create(rows, total);
	}
	
	private Page<MailInfo> listSendMail(String senderId, Boolean isSend, ListMailQuery query, Integer curPage, Integer pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		
		// 必须加入该记录是没有被删除的
		StringBuilder fb = new StringBuilder(" from mail o  inner join s_staff s on s.id = o.sender_id where o.is_hide = 0");
		
		// 是否已经发送（判断是草稿还是正稿）
		fb.append(" and o.is_send = ?");
		paramList.add(isSend.booleanValue() == true ? 1 : 0);
		
		// 发送者条件
		fb.append(" and sender_id = ?");
		paramList.add(senderId);
		
		if (null != query) {
			if (StringUtils.isMeaningFul(query.getMailId())) {
				fb.append(" and o.id = ?");
				paramList.add(query.getMailId());
			}
			
			if (StringUtils.isMeaningFul(query.getSubject())) {
				fb.append(" and o.subject like ?");
				paramList.add("%" + query.getMailId().trim() + "%");
			}
		}
		
		String fromSql = fb.toString();
		Object[] params = paramList.toArray();
		
		int total = mailDao.findCountBySql("select count(o.id) " + fromSql, params);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select o.id, o.subject, o.content, o.is_send isSend, o.send_date sendDate, o.sender_id senderId, o.party_names1 partyNames1, o.party_names2 partyNames2, s.id senderId, s.name senderName");
		List<MailInfo> rows = mailDao.findListBySqlAsAliasToBean2(sb.toString() + fromSql + " order by o.send_date desc", MailInfo.class, params, curPage, pageSize);
		
		for (MailInfo mi : rows) {
			mi.setPartys(this.mailPartyDao.listMailPartyByMailId(mi.getId()));
			mi.setAttachs(this.mailAttachDao.listMailAttachByMailId(mi.getId()));
		}
		return Page.create(rows, total);
	}

	/**
	 * 删除邮件
	 * @param mailId
	 * @param partyId
	 */
	@Transactional
	public void delMail(String mailId, String partyId) {
		Mail mail = this.mailDao.get(mailId);
		if (mail.getSenderId().equals(partyId)) {
			// 隐藏该邮件对发送人的可见度
			this.mailDao.executeSQL("update mail set is_hide = 1 where id = ?", new Object[]{mailId});
		}
		// 隐藏该邮件对接收人的可见度
		this.mailDao.executeSQL("update mail_party set mail_place = ? where mail_id = ? and party_id = ?", new Object[]{MailPlace.HIDE.toString(), mailId, partyId});
	}

	/**
	 * 读邮件
	 * @param mailId
	 * @param partyId
	 * @return
	 */
	@Transactional
	public Mail readMail(String mailId, String receiveId) {
		// 设置该邮件的已读状态
		this.mailDao.executeSQL("update mail_party set is_read = ?, read_date = getDate() where mail_id = ? and party_id = ?", new Object[]{1, mailId, receiveId});
		return this.getMailById(mailId);
	}

	/**
	 * 写邮件
	 * @param mailId
	 * @return
	 */
	public Mail getMailById(String mailId) {
		Mail mail = this.mailDao.get(mailId);
		mail.setReceivers(this.mailPartyDao.listMailPartyByMailId(mailId));
		mail.setAttachs(this.mailAttachDao.listMailAttachByMailId(mailId));
		return mail;
	}

	/**
	 * 保存邮件
	 * @param mail
	 */
	@Transactional
	public void saveMail(Mail mail) {
		Mail per = mailDao.get(mail.getId());
		if (null == per) {
			mail.setIsHide(Boolean.FALSE);
			mail.setIsSend(Boolean.FALSE);
			mailDao.persist(mail);
		} else {
			per.setSubject(mail.getSubject());
			per.setContent(mail.getContent());
			this.mailPartyDao.delMailPartyByMailId(mail.getId());
		}
		for (MailParty mp : mail.getReceivers()) {
			mp.setId(null);
			mp.setMailId(mail.getId());
			mp.setIsRead(Boolean.FALSE); // 未读
			mp.setMailPlace(MailPlace.INBOX); // 收件箱中
			this.mailPartyDao.persist(mp);
		}
	}
	
	/**
	 * 发送邮件
	 * @param mail
	 */
	@Transactional
	public void sendMail(Mail mail) {
		Mail per = this.mailDao.get(mail.getId());
		if (null == per) { // 如果根据ID找不到邮件，就新建。
			
			boolean containToParty = false;
			for (MailParty mp : mail.getReceivers()) {
				if (MailParty.PartyType.TO ==  mp.getType()) {
					containToParty = true;
				}
				mp.setMailId(mail.getId());
				mp.setIsRead(Boolean.FALSE); // 未读
				mp.setMailPlace(MailPlace.INBOX); // 收件箱中
				this.mailDao.persistEntity(mp);
			}
			if (!containToParty) throw new IllegalStateException("没有选择收件人。");
			
			if (!StringUtils.isMeaningFul(mail.getSubject())) {
				throw new IllegalStateException("没有填写主题。");
			}
			if (!StringUtils.isMeaningFul(mail.getContent())) {
				throw new IllegalStateException("没有填写内容。");
			}
			if (null == mail.getReceivers() || 0 == mail.getReceivers().size()) {
				throw new IllegalStateException("没有选择收件人。");
			}
			mail.setIsHide(Boolean.FALSE);
			mail.setIsSend(Boolean.TRUE);
			mail.setSendDate(new Date());
			mailDao.persist(mail);
		} else {
			per.setIsSend(Boolean.TRUE);
			per.setSendDate(new Date());
			this.mailPartyDao.delMailPartyByMailId(mail.getId());
			for (MailParty mp : mail.getReceivers()) {
				mp.setMailId(mail.getId());
				mp.setIsRead(Boolean.FALSE); // 未读
				mp.setMailPlace(MailPlace.INBOX); // 收件箱中
				this.mailDao.persistEntity(mp);
			}
		}
	}

	/**
	 * 邮件附件上传
	 * @param MailAttachController
	 */
	@Transactional
	public List<MailAttach> addAttach(MailAttachController MailAttachController) {
		String mailId = MailAttachController.getMailId();
		List<File> uploads = MailAttachController.getUpload();
		List<MailAttach> list = new ArrayList<MailAttach>();
		if (null != uploads) {
			for (int i = 0; i < uploads.size(); i++) {
				File file = uploads.get(i);
				String fileName = MailAttachController.getUploadFileName().get(i);
				String contentType = MailAttachController.getUploadContentType().get(i);
				try {
					list.add(addAttach(mailId, file, fileName, contentType));
				} catch (Exception e) {
					// 这里需要取消件上传的文件
					e.printStackTrace();
				} finally {
					
				}
			}
		}
		return list;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private MailAttach addAttach(String mailId, File file, String fileName, String contentType) throws IOException {
		MailAttach ma = new MailAttach();
		ma.setId(null);
		ma.setMailId(mailId);
		ma.setUploadFileName(fileName);
		ma.setUploadFileType(fileName.replaceAll("^.+\\.", ""));
		ma.setUploadFileSize(Files.size(file.toPath()));
		ma.setSaveFileName(StringUtils.uuid());
		
		Path saveFileRoot = Paths.get(SysVariable.INSTANCE.getValue("EMAIL_ATTACH_ROOT") + File.separator);
		String mailAttachPath = mailId + File.separator;
		Path saveFilePath = saveFileRoot.resolve(mailAttachPath);
		if (!Files.exists(saveFilePath)) {
			Files.createDirectory(saveFilePath);
		}
		ma.setSaveFilePath(mailAttachPath + DateUtil.formatYMDHMSCN(new Date())+"_"+ StringUtils.uuid() + "." + ma.getUploadFileType());
		this.mailDao.persistEntity(ma);
		Files.copy(new FileInputStream(file), saveFileRoot.resolve(ma.getSaveFilePath()));
		return ma;
	}

	/**
	 * 根据邮件附件ID获取邮件附件保存信息
	 * @param parameter
	 */
	public MailAttach getAttachById(String attachId) {
		return this.mailAttachDao.get(attachId);
	}
	
	/**
	 * 初始化回复邮件信息
	 * @param mailId
	 * @param currentStaffId
	 * @return
	 */
	public Mail initReplyMailInfo(String mailId, String currentStaffId) {
		Mail replyMail = this.getMailById(mailId);
		String senderId = replyMail.getSenderId();
		
		Mail mail = new Mail(StringUtils.uuid());
		mail.setSubject("回复：" + replyMail.getSubject());
		mail.setContent(replyMail.getContent());
		
		// 设置发件人为回复的收件人
		List<MailParty> mps = new ArrayList<MailParty>();
		MailParty mp = new MailParty();
		mp.setPartyId(senderId);
		mp.setPartyName(staffDao.get(senderId).getName());
		mp.setType(PartyType.CC);
		mps.add(mp);
		mail.setReceivers(mps);
		return mail;
	}

	/**
	 * 初始化转发邮件信息
	 * @param mailId
	 * @param currentStaffId
	 * @return
	 */
	public Mail initForwardMailInfo(String mailId, String currentStaffId) {
		Mail forwardMail = this.getMailById(mailId);
		Mail mail = new Mail(StringUtils.uuid());
		mail.setSubject("转发：" + forwardMail.getSubject());
		mail.setContent(forwardMail.getContent());
		return mail;
	}
	
	/**
	 * 从发件箱中移除
	 * @param mailIds
	 * @param staffId
	 */
	@Transactional
	public void delFromOutbox(List<String> mailIds, String staffId) {
		for (String mailId : mailIds) {
			if (StringUtils.isMeaningFul(mailId)) {
				Mail mail = this.mailDao.get(mailId);
				mail.setIsHide(Boolean.TRUE);
				this.mailPartyDao.updateMailPlaceForParty(mailId, staffId, MailPlace.RECYCLE);
			}
		}
	}

	/**
	 * 从收件箱中移除
	 * @param mailIds
	 * @param staffId
	 */
	@Transactional
	public void delFromInbox(List<String> mailIds, String staffId) {
		for (String mailId : mailIds) {
			if (StringUtils.isMeaningFul(mailId)) {
				Mail mail = this.mailDao.get(mailId);
				mail.setIsHide(Boolean.TRUE);
				this.mailPartyDao.updateMailPlaceForParty(mailId, staffId, MailPlace.RECYCLE);
			}
		}
	}

	/**
	 * 从回收站中移除
	 * @param mailIds
	 * @param staffId
	 */
	@Transactional
	public void delFromCycle(List<String> mailIds, String staffId) {
		for (String mailId : mailIds) {
			if (StringUtils.isMeaningFul(mailId)) {
				this.mailPartyDao.updateMailPlaceForParty(mailId, staffId, MailPlace.HIDE);
			}
		}
	}
	
	/**
	 * 从回收站中撤销删除
	 * @param mailIds
	 * @param staffId
	 */
	@Transactional
	public void resetFromRecycle(List<String> mailIds, String staffId) {
		for (String mailId : mailIds) {
			if (StringUtils.isMeaningFul(mailId)) {
				this.mailPartyDao.updateMailPlaceForParty(mailId, staffId, MailPlace.INBOX);
			}
		}
	}
}
