package com.yida.mail.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mail")
public class Mail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", columnDefinition="VARCHAR(32)")
	private String id;

	@Column(name="subject", columnDefinition="NVARCHAR(512)", nullable=true, unique=false)
	private String subject;
	
	@Column(name="content", columnDefinition="NVARCHAR(4000)", nullable=true, unique=false)
	private String content;
	
	/**
	 * 是否已经发送
	 * 用于标识该邮件是草稿还是正稿。如果该类一条实体记录被标记为已发送，则说明是正稿，反之则为草稿。
	 */
	@Column(name="is_send")
	private Boolean isSend = Boolean.FALSE;
	
	/**
	 * 发送人ID
	 */
	@Column(name="sender_id")
	private String senderId;
	
	/**
	 * 发送日期
	 * 只有发送的邮件有发送日期。
	 */
	@Column(name="send_date")
	private Date sendDate;
	
	/**
	 * 发送人邮件地址
	 */
	@Column(name="send_mail")
	private String sendMail;
	
	/**
	 * 是否隐藏
	 */
	@Column(name="is_hide")
	private Boolean isHide = Boolean.FALSE;
	
	/**
	 * 保存邮件收件人名称，冗余字段，优化查询。
	 */
	@Column(name="party_names1", columnDefinition="nvarchar(400)")
	private String partyNames1;
	
	/**
	 * 保存邮件抄送人名称，冗余字段，优化查询。
	 */
	@Column(name="party_names2", columnDefinition="nvarchar(400)")
	private String partyNames2;
	
	@Transient
	private String senderName;
	
	@Transient
	private List<MailParty> receivers;
	
	@Transient
	private List<MailAttach> attachs;
	
	
	public Mail() {
		super();
	}
	
	public Mail(String id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Mail [subject=" + subject + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mail other = (Mail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSendMail() {
		return sendMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
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

	public Boolean getIsHide() {
		return isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	public List<MailParty> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<MailParty> receivers) {
		this.receivers = receivers;
	}

	public List<MailAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<MailAttach> attachs) {
		this.attachs = attachs;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
}
