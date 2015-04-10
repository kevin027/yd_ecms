package com.tools.sys;

import org.apache.log4j.Logger;

public class ConfigLoader 
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ConfigLoader.class);
	
	public static void startSystemglobals(String appPath)
	{
		SystemGlobals.initGlobals(appPath, appPath + "/WEB-INF/config/SystemGlobals.properties");
	}
	public static void listenForChanges()
	{
		int fileChangesDelay = SystemGlobals.getIntValue(ConfigKeys.FILECHANGES_DELAY);
		if (fileChangesDelay > 0) {
			// System Properties
			FileMonitor.getInstance().addFileChangeListener(new SystemGlobalsListener(),
				SystemGlobals.getValue(ConfigKeys.DEFAULT_CONFIG), fileChangesDelay);

        }
	}
}
