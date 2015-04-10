package com.tools.utils;

import ognl.Ognl;
import ognl.OgnlException;

import java.util.*;
import java.util.Map.Entry;

public class SqlUtils {
	
	public static StringBuilder andLike(StringBuilder sb, List<Object> params, String alias, String columnName, Object columnValue) {
		alias = (null != alias) ? alias.trim() : "";
		String key = columnName;
		Object val = columnValue;
		if (StringUtils.isMeaningFul(key) && null != val) {
			String value = ((String)val).trim();
			if (StringUtils.isMeaningFul(value)) {
				sb.append(" and ").append(alias).append(".").append(key).append(" like ?");
				params.add("%" + value + "%");
			}
		}
		return sb;
	}
	
	public static StringBuilder andLike(StringBuilder sb, List<Object> params, String alias, Object ognlRoot, String ognlExp) {
		try {
			alias = (null != alias) ? alias.trim() : "";
			Object val = Ognl.getValue(ognlExp, ognlRoot);
			andLike(sb, params, alias, ognlExp, val);
		} catch (OgnlException o) {
			throw new IllegalStateException("没有找到" + ognlExp);
		}
		return sb;
	}
	
	public static StringBuilder andLikeBatch(StringBuilder sb, List<Object> params, String alias, Map<String, Object> cvMap) {
		alias = (null != alias) ? alias.trim() : "";
		Set<Entry<String, Object>> set = cvMap.entrySet();
		for (Entry<String, Object> o : set) {
			String key = o.getKey();
			Object val = o.getValue();
			if (StringUtils.isMeaningFul(key) && null != val) {
				String value = ((String)val).trim();
				if (StringUtils.isMeaningFul(value)) {
					sb.append(" and ").append(alias).append(".").append(key).append(" like ?");
					params.add("%" + value + "%");
				}
			}
		}
		return sb;
	}

	public static StringBuilder andLikeBatch(StringBuilder sb, List<Object> params, String alias, Object ognlRoot, String...ognlExps) {
		if (null != ognlExps) {
			for (String columnName : ognlExps) {
				if (StringUtils.isMeaningFul(columnName)) {
					andLike(sb, params, alias, ognlRoot, columnName);
				}
			}
		}
		return sb;
	}
	
	public static StringBuilder andEqual(StringBuilder sb, List<Object> params, String alias, String columnName, Object columnValue) {
		alias = (null != alias) ? alias.trim() : "";
		if (null != columnValue) {
			if (columnValue.getClass() == String.class) {
				columnValue = ((String)columnValue).trim();
			} 
			sb.append(" and ").append(alias).append(".").append(columnName).append(" = ?");
			params.add(columnValue);
		}
		return sb;
	}
	
	public static StringBuilder andMoreThan(StringBuilder sb, List<Object> params, String alias, String columnName, Object columnValue) {
		alias = (null != alias) ? alias.trim() : "";
		if (null != columnValue) {
			if (columnValue.getClass() == String.class) {
				columnValue = ((String)columnValue).trim();
			} 
			sb.append(" and ").append(alias).append(".").append(columnName).append(" > ?");
			params.add(columnValue);
		}
		return sb;
	}
	
	public static StringBuilder andMoreThanEqual(StringBuilder sb, List<Object> params, String alias, String columnName, Object columnValue) {
		alias = (null != alias) ? alias.trim() : "";
		if (null != columnValue) {
			if (columnValue.getClass() == String.class) {
				columnValue = ((String)columnValue).trim();
			} 
			sb.append(" and ").append(alias).append(".").append(columnName).append(" >= ?");
			params.add(columnValue);
		}
		return sb;
	}
	
	public static StringBuilder andLessThan(StringBuilder sb, List<Object> params, String alias, String columnName, Object columnValue) {
		alias = (null != alias) ? alias.trim() : "";
		if (null != columnValue) {
			if (columnValue.getClass() == String.class) {
				columnValue = ((String)columnValue).trim();
			} 
			sb.append(" and ").append(alias).append(".").append(columnName).append(" < ?");
			params.add(columnValue);
		}
		return sb;
	}
	
	public static StringBuilder andLessThanEqual(StringBuilder sb, List<Object> params, String alias, String columnName, Object columnValue) {
		alias = (null != alias) ? alias.trim() : "";
		if (null != columnValue) {
			if (columnValue.getClass() == String.class) {
				columnValue = ((String)columnValue).trim();
			} 
			sb.append(" and ").append(alias).append(".").append(columnName).append(" <= ?");
			params.add(columnValue);
		}
		return sb;
	}
	
	public static StringBuilder andEqual(StringBuilder sb, List<Object> params, String alias, Object ognlRoot, String ognlExp) {
		try {
			alias = (null != alias) ? alias.trim() : "";
			Object val = Ognl.getValue(ognlExp, ognlRoot);
			andEqual(sb, params, alias, ognlExp, val);
		} catch (OgnlException o) {
			throw new IllegalStateException("没有找到" + ognlExp);
		}
		return sb;
	}
	
	public static StringBuilder andEqual(StringBuilder sb, List<Object> params, String alias, Map<String, Object> cvMap) {
		alias = (null != alias) ? alias.trim() : "";
		Set<Entry<String, Object>> set = cvMap.entrySet();
		for (Entry<String, Object> o : set) {
			String key = o.getKey();
			Object val = o.getValue();
			if (StringUtils.isMeaningFul(key) && null != val) {
				if (val.getClass() == String.class) {
					val = ((String) val).trim();
				}
				sb.append(" and ").append(alias).append(".").append(key).append(" = ?");
				params.add(val);
			}
		}
		return sb;
	}
	
	public static StringBuilder andEqualBatch(StringBuilder sb, List<Object> params, String alias, Object ognlRoot, String...ognlExps) {
		if (null != ognlExps) {
			for (String columnName : ognlExps) {
				if (StringUtils.isMeaningFul(columnName)) {
					andEqual(sb, params, alias, ognlRoot, columnName);
				}
			}
		}
		return sb;
	}
	
	public static StringBuilder andDateRange(StringBuilder sb, List<Object> params, String alias, String columnName, Object ognlRoot, String ognlExp) {
		try {
			alias = (null != alias) ? alias.trim() : "";
			
			Object startValue = Ognl.getValue(ognlExp + "Start", ognlRoot);
			if (null != startValue) {
				andMoreThan(sb, params, alias, StringUtils.notNull(columnName, ognlExp).toString(), startValue);
			}
			
			Object endValue = Ognl.getValue(ognlExp + "End", ognlRoot);
			if (null != endValue) {
				Date endDate = (Date) endValue;
				Calendar delayCalendar = Calendar.getInstance();
				delayCalendar.setTime(endDate);
				delayCalendar.add(Calendar.DAY_OF_YEAR, 1);
				andLessThan(sb, params, alias, StringUtils.notNull(columnName, ognlExp).toString(), delayCalendar.getTime());
			}
		} catch (OgnlException o) {
			throw new IllegalStateException("没有找到" + ognlExp);
		}
		return sb;
	}
	
	public static StringBuilder andDateRange(StringBuilder sb, List<Object> params, String alias, Object ognlRoot, String ognlExp) {
		return andDateRange(sb, params, alias, ognlExp, ognlRoot, ognlExp);
	}
}
