package com.yida.file.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.utils.StringUtils;
import com.yida.core.base.service.BaseService;
import com.tools.sys.PageInfo;
import com.yida.file.entity.BusinessFile;
import com.yida.file.entity.FileInfo;
import com.yida.file.vo.FileUploadForm;
import com.yida.file.vo.ListBusinessFileForm;

@Service
public class BusinessFileService extends BaseService{
	
	public @Resource FileService fileService;
	
	public List<BusinessFile> listBusinessFile(ListBusinessFileForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(businessFileDao.getTableName() + " o where 1=1");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String name = StringUtils.notNull(query.getName()).trim();
			if (0 < name.length()) {
				fromBuilder.append(" and o.name like ?");
				paramList.add("%" + name + "%");
			}
			
			String businessId = StringUtils.notNull(query.getBusinessId()).trim();
			if (0 < businessId.length()) {
				fromBuilder.append(" and o.business_id = ?");
				paramList.add(businessId);
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = businessFileDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<BusinessFile> list = businessFileDao.findListBySql("select o.* " + fromSql + " order by o.receive_date desc", params, pageInfo);
		for (BusinessFile bf : list) {
			List<FileInfo> fis = this.fileService.listFile(null, bf.getId(), null);
			if (null != fis && 0 < fis.size()) {
				bf.setFileId(fis.get(0).getId());
			}
		}
		return list;
	}

	
	@Transactional
	public BusinessFile saveBusinessFile(BusinessFile businessFile) {
		businessFile.setId(null);
		
		this.businessFileDao.persist(businessFile);
		
		String name = StringUtils.notNull(businessFile.getName()).trim();
		if (0 == name.length()) {
			throw new IllegalStateException("提供资料名称不能为空");
		}
		businessFile.setName(name);
		
		Integer copies = businessFile.getCopies();
		if (null == copies || copies < 1) {
			throw new IllegalStateException("份数必须为大于1的整数");
		}
		
		Boolean isElectronic = businessFile.getIsElectronic();
		if (null != isElectronic && Boolean.TRUE == isElectronic
				&& null == businessFile.getUpload()) {
			throw new IllegalStateException("必须上传对应的电子版资料");
		}
		
		if (null != businessFile.getUpload()) {
			FileUploadForm fuf = new FileUploadForm();
			fuf.setUpload(businessFile.getUpload());
			fuf.setUploadFileName(businessFile.getUploadFileName());
			fuf.setUploadContentType(businessFile.getUploadContentType());
			fuf.setRefModule(businessFile.getRefModule());
			fuf.setRefRecordId(businessFile.getId());
			fuf.setCreateStepId(businessFile.getStepId());
			fuf.setOpMan(businessFile.getReceiveStaff());
			this.fileService.upload(fuf);
		}
		
		/*if (this.businessFileDao.columnValueIsExists("name", businessFile.getName(), "id", businessFile.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}*/
		
		return businessFile;
	}

	
	@Transactional
	public void updateBusinessFile(BusinessFile businessFile) {
		
		String name = StringUtils.notNull(businessFile.getName()).trim();
		if (0 == name.length()) {
			throw new IllegalStateException("提供资料名称不能为空");
		}
		businessFile.setName(name);
		
		Integer copies = businessFile.getCopies();
		if (null == copies || copies < 1) {
			throw new IllegalStateException("份数必须为大于1的整数");
		}
		
		Boolean isElectronic = businessFile.getIsElectronic();
		if (null != isElectronic && Boolean.TRUE == isElectronic
				&& null == businessFile.getUpload()) {
			List<FileInfo> list = fileService.listFile(businessFile.getRefModule(), businessFile.getId(), null);
			if (0 == list.size()) {
				throw new IllegalStateException("必须上传对应的电子版资料");
			}
		}
		
		
		if (null != businessFile.getUpload()) {
			// 查看是否已经存在上传的文件，如果存在，则先把已有的文件删除。
			List<FileInfo> fis = this.fileService.listFile(businessFile.getRefModule(), businessFile.getId(), null);
			for (FileInfo fi : fis) {
				this.fileService.delFile(fi.getId());
			}
			
			FileUploadForm fuf = new FileUploadForm();
			fuf.setUpload(businessFile.getUpload());
			fuf.setUploadFileName(businessFile.getUploadFileName());
			fuf.setUploadContentType(businessFile.getUploadContentType());
			fuf.setRefModule(businessFile.getRefModule());
			fuf.setRefRecordId(businessFile.getId());
			fuf.setCreateStepId(businessFile.getStepId());
			fuf.setOpMan(businessFile.getReceiveStaff());
			this.fileService.upload(fuf);
		}
		
		this.businessFileDao.merge(businessFile);
	}

	
	@Transactional
	public void delBusinessFileById(String id) {
		BusinessFile businessFile = this.businessFileDao.get(id);
		
		try {
			this.businessFileDao.remove(businessFile);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	
	public BusinessFile getBusinessFileById(String businessFileId) {
		return this.businessFileDao.get(businessFileId);
	}

	
}
