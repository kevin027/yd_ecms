package com.yida.mail.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yida.core.base.dao.BaseDao;
import com.yida.mail.entity.MailParty;
import com.yida.mail.entity.MailParty.MailPlace;

@Repository
public class MailPartyDao extends BaseDao<MailParty, String> {

	
	public List<MailParty> listMailPartyByMailId(String mailId) {
		String sql = String.format("select o.* from %1$s o where o.mail_id = ?", getTableName());
		return findListBySql(sql, new Object[]{mailId});
	}

	
	public void persistMailParty(MailParty mailParty) {
		this.persist(mailParty);
	}

	
	public void delMailPartyByMailId(String mailId) {
		this.executeSQL("delete from mail_party where mail_id = ?", new Object[]{mailId});
	}

	
	public void updateMailPlaceForParty(String mailId, String staffId, MailPlace place) {
		this.executeSQL("update mail_party set mail_place = ? where mail_id = ? and party_id = ?", new Object[]{place.toString(), mailId, staffId});
	}
}
