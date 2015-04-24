package com.yida.desktop.controller;

import com.yida.core.base.controller.BaseController;
import com.yida.desktop.entity.DevMark;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统桌面
 * @author kevin
 */
@Controller
@RequestMapping("desktop")
public class DesktopController extends BaseController {
	
	@RequestMapping("main")
	public String main() {
		return "desktop/jsp/main";
	}
	

	/**
	 * 系统开发记录
	 * @return
	 */
	@RequestMapping("systemDevMark")
	public String systemDevMark(HttpServletRequest request) {
		try {
			List<DevMark> list = desktopService.getSystemDevMark(getCurrentAccount(), null);
			request.setAttribute("list", list);
			return "desktop/jsp/systemDevMark";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	@RequestMapping("personalMails")
	public String personalMails() {
		return "desktop/jsp/personalMails";
	}
	
	@RequestMapping("applyNotices")
	public String applyNotices() {
		return "desktop/jsp/applyNotices";
	}
	
	@RequestMapping("publicNotices")
	public String publicNotices() {
		return "desktop/jsp/publicNotices";
	}

}
