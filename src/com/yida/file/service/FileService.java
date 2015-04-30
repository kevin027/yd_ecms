package com.yida.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.sys.SysVariable;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Staff;
import com.yida.core.base.service.BaseService;
import com.tools.sys.PageInfo;
import com.yida.file.entity.FileInfo;
import com.yida.file.entity.FileOpConfig;
import com.yida.file.entity.FileOpLog;
import com.yida.file.vo.FileDownloadResult;
import com.yida.file.vo.FileUploadForm;
import com.yida.file.vo.ListFileForm;

@Service
public class FileService extends BaseService {
	
	private BeanFactory beanFactory;

	/**
	 * 上传文件，通过上传文件，以件上传时要关联的模块和模块相关的记录ID，保存到服务器配置的位置，并生成对应的保存记录和操作日志。
	 * @param upload 要上传文件对象
	 * @param uploadFileName 客户端文件上传时的名字（包含文件类型，即包含后缀名）
	 * @param uploadFileContentType 客户端文件上传时的类型
	 * @param opMan 上传操作的账号（当前登录的账号）
	 * @param refModule 上传文件关联的模块
	 * @param refRecordId 上传文件关联的模块记录ID
	 * @return 
	 */
	@Transactional
	public FileInfo upload(FileUploadForm ff) {
		File uploadFile = ff.getUpload();
		String uploadFileName = ff.getUploadFileName();
		Staff opMan = ff.getOpMan();
		String refModule = ff.getRefModule();
		String refRecordId = ff.getRefRecordId();
		
		FileOpConfig config = fileOpConfigDao.getEntity(FileOpConfig.class, refModule);
		if (null == config) {
			throw new IllegalStateException("没有添加" + refModule + "模块的文件上传配置记录");
		}
		
		DefaultFileSavePathResolver savePathResolver = (DefaultFileSavePathResolver)beanFactory.getBean(config.getUploadPathResolver());
		if (null != savePathResolver) {
			// 保存到系统的文件名，包含文件类型
			try {
				Path saveSubPath = savePathResolver.resolve(uploadFile, config, refRecordId);
				String saveFileRootPath = SysVariable.INSTANCE.getValue("SAVE_FILE_ROOT");
				Path saveFullPath = Paths.get(saveFileRootPath).resolve(saveSubPath);
				
				String fileType = uploadFileName.replaceAll("^.+\\.", "");
				
				// 文件类型黑名单检查（条件适用优先于白名单）
				String refuseFileType = StringUtils.notNull(config.getRefuseFileType()).trim();
				if (0 < refuseFileType.length() && refuseFileType.matches("(^|.+(?<=,))" + fileType + "((?=,).+|$)")) {
					throw new IllegalStateException("不允许上传格式为" + fileType + "的文件");
				}
				
				// 文件类型白名单检查
				String acceptFileType = StringUtils.notNull(config.getAcceptFileType()).trim();
				if (0 < acceptFileType.length() && !acceptFileType.matches("(^|.+(?<=,))" + fileType + "((?=,).+|$)")) {
					throw new IllegalStateException("不允许上传格式为" + fileType + "的文件");
				}
				
				//如果保存目录不存在就新建
				if (!Files.exists(saveFullPath)) {
					Files.createDirectories(saveFullPath);
				}
				
				// 用UUID生成保存文件名
				String saveFileName = StringUtils.uuid() + "." + fileType;
				
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(uploadFileName); // 保存文件上传时名字
				fileInfo.setSuffix(fileType); // 保存文件上传时类型
				fileInfo.setSavePath(saveSubPath.resolve(saveFileName).toString()); // 保存文件存放的相对路径
				fileInfo.setRefModule(refModule); // 保存文件关联的模块名
				fileInfo.setRefRecordId(refRecordId); // 保存文件关联到的数据库记录ID
				fileInfo.setIsHide(Boolean.FALSE); // 保存文件记录可见状态
				fileInfo.setCreateUserId(ff.getOpMan().getId()); // 保存文件的上传用户
				fileInfo.setCreateDate(new Date()); // 保存文件的上传时间
				fileInfo.setRemark(ff.getRemark()); // 保存文件上传时的备注
				fileInfo.setCreateStepId(ff.getCreateStepId()); // 保存文件上传的步骤
				fileDao.persist(fileInfo);
				
				FileOpLog opLog = new FileOpLog();
				opLog.setOpDate(fileInfo.getCreateDate());
				opLog.setFile(fileInfo);
				opLog.setOpMan(opMan);
				opLog.setOpType(FileOpLog.OpType.UPLOAD);
				fileOpLogDao.persist(opLog);
				
				// 保存文件记录没错之后拷贝文件到保存路径
				Files.copy(new FileInputStream(uploadFile), saveFullPath.resolve(saveFileName), StandardCopyOption.REPLACE_EXISTING);
				return fileInfo;
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
		} else {
			throw new IllegalStateException("获取保存路径解析器失败。");
		}
	}

	/**
	 * 下载文件，通过文件记录ID查找到文件存储在服务器的地址，获取文件的输入流和上传时的文件地址。并且保存操作日志。
	 * @param fileId 要下载的文件记录ID
	 * @param opAccount 下载操作的账号（当前登录的账号）
	 * @return
	 */
	@Transactional
	public FileDownloadResult download(String fileId, Staff opAccount) {
		FileInfo fileInfo = fileDao.getEntity(FileInfo.class, fileId);
		try {
			String saveFileRootPath = SysVariable.INSTANCE.getValue("SAVE_FILE_ROOT");
			Path saveFullPath = Paths.get(saveFileRootPath).resolve(fileInfo.getSavePath());
			
			FileInputStream fis = new FileInputStream(saveFullPath.toFile());
			
			FileOpLog opLog = new FileOpLog();
			opLog.setOpDate(new Date());
			opLog.setFile(fileInfo);
			opLog.setOpMan(opAccount);
			opLog.setOpType(FileOpLog.OpType.DOWNLOAD);
			fileOpLogDao.persist(opLog);
			
			FileDownloadResult r = new FileDownloadResult();
			r.setDownloadFile(fis);
			r.setDownloadFileName(fileInfo.getName());
			
			return r;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	/**
	 * 根据查询条件获取文件记录列表
	 * @param query 查询条件
	 * @param pageInfo 分页信息
	 * @return
	 */
	public List<FileInfo> listFile(ListFileForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from " + fileDao.getTableName() + " o where is_hide = 0");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String module = StringUtils.notNull(query.getRefModule()).trim();
			if (0 < module.length()) {
				fromBuilder.append(" and o.ref_module = ?");
				paramList.add(module);
			}
			
			String refId = query.getRefRecordId();
			if (StringUtils.isMeaningFul(refId)) {
				fromBuilder.append(" and cast(o.ref_record_id as varchar(32)) = ?");
				paramList.add(refId.trim());
			}
			
			String type = query.getType();
			if (StringUtils.isMeaningFul(type)) {
				fromBuilder.append(" and o.type = ?");
				paramList.add(type);
			}
			
			String stepId = query.getStepId();
			if (StringUtils.isMeaningFul(stepId)) {
				fromBuilder.append(" and o.step_id = ?");
				paramList.add(stepId);
			}
			
			Staff uploadMan = query.getUploadMan();
			if (null != uploadMan) {
				fromBuilder.append(" and o.exists (select l.id from file_op_log l where l.op_man = ? and l.file_info_id = o.id)");
				paramList.add(uploadMan.getId());
			}
			
			Date fromDate = query.getUploadFromDate();
			if (null != fromDate) {
				fromBuilder.append(" and o.create_date > ?");
				paramList.add(fromDate);
			}
			
			Date toDate = query.getUploadToDate();
			if (null != toDate) {
				Calendar delayCalendar = Calendar.getInstance();
				delayCalendar.setTime(toDate);
				delayCalendar.add(Calendar.DAY_OF_YEAR, 1);
				fromBuilder.append(" and o.create_date < ?");
				paramList.add(delayCalendar.getTime());
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = fileDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<FileInfo> list = fileDao.findListBySql("select o.* " + fromSql + " order by o.create_date desc", params, pageInfo);
		return list;
	}

	/**
	 * 获取某模块某记录下关联的文件记录列表
	 * @param module 关联的模块ID
	 * @param refId 关联模块的记录ID
	 * @param pageInfo 分页信息
	 * @return
	 */
	public List<FileInfo> listFile(String module, String refId, PageInfo pageInfo) {
		ListFileForm query = new ListFileForm();
		query.setRefModule(module);
		query.setRefRecordId(refId);
		return this.listFile(query, pageInfo);
	}
	
	/**
	 * 获取某模块记录下关联的文件记录数量
	 * @param module
	 * @param refId
	 * @return
	 */
	public int getFileCount(String module, String refId) {
		return this.fileDao.getFileCount(module, refId);
	}

	/**
	 * 删除某模块某记录下关联的文件记录列表（只设置为只隐藏，不删除记录和实际文件）
	 * @param module 关联的模块ID
	 * @param refId 关联模块的记录ID
	 * @Param opMan 操作人员
	 */
	@Transactional
	public void hideFile(String module, String refId, Staff opAccount) {
		List<FileInfo> list = this.listFile(module, refId, null);
		for (FileInfo f : list) {
			f.setIsHide(true);
			
			FileOpLog opLog = new FileOpLog();
			opLog.setOpDate(new Date());
			opLog.setFile(f);
			opLog.setOpMan(opAccount);
			opLog.setOpType(FileOpLog.OpType.DOWNLOAD);
			fileOpLogDao.persist(opLog);
		}
	}

	/**
	 * 根据文件记录ID获取文件记录
	 * @param fileId
	 * @return
	 */
	public FileInfo getFileInfoById(String fileId) {
		return this.fileDao.get(fileId);
	}

	/**
	 * 删除文件记录，不删除文件记录对应服务器上的文件
	 * @param fileId
	 */
	@Transactional
	public void delFile(String fileId) {
		FileInfo file = this.fileDao.get(fileId);
		this.fileDao.remove(file);
	}
	
}
