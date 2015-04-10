package com.yida.file.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.core.base.entity.Staff;
import com.yida.file.entity.FileInfo;
import com.yida.file.service.FileService;
import com.yida.file.vo.FileDownloadResult;
import com.yida.file.vo.FileUploadForm;
import com.yida.file.vo.ListFileForm;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	protected static final long serialVersionUID = 9096685171133030434L;

	public @Resource FileService fileService;
	public File file;
	public String fileFileName;
	public String fileContentType;
	
	public List<File> upload;
	public List<String> uploadFileName;
	public List<String> uploadContentType;
	
	public String downloadFileName;
	public InputStream downloadInputStream;
	
	public String refModule;
	public String refRecordId;
	public String remark;
	public String stepId;

	public String fileId;
	public ListFileForm query;
	
	@RequestMapping("fileUploadPage")
	public String fileUploadPage() {
		return "business/project/jsp/fileUploadPage";
	}
	
	@RequestMapping("upload")
	public String upload() {
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
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("download")
	public String download() {
		Staff opStaff = super.getCurrentAccount().getStaff();
		try {
			FileDownloadResult r = fileService.download(fileId, opStaff);
			this.downloadFileName = new String(r.getDownloadFileName().getBytes("GBK"), "ISO8859-1");
			this.downloadInputStream = r.getDownloadFile();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "download";
	}
	
	@RequestMapping("delFile")
	public String delFile() {
		JSONObject result = new JSONObject();
		try {
			this.fileService.delFile(fileId);
			result.put(SysConstant.AJAX_REQ_STATUS, true);
		} catch (Exception e) {
			result.put(SysConstant.AJAX_REQ_STATUS, false);
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("listFile")
	public String listFile() {
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
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	@RequestMapping("fileSimpleView")
	public String fileSimpleView(HttpServletRequest request) {
		query = new ListFileForm();
		query.setRefModule(refModule);
		query.setRefRecordId(refRecordId);
		List<FileInfo> list = this.fileService.listFile(query, null);
		request.setAttribute("list", list);
		return "fileSimpleView";
	}
	
}
