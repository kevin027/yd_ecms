package com.yida.core.common.easyui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Role;

public class EasyUIHelper {

	private EasyUIHelper() {
	}
	
	/**
	 * EASYUI TREE
	 * @param roles
	 * @param checkRoleIdSet
	 * @return
	 */
	public final static String roleTree(List<Role> roles, Set<String> checkRoleIdSet) {
		List<EasyUITreeData> ds = new ArrayList<EasyUITreeData>(roles.size());
		for (Role r : roles) {
			EasyUITreeData d = new EasyUITreeData();
			d.setId(r.getId());
			d.setText(r.getName());
			d.setChecked((null != checkRoleIdSet && checkRoleIdSet.contains(r.getId())));
			ds.add(d);
		}
		return StringUtils.toJsonArrayIncludeProperty(ds, Arrays.asList("id", "text", "checked", "state"));
	}

}
