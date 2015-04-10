package com.tools.sys;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.processors.JsonValueProcessor;

import com.yida.core.ext.json.processor.DateValueJsonProcessor;

/**
 * 系统共用
 */
public final class SysCommon {
	
	public static final class SaftDateFormat {
		private ThreadLocal<DateFormat> threadLocal;
		private SaftDateFormat(final String format) {
			threadLocal = new ThreadLocal<DateFormat>() {
		        @Override
		        protected synchronized DateFormat initialValue() {
		            return new SimpleDateFormat(format);
		        }
		    };
		}
	    public Date parse(String dateStr) throws ParseException {
	    	if (null == dateStr) return null;
	    	dateStr = dateStr.trim();
	    	if (0 == dateStr.length()) return null;
	        return threadLocal.get().parse(dateStr);
	    }

	    public String format(Object date) {
	    	if (null == date) return "";
	        return threadLocal.get().format(date);
	    }
	}

	public final static SaftDateFormat YMD_YMD = new SaftDateFormat("yyyy/MM/dd");
	public final static SaftDateFormat YMD = new SaftDateFormat("yyyy-MM-dd");
	public final static SaftDateFormat YMD_H = new SaftDateFormat("yyyy-MM-dd HH");
	public final static SaftDateFormat YMD_HM = new SaftDateFormat("yyyy-MM-dd HH:mm");
	public final static SaftDateFormat YMD_HMS = new SaftDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static SaftDateFormat CYMD = new SaftDateFormat("yyyy年MM月dd日");
	public final static SaftDateFormat CYMD_H = new SaftDateFormat("yyyy年MM月dd日 HH时");
	public final static SaftDateFormat CYMD_HM = new SaftDateFormat("yyyy年MM月dd日 HH时mm分");
	public final static SaftDateFormat CYMD_HMS = new SaftDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	
	public final static SaftDateFormat UUDT = new SaftDateFormat("yyyyMMddHHmmssSSS");
	
	public final static JsonValueProcessor DATE_JSON_PROCESSOR_YMD = new DateValueJsonProcessor(YMD);
	public final static JsonValueProcessor DATE_JSON_PROCESSOR_YMD_HMS = new DateValueJsonProcessor(YMD_HMS);
	
}
