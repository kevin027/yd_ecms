package com.tools.sys;

import com.gdcalib.PSIApp;

public class PsiAppInit {

	
	private static PSIApp psij = null;
	
	
	private PsiAppInit()
	{
		
	}
	public  static PSIApp getInstance()
	{
		
		if (psij==null )
		{
			psij=new  PSIApp();
		}
		return psij;
	}
}
