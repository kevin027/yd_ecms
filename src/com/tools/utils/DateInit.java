package com.tools.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateInit {

	private String dirpath = "C:/";

	public DateInit() {
		init();
	}
	
	private void init() {
		StringBuffer sb = new StringBuffer(getClass().getResource("/_logs/readme.txt").getFile());
		sb.delete(0, 1);
		sb.delete(sb.indexOf("readme.txt"), sb.length());
		dirpath = sb.toString();
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile(("[0-9]"));
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * log function
	 * 
	 */
	public boolean AddLog(String type, String action, String status,
			String title, String detail) {
		return true;
	}

	/**
	 * createRandomStr function
	 * 
	 * @return RandomString
	 */

	public String createRandomStr() {

		char[] randomChar = new char[8];
		for (int i = 0; i < 8; i++) {
			randomChar[i] = (char) (Math.random() * 26 + 'a');
		}
		String replaceStr = new String(randomChar);
		return replaceStr;
	}

	public void AddLog(String sMsg) {
		String sFileName = "";
		String sDate = "";
		String sTime = "";
		String sMessage = "";

		if (sMsg.equals("")) {
			return;
		}

		java.util.Calendar cur_date = Calendar.getInstance();
		int iYear = cur_date.get(Calendar.YEAR);
		int iMonth = cur_date.get(Calendar.MONTH) + 1;
		int iDay = cur_date.get(Calendar.DATE);
		int iHour = cur_date.get(Calendar.HOUR_OF_DAY);
		int iMinute = cur_date.get(Calendar.MINUTE);
		int iSecond = cur_date.get(Calendar.SECOND);
		cur_date = null;

		String sYear = Integer.toString(iYear);
		String sMonth = (iMonth < 10) ? ("0" + Integer.toString(iMonth))
				: Integer.toString(iMonth);
		String sDay = (iDay < 10) ? ("0" + Integer.toString(iDay)) : Integer
				.toString(iDay);
		String sHour = (iHour < 10) ? ("0" + Integer.toString(iHour)) : Integer
				.toString(iHour);
		String sMinute = (iMinute < 10) ? ("0" + Integer.toString(iMinute))
				: Integer.toString(iMinute);
		String sSecond = (iSecond < 10) ? ("0" + Integer.toString(iSecond))
				: Integer.toString(iSecond);

		sDate = sYear + "/" + sMonth + "/" + sDay;
		sTime = sHour + ":" + sMinute + ":" + sSecond;
		sFileName = dirpath + "report_" + sYear + sMonth + sDay + "_Log.log";
		sMessage = "[" + sDate + " " + sTime + "] : " + sMsg;

		PrintWriter log;
		try {
			log = new PrintWriter(new FileWriter(sFileName, true), true);
			log.println(sMessage + "\r"); // auto enter; log.print can't auto
											// enter
			log.close();
		} catch (IOException e) {
			System.err.println("Can't not open file: " + sFileName);
		}
		return;
	}

	/**
	 * ErrLog function
	 * 
	 * @param sMsg
	 */
	public void ErrLog(String sMsg) {
		String sFileName = "";
		String sDate = "";
		String sTime = "";
		String sMessage = "";

		if (sMsg.equals("")) {
			return;
		}

		java.util.Calendar cur_date = Calendar.getInstance();
		int iYear = cur_date.get(Calendar.YEAR);
		int iMonth = cur_date.get(Calendar.MONTH) + 1;
		int iDay = cur_date.get(Calendar.DATE);
		int iHour = cur_date.get(Calendar.HOUR_OF_DAY);
		int iMinute = cur_date.get(Calendar.MINUTE);
		int iSecond = cur_date.get(Calendar.SECOND);
		cur_date = null;

		String sYear = Integer.toString(iYear);
		String sMonth = (iMonth < 10) ? ("0" + Integer.toString(iMonth))
				: Integer.toString(iMonth);
		String sDay = (iDay < 10) ? ("0" + Integer.toString(iDay)) : Integer
				.toString(iDay);
		String sHour = (iHour < 10) ? ("0" + Integer.toString(iHour)) : Integer
				.toString(iHour);
		String sMinute = (iMinute < 10) ? ("0" + Integer.toString(iMinute))
				: Integer.toString(iMinute);
		String sSecond = (iSecond < 10) ? ("0" + Integer.toString(iSecond))
				: Integer.toString(iSecond);

		sDate = sYear + "/" + sMonth + "/" + sDay;
		sTime = sHour + ":" + sMinute + ":" + sSecond;
		sFileName = dirpath + "report_" + sYear + sMonth + sDay + "_Log.log";
		sMessage = "[" + sDate + " " + sTime + "] : " + sMsg;

		PrintWriter log;
		try {
			log = new PrintWriter(new FileWriter(sFileName, true), true);
			log.println(sMessage + "\r"); // auto enter; log.print can't auto
											// enter
			log.close();
		} catch (IOException e) {
			System.err.println("Can't not open file: " + sFileName);
		}
		return;
	}

	public static boolean isDigit(String s) {
		if (s == null || s.length() < 1) {
			return false;
		}
		boolean sign = true;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < '0' || s.charAt(i) > '9') {
				sign = false;
				break;
			}
		}
		return sign;
	}

	public boolean isValida(String s) {
		if (s == null || s.length() < 1) {
			return false;
		}
		// /?><,!~|+=^&!
		char[] temp = { '\'', '\"' };
		boolean sign = true;
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < temp.length; j++) {
				if (s.charAt(i) == temp[j]) {
					sign = false;
					break;
				}
			}

		}
		return sign;
	}

	public String formatString(String s, int last, char ch) {
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		int slen = sb.length();
		if (slen > last) {
			for (int i = 0; i < last; i++) {
				sb.setCharAt(slen - last + i, ch);
			}
		}
		return sb.toString();
	}

	public String breakString(String s, int hold, char ch, int j) {
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		int slen = sb.length();
		if (slen > hold) {
			sb.delete(hold, slen);
			for (int i = 0; i < j; i++) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

}
