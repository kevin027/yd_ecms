package com.yida.file.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tools.utils.StringUtils;
import com.yida.core.base.dao.BaseDao;
import com.yida.file.entity.FileInfo;

@Repository
public class FileDao extends BaseDao<FileInfo, String> {

	public int getFileCount(String module, String refId) {
		StringBuilder sqlBuffer = new StringBuilder("select count(o.id) from ").append(super.getTableName()).append(" o where o.is_hide = 0");
		List<Object> params = new ArrayList<Object>(2);
		if (StringUtils.isMeaningFul(module)) {
			sqlBuffer.append(" and o.ref_module = ?");
			params.add(module);
		}
		
		if (StringUtils.isMeaningFul(refId)) {
			sqlBuffer.append(" and o.ref_record_id = ?");
			params.add(refId);
		}
		return this.findCountBySql(sqlBuffer.toString(), params.toArray());
	}

}
