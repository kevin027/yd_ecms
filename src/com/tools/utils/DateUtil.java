package com.tools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @作者: kevin
 * @描述：日期转换工具类
 * @时间: Dec 18, 2014
 */
public class DateUtil {
	public static SimpleDateFormat formater = new SimpleDateFormat();
	
	public static final String Y = "yyyy";
	public static final String YM = "yyyy-MM";
	public static final String YMD = "yyyy-MM-dd";
	public static final String yyMMdd = "yy-MM-dd";
	public static final String STRING_YMD = "yyyyMMdd";
	public static final String yyyyMMdd2 = "yyyy/MM/dd";
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YMDHM = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_ZERO = "yyyyMMddHHmmss";
	public static final String yyMMddHHmmss = "yy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT = YMD + " HH:mm:ss";
	public static final String HHmm = "HH:mm";
	public static final String HHmmss = "HH:mm:ss";
	public static final String YMDCN = "yyyy年MM月dd日";
	public static final String YMDHMSCN = "yyyy年MM月dd日 HH时mm分ss秒";

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 Y="yyyy"
	 */
	public static String formatY(Date date) {
		return format(date, Y);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 YM = "yyyy-MM"
	 */
	public static String formatYM(Date date) {
		return format(date, YM);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 YMD = "yyyy-MM-dd"
	 */
	public static String formatYMD(Date date) {
		return format(date, YMD);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @描述 yyMMdd = "yy-MM-dd"
	 */
	public static String formatyyMMdd(Date date) {
		return format(date, yyMMdd);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @描述 yyyyMMdd2 = "yyyy/MM/dd"
	 */
	public static String formatyyyyMMdd2(Date date) {
		return format(date, yyyyMMdd2);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 YMDCN = "yyyy年MM月dd日"
	 */
	public static String formatYMDCN(Date date) {
		return format(date, YMDCN);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 YMDHMSCN = "yyyy年MM月dd日 HH时mm分ss秒"
	 */
	public static String formatYMDHMSCN(Date date) {
		return format(date, YMDHMSCN);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 YMDHM = "yyyy-MM-dd HH:mm"
	 */
	public static String formatYMDHM(Date date) {
		return format(date, YMDHM);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 YMDHMS = "yyyy-MM-dd HH:mm:ss"
	 */
	public static String formatYMDHMS(Date date) {
		return format(date, YMDHMS);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @描述 yyMMddHHmmss = "yy-MM-dd HH:mm:ss"
	 */
	public static String formatyyMMddHHmmss(Date date) {
		return format(date, yyMMddHHmmss);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @描述 HHmm = "HH:mm"
	 */
	public static String formatHHmm(Date date) {
		return format(date, HHmm);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @描述 HHmmss = "HH:mm:ss"
	 */
	public static String formatHHmmss(Date date) {
		return format(date, HHmmss);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @描述 STRING_YMD = "yyyyMMdd"
	 */
	public static String formatStringYMD(Date date) {
		return format(date, STRING_YMD);
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			formater.applyPattern(pattern);
			return formater.format(date);
		}
	}

	public static Date parseYMD(String strDate) {
		return parse(strDate, YMD);
	}

	public static Date parseYMDHM(String strDate) {
		return parse(strDate, YMDHM);
	}

	public static Date parseYMDHMS(String strDate) {
		return parse(strDate, YMDHMS);
	}

	public static Date parse(String strDate, String pattern) {
		if (strDate == null || strDate.trim().length() == 0) {
			return null;
		} else {
			formater.applyPattern(pattern);
			try {
				return formater.parse(strDate);
			} catch (ParseException e) {
				System.err.println("错误：时间转换出错");
				return null;
			}
		}
	}

	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	public static Integer getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static Date convertSampleStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			aDate = convertStringToDate(DATE_FORMAT_ZERO, strDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		// System.out.println(aDate.toLocaleString());
		return aDate;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param aMask
	 *            输入字符串的格式
	 * @param strDate
	 *            一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		System.out.println("strDate==" + strDate);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	public static String date2String(Date d) {
		long l = d.getTime();
		try {
			return long2String(l);
		} catch (Exception e) {
			return "";
		}

	}

	public static String long2String(long l) {
		Date aDate = new Date(l);
		SimpleDateFormat df = new SimpleDateFormat(DATETIME_FORMAT);

		return df.format(aDate);
	}

	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author 覃业欣
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " 天 " + hours + " 小时 " + minutes + " 分钟 " + seconds
				+ " 妙 ";
	}

	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes 后的格式
	 * @author 覃业欣
	 */
	public static String formatDuringToMinutes(long mss) {
		String d = "";
		if (mss > 0) {
			long days = mss / (1000 * 60 * 60 * 24);
			long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
			d = (days == 0 ? "" : (days + "天"))
					+ (hours == 0 ? "" : (hours + "小时"))
					+ (minutes == 0 ? "" : (minutes + "分钟"));
		} else {
			d = "超时";
		}
		return d;
	}

	/**
	 * 
	 * @param begin
	 *            时间段的开始
	 * @param end
	 *            时间段的结束
	 * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author 覃业欣
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}

	/**
	 * 
	 * @param begin
	 *            时间段的开始
	 * @param end
	 *            时间段的结束
	 * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes的格式展示
	 * @author 覃业欣
	 */
	public static String formatDuringToMinutes(Date begin, Date end) {
		return formatDuringToMinutes(end.getTime() - begin.getTime());
	}

	public static Date parseToDate(String s, String style) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(style);
		Date date = null;
		if (s == null || s.length() < 8)
			return null;
		try {
			date = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String parseToString(String s, String style) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(style);
		Date date = null;
		String str = null;
		if (s == null || s.length() < 8)
			return null;
		try {
			date = simpleDateFormat.parse(s);
			str = simpleDateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String parseToString(Date date, String style) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(style);
		String str = null;
		if (date == null)
			return null;
		str = simpleDateFormat.format(date);
		return str;
	}

	public static boolean checkDateFromStr(String s, String style) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(style);
		Date date = null;
		String str = null;
		boolean flag = false;
		if (s != null && !"".equals(s) && s.length() > 8) {
			try {
				date = simpleDateFormat.parse(s);
				str = simpleDateFormat.format(date);
				System.out.println(str);
				if (date.getYear() > 0) {
					flag = true;
				} else {
					flag = false;
				}
				flag = true;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * Getdate function
	 * 
	 * @param month
	 * @return void
	 */
	public String Getdate(int month) {
		java.util.Calendar cur_date = Calendar.getInstance();
		cur_date.roll(java.util.Calendar.MONTH, month);
		int yyyy = cur_date.get(Calendar.YEAR);
		int mm = cur_date.get(Calendar.MONTH) + 1;
		int dd = cur_date.get(Calendar.DATE);
		cur_date = null;
		return (String.valueOf(yyyy) + (mm < 10 ? "0" : "")
				+ String.valueOf(mm) + (dd < 10 ? "0" : "") + String
					.valueOf(dd));
	}

	public static String getDateStr(char c, int month) {
		java.util.Calendar cur_date = Calendar.getInstance();
		if (c == 'Y' || c == 'y') {
			cur_date.add(java.util.Calendar.YEAR, month);
		} else if (c == 'M' || c == 'm') {
			cur_date.add(java.util.Calendar.MONTH, month);
		} else if (c == 'D' || c == 'd') {
			cur_date.add(java.util.Calendar.DATE, month);
		}
		int yyyy = cur_date.get(Calendar.YEAR);
		int mm = cur_date.get(Calendar.MONTH) + 1;
		int dd = cur_date.get(Calendar.DATE);
		cur_date = null;
		return (String.valueOf(yyyy) + "-" + (mm < 10 ? "0" : "")
				+ String.valueOf(mm) + "-" + (dd < 10 ? "0" : "") + String
					.valueOf(dd));
	}
	
	public static String getBig(String demo) {
		String result = "";
		String[] temp = demo.split("-");
		for (int i = 0; i < temp[0].length(); i++) {
			if (temp[0].charAt(i) == '1') {
				result = result + "一";
			} else if (temp[0].charAt(i) == '2') {
				result = result + "二";
			} else if (temp[0].charAt(i) == '3') {
				result = result + "三";
			} else if (temp[0].charAt(i) == '4') {
				result = result + "四";
			} else if (temp[0].charAt(i) == '5') {
				result = result + "五";
			} else if (temp[0].charAt(i) == '6') {
				result = result + "六";
			} else if (temp[0].charAt(i) == '7') {
				result = result + "七";
			} else if (temp[0].charAt(i) == '8') {
				result = result + "八";
			} else if (temp[0].charAt(i) == '9') {
				result = result + "九";
			} else if (temp[0].charAt(i) == '0') {
				result = result + "零";
			}
		}
		result = result + "年";
		if (temp[1].equals("01")) {
			result = result + "一月";
		} else if (temp[1].equals("02")) {
			result = result + "二月";
		} else if (temp[1].equals("03")) {
			result = result + "三月";
		} else if (temp[1].equals("04")) {
			result = result + "四月";
		} else if (temp[1].equals("05")) {
			result = result + "五月";
		} else if (temp[1].equals("06")) {
			result = result + "六月";
		} else if (temp[1].equals("07")) {
			result = result + "七月";
		} else if (temp[1].equals("08")) {
			result = result + "八月";
		} else if (temp[1].equals("09")) {
			result = result + "九月";
		} else if (temp[1].equals("10")) {
			result = result + "十月";
		} else if (temp[1].equals("11")) {
			result = result + "十一月";
		} else if (temp[1].equals("12")) {
			result = result + "十二月";
		}
		if (temp[2].equals("01")) {
			result = result + "一日";
		} else if (temp[2].equals("02")) {
			result = result + "二日";
		} else if (temp[2].equals("03")) {
			result = result + "三日";
		} else if (temp[2].equals("04")) {
			result = result + "四日";
		} else if (temp[2].equals("05")) {
			result = result + "五日";
		} else if (temp[2].equals("06")) {
			result = result + "六日";
		} else if (temp[2].equals("07")) {
			result = result + "七日";
		} else if (temp[2].equals("08")) {
			result = result + "八日";
		} else if (temp[2].equals("09")) {
			result = result + "九日";
		} else if (temp[2].equals("10")) {
			result = result + "十日";
		} else if (temp[2].equals("11")) {
			result = result + "十一日";
		} else if (temp[2].equals("12")) {
			result = result + "十二日";
		} else if (temp[2].equals("13")) {
			result = result + "十三日";
		} else if (temp[2].equals("14")) {
			result = result + "十四日";
		} else if (temp[2].equals("15")) {
			result = result + "十五日";
		} else if (temp[2].equals("16")) {
			result = result + "十六日";
		} else if (temp[2].equals("17")) {
			result = result + "十七日";
		} else if (temp[2].equals("18")) {
			result = result + "十八日";
		} else if (temp[2].equals("19")) {
			result = result + "十九日";
		} else if (temp[2].equals("20")) {
			result = result + "二十日";
		} else if (temp[2].equals("21")) {
			result = result + "二十一日";
		} else if (temp[2].equals("22")) {
			result = result + "二十二日";
		} else if (temp[2].equals("23")) {
			result = result + "二十三日";
		} else if (temp[2].equals("24")) {
			result = result + "二十四日";
		} else if (temp[2].equals("25")) {
			result = result + "二十五日";
		} else if (temp[2].equals("26")) {
			result = result + "二十六日";
		} else if (temp[2].equals("27")) {
			result = result + "二十七日";
		} else if (temp[2].equals("28")) {
			result = result + "二十八日";
		} else if (temp[2].equals("29")) {
			result = result + "二十九日";
		} else if (temp[2].equals("30")) {
			result = result + "三十日";
		} else if (temp[2].equals("31")) {
			result = result + "三十一日";
		}
		return result;
	}
}
