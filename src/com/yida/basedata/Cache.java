package com.yida.basedata;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.yida.core.base.dao.BaseDao;
/**
 * 
 * @author kevin
 * @描述 提取基础数据类
 *
 */
@Scope("singleton")
@Service
public class Cache extends BaseDao<Cache, String> implements Observer{
	
	private Map<Class<?>, AtomicBoolean> changeMap = new ConcurrentHashMap<>();
	private Map<Class<?>, List<?>> cacheDatas = new ConcurrentHashMap<>();
	
	public Cache() {
		//changeMap.put(UnitType.class, new AtomicBoolean(true));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Class<?> type = (Class<?>)arg;
		AtomicBoolean change = changeMap.get(type);
		if (null == change) {
			throw new IllegalStateException("没有维护" + type + "此类型数据");
		}
		change.set(true);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> get(Class<T> type, CacheFetchType<T> cacheFetchType) {
		AtomicBoolean change = changeMap.get(type);
		if (null == change) {
			throw new IllegalStateException("没有维护" + type + "此类型数据");
		}
		List<T> list = null;
		boolean needUpdate = change.get();
		needUpdate = true;
		if (needUpdate) {
			list = cacheFetchType.execute(type, this.getHibernateTemplate());
			cacheDatas.put(type, list);
			change.set(false);
		} else {
			list = (List<T>)cacheDatas.get(type);
		}
		return list;
	}
	
	public <T> List<T> getAsOptions(Class<T> type) {
		/*return this.get(type, new CacheFetchType<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> execute(Class<T> type, HibernateTemplate hibernateTemplate) {
				String hql = String.format("select new %1$s(id, name) from %1$s where invalid = false", type.getCanonicalName());
				return (List<T>)hibernateTemplate.find(hql);
			}
		});*/
		return get(type);
	}
	
	public <T> List<T> get(Class<T> type) {
		return this.get(type, new CacheFetchType<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> execute(Class<T> type, HibernateTemplate hibernateTemplate) {
				String hql = String.format("select o from %1$s o where o.invalid = false ", type.getCanonicalName());
				return hibernateTemplate.find(hql);
			}
		});
	}
	
	public <T> List<T> getParentOptions(Class<T> type){
		return this.get(type, new CacheFetchType<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> execute(Class<T> type, HibernateTemplate hibernateTemplate) {
				String hql = String.format("select o from %1$s o where o.invalid = false and o.parent is null", type.getCanonicalName());
				return hibernateTemplate.find(hql);
			}
		});
	}

	public <T> List<T> getPidOptions(Class<T> type){
		return this.get(type, new CacheFetchType<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> execute(Class<T> type, HibernateTemplate hibernateTemplate) {
				String hql = String.format("select o from %1$s o where o.invalid = false and o.pid = '-1'", type.getCanonicalName());
				return hibernateTemplate.find(hql);
			}
		});
	}
	
	public <T> List<T> getUnitInfos(Class<T> type){
		return this.get(type, new CacheFetchType<T>(){
			@SuppressWarnings("unchecked")
			@Override
			public List<T> execute(Class<T> type, HibernateTemplate hibernateTemplate) {
				String hql = String.format("select o from %1$s o where 1=1", type.getCanonicalName());
				return hibernateTemplate.find(hql);
			}
		});
	}
}
