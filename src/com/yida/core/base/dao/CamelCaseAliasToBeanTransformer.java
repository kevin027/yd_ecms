package com.yida.core.base.dao;

import java.util.List;

import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

import com.tools.utils.StringUtils;

class CamelCaseAliasToBeanTransformer implements ResultTransformer {

	private static final long serialVersionUID = 4564398361349116557L;
	
	final Class<?> type;
	PropertyAccessor propertyAccessor = null;
	Setter[] setters  = null;
	private CamelCaseAliasToBeanTransformer(Class<?> type) {
		this.type = type;
		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] 
				{PropertyAccessorFactory.getPropertyAccessor(type, null), PropertyAccessorFactory.getPropertyAccessor("field")}); 
	}
	
	public static CamelCaseAliasToBeanTransformer toBean(Class<?> type) {
		return new CamelCaseAliasToBeanTransformer(type);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] alias) {
		if (null == setters) {
			setters = new Setter[alias.length];
			for (int i = 0; i < alias.length; i++) {
				setters[i] =  propertyAccessor.getSetter(type, StringUtils.underScoreCaseToCamelCase(alias[i]));
			}
		}
		try {
			Object obj = type.newInstance();
			for (int i = 0; i < alias.length; i++) {
				setters[i].set(obj, tuple[i], null);
			}
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException(type + "实例化出错");
		}
	}

}
