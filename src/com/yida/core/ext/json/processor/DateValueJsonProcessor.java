package com.yida.core.ext.json.processor;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.tools.sys.SysCommon.SaftDateFormat;

public class DateValueJsonProcessor implements JsonValueProcessor {
	
	private SaftDateFormat sdf;
	
	public DateValueJsonProcessor(SaftDateFormat sdf) {
		this.sdf = sdf;
	}

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return process(arg0);
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1,
			JsonConfig arg2) {
		return process(arg1);
	}
	
	public String process(Object date) {
		if (null == date) return "";
		return sdf.format(date);
	}
}
