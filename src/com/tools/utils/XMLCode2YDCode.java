package com.tools.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin
 */
public class XMLCode2YDCode
{
	private static Map<String,String> map=new HashMap<String, String>();
	private static Map<Integer,String> specType=new HashMap<Integer, String>();
	static
	{
		map.put("AFWSCXMF", "AQFHWMSG");
		// map.put("CLF","CLF");
		map.put("CCLF", "JSCL");
		map.put("CGLF", "JSGL");
		map.put("CHJ", "QSF");
		map.put("CJXF", "JSJX");
		map.put("CLR", "JSLR");
		map.put("CRGF", "JSRG");
		map.put("CSBF", "JSSBF"); // 措施设备费 JSSBF
		map.put("CZCF", "JSZCF"); // 措施主材费 JSZCF
		// map.put("DLF","DLF"); //独立费
		map.put("FCLF", "QCL");
		map.put("FDLF", "NDLF"); // 分部分项独立费
		map.put("FGLF", "QGL");
		map.put("FHJ", "QDF");
		map.put("FJXF", "QJX");
		map.put("FJGF", "QJGC"); // 分部分项甲供费 QJGC
		map.put("FLR", "QLR");
		map.put("FRGF", "QRG");
		map.put("FSBF", "QSBF"); // 分部分项设备费 QSBF
		// map.put("FYGF","缺少"); //分部分项乙供费
		map.put("FZJF", "ZMF");
		map.put("FZCF", "QZCF"); // 分部分项主材费 QZCF
		// map.put("GLF","GLF");
		// map.put("GF","GF");
		map.put("JXF", "JXF");
		// map.put("JSCSF","缺少"); //技术措施费
		map.put("JGCLF", "JGC");
		// map.put("LR","LR");
		map.put("JRG", "LXF");
		map.put("QTCSF", "QTCSF");
		map.put("QTXM", "QTF");
		// map.put("RGF","RGF");
		// map.put("SBF","SBF"); //设备费
		// map.put("SJ","SJ");
		// map.put("ZCF","ZCF"); //主材费
		// map.put("ZZJ","ZZJ");
		map.put("CLZGJ", "ZGC");
		map.put("ZYGCZGJ", "ZGGC");
		map.put("ZCBFWF", "ZBF");
		map.put("FZGJ", "QZGJ");
		map.put("ZLJE", "ZLF");
		specType.put(1, "建筑");
		specType.put(2, "装饰");
		specType.put(3, "安装");
		specType.put(4, "市政");
		specType.put(5, "园林绿化");
		specType.put(6, "公路");
		specType.put(7, "电力");
		specType.put(8, "地铁");
		specType.put(9, "房屋修缮");
		specType.put(10, "环卫");
		specType.put(11, "水利");
		specType.put(12, "港口");
		specType.put(13, "冶金");

	}
	public static String getCode(String xmlCode)
	{
		if(xmlCode==null||xmlCode.trim().length()==0)
		{
			return "";
		}else
		{
			String ydCode=map.get(xmlCode.trim());
			if(ydCode==null)
			{
				return xmlCode.trim();
			}else
			{
				return ydCode;
			}
		}
	}
	public static String getSpec(Integer index)
	{
		if(index==null)
		{
			return "";
		}else
		{
			String ydCode=map.get(index);
			if(ydCode==null)
			{
				return "";
			}else
			{
				return ydCode;
			}
		}
	}
	
	public static String getBase(String xmlBase)
	{
		if(xmlBase==null||xmlBase.trim().length()==0)
		{
			return "";
		}else
		{
			String ydBase="";
			String[] bases=xmlBase.split("\\+");
			for (String str : bases)
			{
				if(ydBase=="")
				{
					ydBase=getCode(str);
				}else
				{
					ydBase=ydBase+"+"+getCode(str);
				}
			}
			return ydBase;
		}
	}
}
