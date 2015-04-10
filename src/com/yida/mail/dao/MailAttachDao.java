package com.yida.mail.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yida.core.base.dao.BaseDao;
import com.yida.mail.entity.MailAttach;

@Repository
public class MailAttachDao extends BaseDao<MailAttach, String> {

	public List<MailAttach> listMailAttachByMailId(String mailId) {
		String sql = String.format("select o.* from %1$s o where o.mail_id = ?", getTableName());
		return findListBySql(sql, new Object[]{mailId});
	}
}
