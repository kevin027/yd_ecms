package com.yida.mail.vo;

import java.util.Date;
import java.util.List;

import com.yida.mail.entity.Mail;
import com.yida.mail.entity.MailAttach;
import com.yida.mail.entity.MailParty;


public class MailInfo {
	
	private String id;

	private String subject;
	
	private String content;
	
	private Byte isSend;
	
	private Byte isRead;
	
	private Date sendDate;
	
	private String partyNames;
	
	private String senderId;
	
	private String senderName;
	
	private String partyNames1;
	
	private String partyNames2;
	
	private List<MailAttach> attachs;
	
	private List<MailParty> partys;
	
	public void fillValue(Mail mail) {
		if (null == mail) return;
		mail = new Mail();
		mail.setId(this.id);
		mail.setSubject(this.subject);
		mail.setContent(this.content);
		mail.setSenderId(this.senderId);
		mail.setSendDate(new Date());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getIsSend() {
		return isSend;
	}

	public void setIsSend(Byte isSend) {
		this.isSend = isSend;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public List<MailAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<MailAttach> attachs) {
		this.attachs = attachs;
	}

	public List<MailParty> getPartys() {
		return partys;
	}

	public void setPartys(List<MailParty> partys) {
		this.partys = partys;
	}

	public String getPartyNames() {
		return partyNames;
	}

	public void setPartyNames(String partyNames) {
		this.partyNames = partyNames;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getPartyNames1() {
		return partyNames1;
	}

	public void setPartyNames1(String partyNames1) {
		this.partyNames1 = partyNames1;
	}

	public String getPartyNames2() {
		return partyNames2;
	}

	public void setPartyNames2(String partyNames2) {
		this.partyNames2 = partyNames2;
	}

	public Byte getIsRead() {
		return isRead;
	}

	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}
	
}
