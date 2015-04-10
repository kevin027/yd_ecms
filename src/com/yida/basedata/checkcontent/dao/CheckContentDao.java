package com.yida.basedata.checkcontent.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.basedata.checkcontent.entity.CheckContent;
import com.yida.core.base.dao.BaseDao;

@Repository
@Scope("singleton")
@Lazy(false)
public class CheckContentDao extends BaseDao<CheckContent, String>{

}
