package com.yida.mail.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 邮件参与者
 */
@Entity
@Table(name = "mail_party")
public class MailParty {
	
	public enum PartyType {
		TO("收件人"),CC("抄送"),BCC("暗抄送");
		public final String chinese;
		private PartyType(String chinese) {
			this.chinese = chinese;
		}
	}
	
	public enum MailPlace {
		INBOX("收件箱"), RECYCLE("回收站"), HIDE("删除");
		public final String chinese;
		private MailPlace(String chinese) {
			this.chinese = chinese;
		}
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="id", columnDefinition="VARCHAR(32)")
	private String id;
	
	/**
	 * 所关联的邮件的ID
	 */
	@Column(name="mail_id", columnDefinition="VARCHAR(32)")
	private String mailId;
	
	/**
	 * 参与者类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="type", columnDefinition="nvarchar(20)")
	private PartyType type;
	
	/**
	 * 参与者记录ID
	 */
	@Column(name="party_id", columnDefinition="varchar(50)")
	private String partyId;
	
	/**
	 * 参与者参与时的名称
	 */
	@Column(name="party_name", columnDefinition="nvarchar(50)")
	private String partyName;
	
	/**
	 * 邮件在参与者中的状态：未读，已读。
	 */
	@Column(name="is_read")
	private Boolean isRead;
	
	/**
	 * 邮件在参与者中的位置：收件箱，回收站，已隐藏
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="mail_place", columnDefinition="NVARCHAR(20)")
	private MailPlace mailPlace;
	
	/**
	 * 邮件是否已读
	 */
	@Column(name="read_date")
	private Date readDate;
	
	public MailParty() {
		
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
		MailParty other = (MailParty) obj;
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

	public PartyType getType() {
		return type;
	}

	public void setType(PartyType type) {
		this.type = type;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public MailPlace getMailPlace() {
		return mailPlace;
	}

	public void setMailPlace(MailPlace mailPlace) {
		this.mailPlace = mailPlace;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
}
