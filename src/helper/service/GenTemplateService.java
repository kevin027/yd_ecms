package helper.service;

import helper.dao.GenTemplateDao;
import helper.entity.GenTemplate;
import helper.vo.ListGenTemplateForm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.utils.StringUtils;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;

@Service
public class GenTemplateService {
	
	private @Resource GenTemplateDao genTemplateDao;
	
	
	public List<GenTemplate> listGenTemplate(ListGenTemplateForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(genTemplateDao.getTableName() + " o where 1=1");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String name = StringUtils.notNull(query.getName()).trim();
			if (0 < name.length()) {
				fromBuilder.append(" and o.name like ?");
				paramList.add("%" + name + "%");
			}
			
			Boolean invalid = query.getInvalid();
			if (null != invalid) {
				fromBuilder.append(" and o.invalid = ?");
				paramList.add(query.getInvalid());
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = genTemplateDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<GenTemplate> list = genTemplateDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}
	
	
	public List<GenTemplate> listValidGenTemplate(ListGenTemplateForm query,
			PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(genTemplateDao.getTableName() + " o where o.invalid=0");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String name = StringUtils.notNull(query.getName()).trim();
			if (0 < name.length()) {
				fromBuilder.append(" and o.name like ?");
				paramList.add("%" + name + "%");
			}
			
			Boolean invalid = query.getInvalid();
			if (null != invalid) {
				fromBuilder.append(" and o.invalid = ?");
				paramList.add(query.getInvalid());
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = genTemplateDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<GenTemplate> list = genTemplateDao.findListBySql("select o.* " + fromSql + " order by o.name", params, pageInfo);
		return list;
	}

	
	@Transactional
	public GenTemplate saveGenTemplate(GenTemplate genTemplate) {
		genTemplate.setId(null);
		
		if (this.genTemplateDao.columnValueIsExists("name", genTemplate.getName(), "id", genTemplate.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		
		this.genTemplateDao.persist(genTemplate);
		return genTemplate;
	}

	
	@Transactional
	public void updateGenTemplate(GenTemplate genTemplate) {
		
		if (this.genTemplateDao.columnValueIsExists("name", genTemplate.getName(), "id", genTemplate.getId())) {
			throw new IllegalStateException("已经存在相同名称。");
		}
		
		this.genTemplateDao.merge(genTemplate);
	}

	
	@Transactional
	public void delGenTemplateById(String id) {
		GenTemplate genTemplate = this.genTemplateDao.get(id);
		if (null == genTemplate) {
			throw new EntityNotFoundException(GenTemplate.class, id);
		}
		
		try {
			this.genTemplateDao.remove(genTemplate);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	
	public GenTemplate getGenTemplateById(String genTemplateId) {
		return this.genTemplateDao.get(genTemplateId);
	}

	
}
