package com.yida.core.webxml.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SessionListenerHandle implements HttpSessionListener {

	protected Logger debugLog = LoggerFactory.getLogger(SessionListenerHandle.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		debugLog.debug("HttpSession created:id=" + session.getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		debugLog.debug("HttpSession destoryed:id=" + session.getId());
	}

}
