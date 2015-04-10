package com.yida.file.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.yida.file.entity.FileOpConfig;

@Service
public class DefaultFileSavePathResolver {

	public Path resolve(File file, FileOpConfig config, String refId) {
		Calendar now = Calendar.getInstance();
		return Paths.get(config.getRefEntity(), String.valueOf(now.get(Calendar.YEAR))
				,String.valueOf(now.get(Calendar.MONTH)), String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
	}
	
}
