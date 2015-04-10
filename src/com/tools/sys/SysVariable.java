package com.tools.sys;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.yida.core.common.ClassPathWatch;
import com.yida.core.common.ClassPathWatchListener;

public enum SysVariable {
	
	INSTANCE;
	
	private Properties prop = new Properties();
	private Path propPath = Paths.get("base.properties");
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	private SysVariable() {
		try {
			ClassPathWatch watch = ClassPathWatch.INSTANCE;
			watch.register(propPath, StandardWatchEventKinds.ENTRY_MODIFY, new ClassPathWatchListener() {
				@Override
				public void performance(WatchEvent<Path> event) {
					SysVariable.INSTANCE.loadConfigProp();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadConfigProp();
	}
	
	public String getValue(String key) {
		lock.readLock().lock();
		try {
			return prop.getProperty(key);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	private void loadConfigProp() {
		Properties newProp = new Properties();
		try (InputStream is = Files.newInputStream(ClassPathWatch.INSTANCE.getClassPath().resolve(propPath))){
			newProp.load(is);
			lock.writeLock().lock();
			try {
				prop = newProp;
			} finally {
				lock.writeLock().unlock();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
