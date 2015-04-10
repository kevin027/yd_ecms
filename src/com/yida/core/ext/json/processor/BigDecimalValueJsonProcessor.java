package com.yida.core.ext.json.processor;

import net.sf.json.JSONNull;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public enum BigDecimalValueJsonProcessor implements JsonValueProcessor {

	INSTANCE;
	
	@Override
	public Object processArrayValue(Object value, JsonConfig jc) {
		return processObjectValue(null, value, jc);
	}

	@Override
	public Object processObjectValue(String propName, Object value, JsonConfig jc) {
		if (null == value) return JSONNull.getInstance();
		return value;
	}

}

