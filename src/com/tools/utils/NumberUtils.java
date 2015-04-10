package com.tools.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.IEEE754rUtils;

public class NumberUtils {
	
	private NumberUtils() {
		throw new AssertionError(getClass() + "不需要实例化!");
	}
	
	public static BigDecimal notNull(BigDecimal num) {
		return notNull(num, BigDecimal.ZERO);
	}
	
	public static BigDecimal notNull(BigDecimal num, BigDecimal candidate) {
		return StringUtils.notNull(num, candidate);
	}
	
	public static BigDecimal add(BigDecimal... ns) {
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal n : ns) {
			if (null != n) sum = sum.add(n);
		}
		return sum;
	}
	
	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		if (null == a) a = BigDecimal.ZERO;
		if (null == b) b = BigDecimal.ZERO;
		return a.subtract(b);
	}
	
	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		if (null == a) a = BigDecimal.ZERO;
		if (null == b) b = BigDecimal.ZERO;
		return a.multiply(b);
	}
	
	public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
		if (a == null || b == null || 0 == a.doubleValue() || 0 == b.doubleValue()) {
			return BigDecimal.ZERO.setScale(scale);
		}
		return a.divide(b, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		return divide(a, b, 6);
	}
	
	public static String numberFormate(Object obj,String pattern){
		try {
			if(!StringUtils.isMeaningFul(pattern))
				pattern = "###,###,###,###,###,###,##0.00";
			DecimalFormat df = new DecimalFormat(pattern);
			return df.format(obj);
		} catch (Exception e) {
			return "0.00";
		}
	}
	
	/**
	 * 将BigDecimal表示的浮点数转为人民币大写。
	 * @param n
	 * @param scale
	 * @return
	 */
	public static String toRmbUpper(BigDecimal n, int scale) {
		String[] uppers = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] ius = {"", "拾", "佰", "仟"};
		String[] ius2 = {"元", "万", "亿", "兆"};
		String[] dus = {"角", "分", "厘"};
		String[] ns = n.setScale(scale, BigDecimal.ROUND_HALF_UP).toString().split("\\.");
		
		StringBuilder r = new StringBuilder();
		
		// 整数部分，按4位为一组分组处理。
		String[] gs = ns[0].split("(?<=^\\d+)(?=(\\d{4})+$)");
		for (int gi = 0; gi < gs.length; gi++) {
			// 组信息
			String g = gs[gs.length - gi - 1];
			
			// 用于保存分组处理的最终结果。
			StringBuilder ng = new StringBuilder();
			
			// 将小写的序号对应上大写数组的序号，获取对应的大写值。
			int lastIndex = 0; // 用于记录上次处理的结果来过滤连续的0
			for (int i = 0; i < g.length(); i++) {
				int index = Integer.parseInt(Character.toString(g.charAt(g.length() - 1 - i)));
				if (0 != index) {
					ng.append(ius[i]).append(uppers[index]);
				} else {
					if (0 != lastIndex) ng.append(uppers[index]);
				}
				lastIndex = index;
			}
			
			// 清除尾部的0。
			String seg = ng.reverse().toString().replaceAll(uppers[0] + "$", "");
			r.insert(0, seg + (seg.equals("") ? uppers[0] : ius2[gi]));
		}
		
		// 浮点数部分
		if (1 < ns.length && 0 < scale) {
			String fp = ns[1].replaceAll("0+$", "");
			int count = Math.min(fp.length(), scale);
			if (0 < count) {
				for (int j = 0; j < count; j++) {
					int index = Integer.parseInt(Character.toString(fp.charAt(j)));
					r.append(uppers[index]);
					if (0 != index) {
						r.append(dus[j]);
					}
				}
				return r.toString().replaceAll(uppers[0] + "+", uppers[0]);
			}
		}
		return r.toString().replaceAll(uppers[0] + "+", uppers[0]) + "整";
	}
	
	public static String toRmbUpper(BigDecimal n) {
		return toRmbUpper(n, 2);
	}
	
	public static boolean isEqual(BigDecimal v1,BigDecimal v2){
		if(v1==null || v2==null) return false;
		return v1.doubleValue()==v2.doubleValue();
	}
	
	/*-------数字类型转换-------------------*/
	public static int stringToInteger(String value){
		try {
			if(StringUtils.isMeaningFul(value)){
				return Integer.parseInt(value);
			}else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static double stringToDouble(String value){
		try {
			if(StringUtils.isMeaningFul(value)){
				return Double.parseDouble(value);
			}else {
				return 0d;
			}
		} catch (Exception e) {
			return 0d;
		}
	}
	
	public static boolean stringToBoolean(String value){
		try {
			if(StringUtils.isMeaningFul(value)){
				if("1".equals(value)) return true; 
				return Boolean.parseBoolean(value);
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 最大值
     * <p>Returns the maximum value in an array.</p>
     * 
     * @param array  an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     * @see IEEE754rUtils#max(double[]) IEEE754rUtils for a version of this method that handles NaN differently
     */
    public static double max(double[] array) {
        // Validates input
        if (array== null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    
        // Finds and returns max
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Double.isNaN(array[j])) {
                return Double.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }
    
        return max;
    }
    
    /**
     * 最小值
     * <p>Returns the minimum value in an array.</p>
     * 
     * @param array  an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     * @see IEEE754rUtils#min(double[]) IEEE754rUtils for a version of this method that handles NaN differently
     */
    public static double min(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    
        // Finds and returns min
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
    
        return min;
    }
	
    /**
     * map到list
     * @param map
     * @return
     */
	public static List<?> mapTransitionList(Map map) {
		List list = new ArrayList();
		Iterator iter = map.entrySet().iterator();
		// 获得map的Iterator
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			list.add(entry.getValue());
		}
		return list;
	}
}
