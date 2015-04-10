package com.yida.core.webxml.listener;

import java.util.Collection;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.sys.OnlineAccountSet;
import com.tools.sys.SysConstant;
import com.yida.core.base.entity.Account;

@WebListener
public class SessionAttributeListenerHandle implements HttpSessionAttributeListener {
	
	protected Logger debugLog = LoggerFactory.getLogger(SessionAttributeListenerHandle.class);
	
	private final String attributeAddedInfo = "session属性添加操作，添加属性%1$s，添加属性值为：%2$s。";
	private final String attributeRemovedInfo = "session属性移除操作，移除属性%1$s，移除属性值为：%2$s。";
	private final String attributeReplacedInfo = "session属性替换操作，替换属性%1$s，替换前为：%2$s，替换后为：%3$s。";

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String key = event.getName(); // 添加的属性
		Object val = event.getSession().getAttribute(event.getName()); // 添加的属性值
		debugLog.debug(String.format(attributeAddedInfo, key, val.toString()));
		try {
			if (SysConstant.LOGIN_ACCOUNT.equals(key) && val instanceof Account) {
				Account user = Account.class.cast(val);
				OnlineAccountSet.INSTANCE.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String key = event.getName(); // 移除的属性
		Object val = event.getValue(); // 移除的属性值
		debugLog.debug(String.format(attributeRemovedInfo, key, val.toString()));
		try {
			if (SysConstant.LOGIN_ACCOUNT.equals(key) && val instanceof Account) {
				OnlineAccountSet.INSTANCE.remove(Account.class.cast(val));
				forwardLoginPage(event.getSession()); // 如果移除的是登录标识，就通知该会话的页面。跳到登录页面。
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		String key = event.getName(); // 被替换的属性
		Object val = event.getValue(); // 被替换的属性值
		Object rep = event.getSession().getAttribute(event.getName()); // 替换后的属性值
		debugLog.debug(String.format(attributeReplacedInfo, key, val.toString(), rep.toString()));
		try {
			if (SysConstant.LOGIN_ACCOUNT.equals(key) && val instanceof Account) {
				OnlineAccountSet.INSTANCE.remove(Account.class.cast(val));
				Account user = Account.class.cast(rep);
				OnlineAccountSet.INSTANCE.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void forwardLoginPage(final HttpSession httpSession) {
		Browser.withAllSessionsFiltered(new ScriptSessionFilter(){
			@Override
			public boolean match(ScriptSession scriptSession) {
				return httpSession.getId().equals(scriptSession.getAttribute(SysConstant.HTTP_SESSION_ID));
			}
		}, new Runnable(){
			@Override
			public void run() {
				Collection<ScriptSession> colls = Browser.getTargetSessions();
				for (ScriptSession ss : colls) {
					ss.addScript(new ScriptBuffer("alert('ok')"));
					break;
				}
			}
		});
	}
}
