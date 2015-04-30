package com.yida.core.base.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tools.sys.PageInfo;

@Repository
public class SqlCommonDao extends BaseDao<Object, Serializable> {
	
	public <T> T getEntity(Class<T> entityClass, Serializable id){
		if(id==null) return null;
		return this.getHibernateTemplate().get(entityClass, id);
	}

	
	@Override
	public <T> T mergeEntity(T entity){
		return this.getHibernateTemplate().merge(entity);
	}
	
	
	@Override
	public <T> void persistEntity(T entity){
		this.getHibernateTemplate().persist(entity);
	}
	
	
	@Override
	public <T> void removeEntity(T entity){
		this.getHibernateTemplate().delete(entity);
	}
	
	
	@Override
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
	
	
	public List<?> findBySql(final String sql,final Object[] params){
		return this.getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				return q.list();
			}
		});
	}
	
	
	public int getCountBySql(String countName, String fromSql, Object[] params) {
		final String sql = String.format("select count(%1$s) ", countName) + fromSql;
		return this.getUniqueBySqlAsAutoCast(sql, params);
	}

	
	@Override
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
	
	
	@Override
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

	
	public <T> T getUniqueBySqlAsAliasToBean(final String sql, final Class<T> beanType, final Object[] params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<T>() {
			@Override
			@SuppressWarnings("unchecked")
			
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setResultTransformer(Transformers.aliasToBean(beanType));
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				return (T) q.uniqueResult();
			}
		});
	}
	
	
	public <T> T getUniqueBySqlAsAliasToBean(final String sql, final Class<T> beanType) {
		return this.getUniqueBySqlAsAliasToBean(sql, beanType, null);
	}
	
	
	@Override
	public <T> List<T> findListBySqlAsAliasToBean(final String sql, final Class<T> beanType, 
			final Object[] params, final PageInfo page) {
		if (null == page) {
			return this.findListBySqlAsAliasToBean(sql, beanType, params, null, null);
		}
		return this.findListBySqlAsAliasToBean(sql, beanType, params, page.getCurrentPage(), page.getMaxResult());
	}
	
	
	public <T> List<T> findListBySqlAsAliasToBean(final String sql, final Class<T> beanType, 
			final Object[] params, final Integer curPage, final Integer pageSize) {
		@SuppressWarnings("unchecked")
		List<T> list = this.getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setResultTransformer(Transformers.aliasToBean(beanType));
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != curPage && null != pageSize) {
					q.setFirstResult((curPage-1) * pageSize);
					q.setMaxResults(pageSize);
				}
				return q.list();
			}
		});
		return list;
	}

	
	@Override
	public <T> List<T> findListBySqlAsAliasToBean(String sql, Class<T> beanType, Object[] params) {
		return this.findListBySqlAsAliasToBean(sql, beanType, params, null);
	}

	
	@Override
	public <T> List<T> findListBySqlAsAliasToBean(String sql, Class<T> beanType) {
		return this.findListBySqlAsAliasToBean(sql, beanType, null, null);
	}

	
	public <T> T getUniqueBySqlAsAutoCast(final String sql, final Object[] params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<T>() {
			@Override
			@SuppressWarnings("unchecked")
			
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				return (T) q.uniqueResult();
			}
		});
	}
	
	
	public <T> T getUniqueBySqlAsAutoCast(final String sql) {
		return getUniqueBySqlAsAutoCast(sql, null);
	}
	
	@SuppressWarnings("unchecked")
	
	public <T> List<T> findListBySqlAsAutoCast(final String sql, final Object[] params, final Integer curPage, final Integer pageSize) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != curPage && null != pageSize) {
					q.setFirstResult((curPage-1) * pageSize);
					q.setMaxResults(pageSize);
				}
				return q.list();
			}
		});
	}
	
	
	public <T> List<T> findListBySqlAsAutoCast(final String sql, final Object[] params) {
		return this.findListBySqlAsAutoCast(sql, params, null, null);
	}

	
	public <T> List<T> findListBySqlAsAutoCast(final String sql) {
		return this.findListBySqlAsAutoCast(sql, null);
	}
	
	
	public <G> List<G> findEntityListBySql(final Class<G> entityClass, final String sql, final Object[] params,
			final PageInfo pageInfo) {
		@SuppressWarnings("unchecked")
		List<G> list = this.getHibernateTemplate().executeFind(new HibernateCallback<List<G>>() {
			
			@Override
			public List<G> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.addEntity(entityClass);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != pageInfo) {
					q.setFirstResult(pageInfo.getFirstResult());
					q.setMaxResults(pageInfo.getMaxResult());
				}
				return q.list();
			}
		});
		return list;
	}

	
	public <G> List<G> findEntityListBySql(Class<G> entityClass, String sql, Object[] params) {
		return findEntityListBySql(entityClass, sql, params, null);
	}

	
	public <G> List<G> findEntityListBySql(Class<G> entityClass, String sql) {
		return findEntityListBySql(entityClass, sql, null, null);
	}
	
	public List<?> findListBySqlAndType(final String sql,final Class<?> type,final PageInfo pageInfo){
		return getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				Query query = arg0.createSQLQuery(sql).addEntity(type);
				if(pageInfo!=null){
					query.setFirstResult(pageInfo.getFirstResult());
					query.setMaxResults(pageInfo.getCurrentPage());
				}
				return query.list();
			}
		});
	}

	
	public <TB> TB getUniqueBySqlAsAliasToBean2(final String sql, final Class<TB> beanType,
			final Object[] params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<TB>() {
			@Override
			@SuppressWarnings("unchecked")
			
			public TB doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setResultTransformer(CamelCaseAliasToBeanTransformer.toBean(beanType));
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				return (TB) q.uniqueResult();
			}
		});
	}

	
	public <TB> TB getUniqueBySqlAsAliasToBean2(String sql, Class<TB> beanType) {
		return this.getUniqueBySqlAsAliasToBean2(sql, beanType, null);
	}

	
	@Override
	public <TB> List<TB> findListBySqlAsAliasToBean2(final String sql,
			final Class<TB> beanType, final Object[] params, final Integer curPage, final Integer pageSize) {
		@SuppressWarnings("unchecked")
		List<TB> list = this.getHibernateTemplate().executeFind(new HibernateCallback<List<TB>>() {
			
			@Override
			public List<TB> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setResultTransformer(CamelCaseAliasToBeanTransformer.toBean(beanType));
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						q.setParameter(i, params[i]);
					}
				}
				if (null != curPage && null != pageSize) {
					q.setFirstResult((curPage-1) * pageSize);
					q.setMaxResults(pageSize);
				}
				return q.list();
			}
		});
		return list;
	}

	
	public <TB> List<TB> findListBySqlAsAliasToBean2(String sql,
			Class<TB> beanType, Object[] params) {
		return this.findListBySqlAsAliasToBean2(sql, beanType, params, null, null);
	}

	
	public <TB> List<TB> findListBySqlAsAliasToBean2(String sql,
			Class<TB> beanType) {
		return this.findListBySqlAsAliasToBean2(sql, beanType, null, null, null);
	}

}
