/*
 * Copyright (c) JForum Team
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:
 * 
 * 1) Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the 
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the 
 * above copyright notice, this list of conditions and 
 * the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor 
 * the names of its contributors may be used to endorse 
 * or promote products derived from this software without 
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT 
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 * 
 * This file creating date: Feb 24, 2003 / 8:25:35 PM
 * The JForum Project
 * http://www.jforum.net
 */
package com.tools.sys;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Properties;

public class SystemGlobals implements VariableStore {
	private static SystemGlobals globals = new SystemGlobals();
	private String defaultConfig;
	private Properties defaults = new Properties();
	//private Map objectProperties = new HashMap();
	private VariableExpander expander = new VariableExpander(this, "${", "}");
	//private static final Logger logger = Logger.getLogger(SystemGlobals.class);

	private SystemGlobals() {
	}

	public static void initGlobals(String appPath, String mainConfigurationFile) {
		globals = new SystemGlobals();
		globals.buildSystem(appPath, mainConfigurationFile);
	}

	public static void reset() {
		globals.defaults.clear();
	}

	private void buildSystem(String appPath, String mainConfigurationFile) {
		if (mainConfigurationFile == null) {
			throw new InvalidParameterException(
					"defaultConfig could not be null");
		}
		this.defaultConfig = mainConfigurationFile;
		this.defaults = new Properties();
		this.defaults.put(ConfigKeys.APPLICATION_PATH, appPath);
		this.defaults.put(ConfigKeys.DEFAULT_CONFIG, mainConfigurationFile);
		SystemGlobals.loadDefaults();
	}


	public static void loadDefaults() {
		try {
			FileInputStream input = new FileInputStream(globals.defaultConfig);
			globals.defaults.load(input);
			input.close();
			globals.expander.clearCache(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String field) {
		return globals.getVariableValue(field);
	}

	public static int getIntValue(String field) {
		return Integer.parseInt(getValue(field));
	}

	public static boolean getBoolValue(String field) {
		return "true".equals(getValue(field));
	}

	public static String getApplicationPath() {
		return getValue(ConfigKeys.APPLICATION_PATH);
	}

	public static Iterator<Object> fetchConfigKeyIterator() {
		return globals.defaults.keySet().iterator();
	}

	public static Properties getConfigData() {
		return new Properties(globals.defaults);
	}

	@Override
	public String getVariableValue(String field) {
		String preExpansion = this.defaults.getProperty(field);
		if (preExpansion == null) {
			return null;
		}
		return expander.expandVariables(preExpansion);
	}
}