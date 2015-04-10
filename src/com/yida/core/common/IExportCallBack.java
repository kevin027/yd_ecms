package com.yida.core.common;

import org.apache.poi.ss.usermodel.Workbook;

public interface IExportCallBack {
	
	public void handle(Workbook workbook);
}