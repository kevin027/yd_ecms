package com.yida.mail.controller;

import com.tools.sys.SysConstant;
import com.tools.sys.SysVariable;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.mail.entity.MailAttach;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/mail")
public class MailAttachController extends BaseController {

    @ResponseBody
	@RequestMapping("uploadAttach")
	public String uploadAttach() {
		JSONObject result = new JSONObject();
		try {
			List<MailAttach> list = this.mailService.addAttach(this);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, "附件添加成功");
			result.put("list", StringUtils.toJsonArrayIncludeProperty(list, Arrays.asList("id", "uploadFileName")));
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "附件添加失败," + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("downloadAttach")
	public String downloadAttach(HttpServletRequest request,String attachId) throws FileNotFoundException, UnsupportedEncodingException {
		MailAttach attach = this.mailService.getAttachById(attachId);
		if (null == attach) {
			throw new IllegalStateException("没有找到对应的文件记录。");
		}
		String downloadFileName = new String(attach.getUploadFileName().getBytes("GBK"), "ISO8859-1");
		InputStream downloadInputStream = new FileInputStream(SysVariable.INSTANCE.getValue("EMAIL_ATTACH_ROOT") + File.separator + attach.getSaveFilePath());
		return "downloadAttach";
	}

}
