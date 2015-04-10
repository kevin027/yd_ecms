package com.tools.utils;

import java.math.BigDecimal;
import java.util.Map;

public class BigDecimalConverter {

	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (BigDecimal.class == toClass) {
			BigDecimal bd = null;
			String bdStr = values[0];
			if (bdStr != null && !"".equals(bdStr)) {
				bd = new BigDecimal(bdStr.replaceAll(",", ""));
			}
			return bd;
		}
		return BigDecimal.ZERO;
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map context, Object o) {
		if (null == o) return null; 
		if (o instanceof BigDecimal) {
			BigDecimal b = new BigDecimal(o.toString()).setScale(2,
					BigDecimal.ROUND_HALF_DOWN);
			return b.toString();
		}
		return o.toString();
	}

}
