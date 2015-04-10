package com.yida.core.common;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JsonConfig;

import com.tools.sys.SysCommon;
import com.yida.core.ext.json.processor.BigDecimalValueJsonProcessor;

public class JSONConfigFactory {
	
	public static JsonConfig getSimpleConfig() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, SysCommon.DATE_JSON_PROCESSOR_YMD_HMS);
		jc.registerJsonValueProcessor(java.sql.Date.class, SysCommon.DATE_JSON_PROCESSOR_YMD);
		jc.registerJsonValueProcessor(java.sql.Timestamp.class, SysCommon.DATE_JSON_PROCESSOR_YMD_HMS);
		jc.registerJsonValueProcessor(BigDecimal.class, BigDecimalValueJsonProcessor.INSTANCE);
		return jc;
	}
	
	public static JsonConfig getYMDConfig() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, SysCommon.DATE_JSON_PROCESSOR_YMD);
		jc.registerJsonValueProcessor(java.sql.Date.class, SysCommon.DATE_JSON_PROCESSOR_YMD);
		jc.registerJsonValueProcessor(java.sql.Timestamp.class, SysCommon.DATE_JSON_PROCESSOR_YMD_HMS);
		jc.registerJsonValueProcessor(BigDecimal.class, BigDecimalValueJsonProcessor.INSTANCE);
		return jc;
	}
	
	public static JsonConfig getYMDHMSConfig() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, SysCommon.DATE_JSON_PROCESSOR_YMD_HMS);
		jc.registerJsonValueProcessor(java.sql.Date.class, SysCommon.DATE_JSON_PROCESSOR_YMD_HMS);
		jc.registerJsonValueProcessor(java.sql.Timestamp.class, SysCommon.DATE_JSON_PROCESSOR_YMD_HMS);
		jc.registerJsonValueProcessor(BigDecimal.class, BigDecimalValueJsonProcessor.INSTANCE);
		return jc;
	}
	
	
}
