package com.tools.utils;

import java.io.UnsupportedEncodingException;

import net.sourceforge.pinyin4j.PinyinHelper;


public class ToolUtils {

    public String IOS(String s) {
      if (s == null || s.length() < 0) {
        return null;
      }
      try {
        s =
            new String(s.trim().getBytes("ISO8859_1"));
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return null;
      }
      return s;
    }

    public String GBK(String s) {
      if (s == null || s.length() < 0) {
        return null;
      }
      try {
        s =
            new String(s.trim().getBytes("GBK"));
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return null;
      }
      return s;
    }

    public String gbToIso(String s) {
      if (s == null || s.length() < 0) {
        return null;
      }
      try {
        s = new String(s.getBytes("GBK"), "ISO8859-1");
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return null;
      }
      return s;
    }

    public String isoToGb(String s) {
      if (s == null || s.length() < 0) {
        return null;
      }
      try {
        s = new String(s.getBytes("ISO8859-1"), "GBK");
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return null;
      }
      return s;
    }

    public String utfToGBK(String s) {
      if (s == null || s.length() < 0) {
        return null;
      }
      try {
        s = new String(s.getBytes("UTF-8"), "GBK");
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return null;
      }
      return s;
    }

    public String isoToUtf(String s) {
      if (s == null || s.length() < 0) {
        return null;
      }
      try {
        s = new String(s.getBytes("ISO8859-1"), "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return null;
      }
      return s;
    }
    
    /**
	 * 获取中文字符的拼音的第一个字母
	 * @author kevin
	 *       -----     2011-03-10
	 */
	public static String getPinYinHeadChar(String str) {  

	    String convert = "";  
	    for (int j = 0; j < str.length(); j++) {  
	      char word = str.charAt(j);  
	      String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	      if (pinyinArray != null) {  
	    	  convert += pinyinArray[0].charAt(0);  
	      }else {  
	    	  convert += word;  
	      }  
	    }  
	    return convert;  
	}
}
