package com.yida.core.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Role;
import com.yida.core.base.vo.ListFunctionForm;
import com.yida.core.common.PageInfo;
import com.yida.core.interfaces.IFunctionTreeFilter;

@Service("functionService")
@Scope("singleton")
@Lazy(true)
public class FunctionService extends BaseService {
	
	/**
	 * 获取系统功能结构树
	 * @return
	 */
	public List<Function> getFunctionTree() {
		boolean isFetchChildren = true;
		List<Function> list = functionDao.getTopLevelFunction(isFetchChildren);
		return list;
	}

	/**
	 * 根据ID获取系统功能
	 * @param id
	 * @return
	 */
	public Function getById(String id) {
		return this.functionDao.get(id);
	}

	/**
	 * 添加系统功能
	 * @param function
	 */
	@Transactional
	public Function saveFunction(Function function) {
		Function parent = null;
		if (null != function.getParent() && StringUtils.isMeaningFul(function.getParent().getId())) {
			parent = functionDao.get(function.getParent().getId());
		}  else {
			function.setParent(null);
		}
		
		function.setInvalid(false);
		function.setIsSys(false);
		
		if (null != parent) {
			function.setHierarchy(parent.getHierarchy()+1);
			function.setId(parent.getId() + function.getSortCode());
		} else {
			function.setHierarchy(1);
			function.setId(function.getSortCode());
		}
		
		this.functionDao.persist(function);
		return function;
	}

	/**
	 * 更新系统功能
	 * @param function
	 */
	@Transactional
	public void updateFunction(Function function) {
		Function old = this.functionDao.get(function.getId());
		old.setName(function.getName());
		old.setCode(function.getCode());
		old.setHref(function.getHref());
		old.setIcon(function.getIcon());
		old.setSortCode(function.getSortCode());
		old.setType(function.getType());
		old.setGroups(function.getGroups());
	}

	/**
	 * 删除系统功能
	 * @param id
	 */
	@Transactional
	public void delFunctionById(String id) {
		Function persist = this.functionDao.get(id);
		if (null != persist) {
			if (Boolean.TRUE == persist.getIsSys()) {
				throw new IllegalStateException("功能【" + persist.getName() + "】是系统核心功能，不能删除。");
			}
			persist.setParent(null);
			for (Role r : persist.getRoles()) {
				r.getFunctions().remove(persist);
			}
			this.functionDao.remove(persist);
		}
	}

	/**
	 * 列出系统功能
	 * @param query
	 * @param pageInfo
	 * @return
	 */
	public List<Function> listFunction(ListFunctionForm query, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder("from s_function o where o.invalid = 0");
		
		List<Object> params = new ArrayList<Object>();
		if (null != query) {
			if (null != query.getFunctionType()) {
				sb.append(" o.type = ?");
				params.add(query.getFunctionType());
			}
			
			if (StringUtils.isMeaningFul(query.getParentFunctionId())) {
				sb.append(" o.parent_id = ?");
				params.add(query.getParentFunctionId().trim());
			}
			
			List<String> list = query.getExcludeFunctionIds();
			if (null != list && 0 < list.size()) {
				sb.append("(");
				for (int i = 0; i < list.size(); i++) {
					sb.append(0 == i ? "?" : ",?");
					params.add(list.get(i));
				}
				sb.append(")");
			}
		}
		
		if (null != pageInfo) {
			int total = functionDao.findCountBySql("select count(distinct o.id) " + sb, params.toArray());
			pageInfo.setTotalResult(total);
		}
		
		List<Function> list = functionDao.findListBySql("select o.* " + sb.toString(), params.toArray(), pageInfo);
		return list;
	}
	
	/**
	 * 根据账号查找其拥有的所有系统功能
	 * @param account
	 * @return
	 */
	public List<Function> findAccountFunctions(Account account) {
		if (null != account.getType()) {
			String accountId = account.getId();
			String auditOrgId = account.getCurrentAuditOrgId();
			if (Account.Type.STAFF == account.getType()) {
				return this.functionDao.findAccountFunctionsForStaff(accountId, auditOrgId);
			} else if (Account.Type.ADMIN == account.getType()) {
				return this.functionDao.findAccountFunctionsForAdmin(accountId, auditOrgId);
			}
		}
		return new ArrayList<Function>();
	}

	/**
	 * 将功能列表通过从属关系转成树结构的表现形式。
	 * @param funs
	 * @param filter
	 * @return
	 */
	public List<Function> listToFunctionTree(List<Function> funs,IFunctionTreeFilter filter) {
		Map<String, Function> maps = new ConcurrentHashMap<String, Function>();
		List<Function> menus = new ArrayList<Function>();
		for (Function f : funs) {
			if (maps.containsKey(f.getId())) continue;
			if (null != filter && !filter.accpet(f)) continue;
			
			f.setChildren(new ArrayList<Function>());
			if (null == f.getParent()) {
				menus.add(f);
			} else {
				Function p = maps.get(f.getParent().getId());
				p.getChildren().add(f);
			}
			maps.put(f.getId(), f);
		}
		return menus;
	}

	/**
	 * 查找员工账号在指定机构下所拥有的系统功能
	 * @param accountId
	 * @param auditOrgId
	 * @return
	 */
	public List<Function> findAccountFunctionsForStaff(String accountId, String auditOrgId) {
		return this.functionDao.findAccountFunctionsForStaff(accountId, auditOrgId);
	}

	/**
	 * 查找管理员账号所拥有的系统功能
	 * @param accountId
	 * @param auditOrgId
	 * @return
	 */
	public List<Function> findAccountFunctionsForAdmin(String accountId, String auditOrgId) {
		return this.functionDao.findAccountFunctionsForAdmin(accountId, auditOrgId);
	}

}
