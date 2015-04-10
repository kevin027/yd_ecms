package com.yida.basedata.audittype.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.basedata.audittype.entity.AuditType;
import com.yida.core.base.dao.BaseDao;

@Repository
@Scope("singleton")
@Lazy(false)
public class AuditTypeDao extends BaseDao<AuditType, String> {

}
