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
 * Created on May 29, 2004 by pieter
 * The JForum Project
 * http://www.jforum.net
 */
package com.tools.sys;

/**
 * @author Pieter Olivier
 * @version $Id: ConfigKeys.java,v 1.120 2007/09/21 15:54:30 rafaelsteil Exp $
 */

public class ConfigKeys {
	private ConfigKeys() {
	}

	/**
	 * 项目部署的路径
	 */
	public static final String APPLICATION_PATH = "application.path";
	/**
	 * 属性文件的路径
	 */
	public static final String DEFAULT_CONFIG = "default.config";
	/**
	 * 检查属性文件是否改变的时间间隔
	 */
	public static final String FILECHANGES_DELAY = "file.changes.delay";
	/**
	 * 系统上传文件路径
	 */
	public static final String UPLOAD_PATH = "file.uploadpath";
	/**
	 * 项目附件上传路径
	 */
	public static final String UPLOAD_ENTERPRISE = "file.enterprise";

	/**
	 * 默认密码
	 */
	public static final String DEFAULTPASSWORD = "defaultpassword";

	/**
	 * 是否使用KEY登录
	 */
	public static final String LOGIN_USE_KEY = "login.useKey";

}
