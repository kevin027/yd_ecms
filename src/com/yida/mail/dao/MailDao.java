package com.yida.mail.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.core.base.dao.BaseDao;
import com.yida.mail.entity.Mail;

@Repository
@Scope("singleton")
@Lazy(false)
public class MailDao extends BaseDao<Mail, String> {

}
