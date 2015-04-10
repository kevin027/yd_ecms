package com.yida.core.common.ztree;

import java.util.List;


public interface IZtreeData<S extends IZtreeData<S>> {
	void setId(String id);
	String getId();
	
	void setName(String name);
	String getName();
	
	void setChildren(List<S> ztree);
	List<S> getChildren();
}
