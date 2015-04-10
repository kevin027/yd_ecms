package com.yida.core.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yida.core.base.entity.Function;
import com.yida.core.common.ztree.ZtreeData;

@Service
public class ZtreeDataTransformService {
	
	@Transactional
	public List<ZtreeData> transform(List<Function> funcionts) {
		List<ZtreeData> zdatas = new ArrayList<ZtreeData>();
		Map<String, ZtreeData> zdMap = new ConcurrentHashMap<String, ZtreeData>();
		this.fillFunctionValue(funcionts, zdatas, zdMap);
		return zdatas;
	}
	
	@Transactional
	private void fillFunctionValue(List<Function> fdatas, List<ZtreeData> zdatas, Map<String, ZtreeData> zdMap) {
		for (Function f : fdatas) {
			ZtreeData z = new ZtreeData();
			z.setId(f.getId());
			z.setName(f.getName());
			z.setIsParent(null != f.getChildren() && f.getChildren().size() > 0);
			z.setOpen(z.getIsParent());
			if (null == f.getParent()) {
				z.setPid("0");
				zdatas.add(z);
			} else {
				ZtreeData pz = zdMap.get(f.getParent().getId());
				if (null != pz) {
					z.setPid(pz.getId());
					pz.getChildren().add(z);
				}
			}
			zdMap.put(z.getId(), z);
			if (null != f.getChildren() && f.getChildren().size() > 0) {
				z.setChildren(new ArrayList<ZtreeData>(f.getChildren().size()));
				this.fillFunctionValue(f.getChildren(), zdatas, zdMap);
			}
		}
	}
}
