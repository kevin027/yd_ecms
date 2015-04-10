package com.tools.utils;

import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * @author kevin
 */
public class ByteUtil
{
	private static int reverseBytes(Integer source)
	{
		if(source==null)
			source=0;
		return Integer.reverseBytes(source);
	}
	private static long reverseBytes(Double source)
	{
		if(source==null)
			source=0.0d;
		return Long.reverseBytes(Double.doubleToLongBits(source));
	}
	public static void writeStr(DataOutputStream dop,String value)
	{
		try
		{
			if(value==null||value.trim().length()==0)
			{
				dop.writeInt(reverseBytes(0));
			}else
			{
				dop.writeInt(reverseBytes(value.getBytes().length));
				dop.write(value.getBytes());
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void writeDouble(DataOutputStream dop,Double value)
	{
		try
		{
			dop.writeLong(reverseBytes(value));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void writeInt(DataOutputStream dop,Integer value)
	{
		try
		{
			dop.writeInt(reverseBytes(value));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void writeBoolean(DataOutputStream dop,boolean value)
	{
		try
		{
			dop.writeBoolean(value);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("resource")
	public static byte[] constructCostInfo(Double total,Double price,Double labor,Double materail,Double machine,Double mainMaterialRate,Double equipmentRate,Double overheadRate,Double profits,Double tax,byte[] analy)
	{
		ByteOutputStream bos=new ByteOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		try
		{
			dos.writeInt(reverseBytes(0));
			dos.writeInt(reverseBytes(0));
			
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));

			dos.writeLong(reverseBytes(labor));
			dos.writeLong(reverseBytes(materail));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(machine));

			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));

			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));
			if(mainMaterialRate==null)
				mainMaterialRate=0d;
			if(equipmentRate==null)
				equipmentRate=0d;
			dos.writeLong(reverseBytes(mainMaterialRate+equipmentRate));
			dos.writeLong(reverseBytes(mainMaterialRate));
			dos.writeLong(reverseBytes(equipmentRate));

			dos.writeLong(reverseBytes(0.0));
			dos.writeLong(reverseBytes(0.0));

			dos.writeLong(reverseBytes(0.0));
			
			dos.writeLong(reverseBytes(overheadRate));
			dos.writeLong(reverseBytes(profits));
			dos.writeLong(reverseBytes(tax));

			dos.writeLong(reverseBytes(price));
			dos.writeLong(reverseBytes(total));
			if(analy!=null)
				dos.write(analy);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return bos.getBytes();
	}
}
