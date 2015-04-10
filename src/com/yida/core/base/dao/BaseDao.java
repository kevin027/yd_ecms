package com.yida.core.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yida.core.common.PageInfo;

public class BaseDao <E, PK extends Serializable> extends HibernateDaoSupport{
	
	public Class<E> type;
	
	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.type =(Class<E>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
	}
	
	@Autowired
	public void setSessionFactory_(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public Session getHibernateSession(){
		SessionFactory factory = getHibernateTemplate().getSessionFactory();
		Session session = factory.getCurrentSession();
		return session;
	}
	
	public String getTableName() {
		AbstractEntityPersister persister = (AbstractEntityPersister) getHibernateTemplate().getSessionFactory().getClassMetadata(type);
		return persister.getTableName();
	}
	
	
	public String getSimpleName() {
		return this.type.getSimpleName();
	}

	
	public String getCanonicalName() {
		return this.type.getCanonicalName();
	}

	
	public E get(PK id) {
		return this.getHibernateTemplate().get(type, id);
	}
	
	public <T> T getEntity(Class<T> entityClass,String id){
		return this.getHibernateTemplate().get(entityClass, id);
	}

	
	public void persist(E entity) {
		this.getHibernateTemplate().persist(entity);
	}
	
	
	public void refresh(E entity) {
		this.getHibernateTemplate().refresh(entity);
	}

	
	public E merge(E entity) {
		return this.getHibernateTemplate().merge(entity);
	}
	public <T> T mergeEntity(T entity){
		return this.getHibernateTemplate().merge(entity);
	}
	public <T> void saveOrUpdate(T entity){
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	
	public <T> void persistEntity(T entity){
		this.getHibernateTemplate().persist(entity);
	}
	
	public void remove(E entity) {
		this.getHibernateTemplate().delete(entity);
	}
	public <T> void removeEntity(T entity){
		this.getHibernateTemplate().delete(entity);
	}

	
	public int findCountByHql(final String hql, final Object[] params) {
		return ((Long) this.getHibernateTemplate().find(hql, params).get(0)).intValue();
	}
	
	public int findCountBySql(final String sql, final Object[] params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				return (Integer)q.list().get(0);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findListByHql(final String hql, final Object[] params, final PageInfo page) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<E>>() {
			
			@Override
			public List<E> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.createQuery(hql);
				if (null != params) {
					int size = params.length;
					for (int i = 0; i < size; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != page) {
					q.setFirstResult(page.getFirstResult());
					q.setMaxResults(page.getMaxResult());
				}
				return q.list();
			}
		});
	}
	
	
	public List<E> findListByHql(final String hql, final Object[] params) {
		return this.findListByHql(hql, params, null);
	}
	
	
	public List<E> findListByHql(String hql) {
		return this.findListByHql(hql, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findListBySql(final String sql, final Object[] params, final PageInfo page) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<E>>() {
			
			@Override
			public List<E> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.addEntity(type);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != page) {
					q.setFirstResult(page.getFirstResult());
					q.setMaxResults(page.getMaxResult());
				}
				return q.list();
			}
		});
	}
	
	
	public List<E> findListBySql(final String sql, final Object[] params) {
		return this.findListBySql(sql, params, null);
	}
	
	
	public List<E> findListBySql(String sql) {
		return this.findListBySql(sql, null, null);
	}
	
	
	public boolean columnValueIsExists(String columnName, Object columnValue, String pkName, PK excludePk) {
		List<Object> params = new ArrayList<Object>(2);
		
		StringBuffer sqlBuffer = new StringBuffer("select count(%1$s)");
		sqlBuffer.append(" from %2$s where %3$s = ?");
		params.add(columnValue);
		
		if (null != excludePk) {
			sqlBuffer.append(" and %1$s <> ?");
			params.add(excludePk);
		}
		
		String sql = String.format(sqlBuffer.toString(), pkName, this.getTableName(), columnName);
		return 0 < this.findCountBySql(sql, params.toArray());
	}
	
	
	public boolean columnValueIsExists(String columnName, Object columnValue, String pkName) {
		return this.columnValueIsExists(columnName, columnValue, pkName, null);
	}

	
	public boolean columnValueIsExists(String limitedColumnName,
			Object limitedColumnValue, String columnName, Object columnValue,
			String pkName, PK excludePk) {
		List<Object> params = new ArrayList<Object>(3);
		
		StringBuffer sqlBuffer = new StringBuffer("select count(%1$s)");
		sqlBuffer.append(" from %2$s where %3$s = ? and %4$s = ?");
		params.add(columnValue);
		params.add(limitedColumnValue);
		
		if (null != excludePk) {
			sqlBuffer.append(" and %1$s <> ?");
			params.add(excludePk);
		}
		
		String sql = String.format(sqlBuffer.toString(), pkName, this.getTableName(), columnName, limitedColumnName);
		return 0 < this.findCountBySql(sql, params.toArray());
	}

	
	public boolean columnValueIsExists(String limitedColumnName,
			Object limitedColumnValue, String columnName, Object columnValue,
			String pkName) {
		return this.columnValueIsExists(limitedColumnName, limitedColumnValue, columnName, columnValue, pkName, null);
	}

	
	public Integer updateBySql(final String sql, final Object[] params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				return q.executeUpdate();
			}
		});
		
	}
	
	
	public int executeSQL(final String sql) {
		Integer result = this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.executeUpdate();
			}
		});
		return result;
	}
	
	
	public int executeSQL(final String sql, final Object[] params) {
		Integer result = this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return query.executeUpdate();
			}
		});
		return result;
	}

	
	
	public <T> List<T> findListBySqlAsAliasToBean(final String sql, final Class<T> type, 
			final Object[] params, final PageInfo page) {
		@SuppressWarnings("unchecked")
		List<T> list = this.getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setResultTransformer(Transformers.aliasToBean(type));
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != page) {
					q.setFirstResult(page.getFirstResult());
					q.setMaxResults(page.getMaxResult());
				}
				return q.list();
			}
		});
		return list;
	}
	
	
	public <T> List<T> findListBySqlAsAliasToBean2(final String sql, final Class<T> type, 
			final Object[] params, final Integer curPage, final Integer pageSize) {
		PageInfo page=null;
		if (null != curPage && null != pageSize) {
			page=new PageInfo();
			page.setCurrentPage(curPage);
			page.setMaxResult(pageSize);
		}
		return this.findListBySqlAsAliasToBean(sql, type, params, page);
	}

	
	public <T> List<T> findListBySqlAsAliasToBean(String sql, Class<T> type, Object[] params) {
		return this.findListBySqlAsAliasToBean(sql, type, params, null);
	}

	
	public <T> List<T> findListBySqlAsAliasToBean(String sql, Class<T> type) {
		return this.findListBySqlAsAliasToBean(sql, type, null, null);
	}

}
