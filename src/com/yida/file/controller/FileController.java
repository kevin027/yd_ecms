package com.yida.file.controller;

import com.tools.sys.PageInfo;
import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.core.base.entity.Staff;
import com.yida.file.entity.FileInfo;
import com.yida.file.vo.FileDownloadResult;
import com.yida.file.vo.FileUploadForm;
import com.yida.file.vo.ListFileForm;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	protected static final long serialVersionUID = 9096685171133030434L;

	@RequestMapping("fileUploadPage")
	public String fileUploadPage() {
		return "business/project/jsp/fileUploadPage";
	}

    @ResponseBody
	@RequestMapping("upload")
	public String upload(File file,String fileFileName,String fileContentType,String refModule,String refRecordId,String remark,String stepId,List<String> uploadFileName) {
		JSONObject result = new JSONObject();
		Staff opStaff = super.getCurrentAccount().getStaff();
		try {
			if (null != file ) {
				try {
					FileUploadForm form = new FileUploadForm();
					form.setUpload(file);
					form.setUploadFileName(fileFileName);
					form.setUploadContentType(fileContentType);
					form.setOpMan(opStaff);
					form.setRefModule(refModule);
					form.setRefRecordId(refRecordId);
					form.setRemark(remark);
					form.setCreateStepId(stepId);
					fileService.upload(form);
				} catch (Exception e) {
					throw e;
				}
			}
			result.put(SysConstant.AJAX_REQ_STATUS, true);
			result.put(SysConstant.AJAX_MSG, String.format("上传成功,", Arrays.toString(uploadFileName.toArray())));
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
			result.put(SysConstant.AJAX_MSG, "上传失败," + e.getMessage());
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("download")
	public String download(HttpServletRequest request,String fileId) {
		Staff opStaff = super.getCurrentAccount().getStaff();
		try {
			FileDownloadResult r = fileService.download(fileId, opStaff);
			String downloadFileName = new String(r.getDownloadFileName().getBytes("GBK"), "ISO8859-1");
            InputStream downloadInputStream = r.getDownloadFile();
            request.setAttribute("downloadFileName",downloadFileName);
            request.setAttribute("downloadInputStream",downloadInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "download";
	}
	
	@RequestMapping("delFile")
	public String delFile(String fileId) {
		JSONObject result = new JSONObject();
		try {
			this.fileService.delFile(fileId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
		}
		jsonText = result.toString();
		return jsonText;
	}
	
	@RequestMapping("listFile")
	public String listFile(ListFileForm query,PageInfo pageInfo) {
		try {
			List<FileInfo> list = this.fileService.listFile(query, pageInfo);
			List<String> excludePropertys = Arrays.asList("opLogs", "createUser");
			
			if (null == pageInfo) {
				jsonText = "{\"rows\":" + StringUtils.toJsonArrayExcludeProperty(list, excludePropertys) + "}";
			} else {
				jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayExcludeProperty(list, excludePropertys) + "}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonText;
	}
	
	@RequestMapping("fileSimpleView")
	public String fileSimpleView(HttpServletRequest request,ListFileForm query,String refModule,String refRecordId) {
		query = new ListFileForm();
		query.setRefModule(refModule);
		query.setRefRecordId(refRecordId);
		List<FileInfo> list = this.fileService.listFile(query, null);
		request.setAttribute("list", list);
		return "fileSimpleView";
	}
	
}
