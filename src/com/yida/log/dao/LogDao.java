package com.yida.log.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.core.base.dao.BaseDao;
import com.yida.log.entity.Log;

@Repository
@Scope("singleton")
@Lazy(false)
public class LogDao extends BaseDao<Log, String> {

}
