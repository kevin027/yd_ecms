package com.yida.core.common;

import java.util.ArrayList;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AutoArrayList extends ArrayList {

	private static final long serialVersionUID = 1593635885785753965L;
	
	private Class itemClass;

	public AutoArrayList(Class itemClass) {
		this.itemClass = itemClass;
	}

	@Override
	public Object get(int index) {
		try {
			while (index >= size()) {
				add(itemClass.newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.get(index);
	}
}
