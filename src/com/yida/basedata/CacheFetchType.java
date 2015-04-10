package com.yida.basedata;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface CacheFetchType<T> {
	public List<T> execute(Class<T> type, HibernateTemplate hibernateTemplate);
}
