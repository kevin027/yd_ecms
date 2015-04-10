package com.yida.basedata.checkitems.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.basedata.checkitems.entity.CheckItem;
import com.yida.core.base.dao.BaseDao;

@Repository
@Scope("singleton")
@Lazy(false)
public class CheckItemDao extends BaseDao<CheckItem, String> {

}
