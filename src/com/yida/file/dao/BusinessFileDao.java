package com.yida.file.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.core.base.dao.BaseDao;
import com.yida.file.entity.BusinessFile;

@Repository
@Scope("singleton")
@Lazy(false)
public class BusinessFileDao extends BaseDao<BusinessFile, String> {

}
