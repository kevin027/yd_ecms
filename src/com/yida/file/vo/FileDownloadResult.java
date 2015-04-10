package com.yida.file.vo;

import java.io.FileInputStream;

public class FileDownloadResult {
	
	private FileInputStream downloadFile;
	private String downloadFileName;
	
	public FileInputStream getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(FileInputStream downloadFile) {
		this.downloadFile = downloadFile;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
}
