package com.yida.desktop.service;

import com.yida.core.base.entity.Account;
import com.yida.core.base.service.BaseService;
import com.yida.core.common.PageInfo;
import com.yida.desktop.entity.DevMark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesktopService extends BaseService {

	public List<DevMark> getSystemDevMark(Account currentAccount, PageInfo pageInfo) {
		return sqlCommonDao.findEntityListBySql(DevMark.class,"from " + DevMark.class.getCanonicalName());
	}
	
}
