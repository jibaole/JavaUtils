package com.comm.util.generic.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.comm.util.BaseForm;
import com.comm.util.page.Page;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * web_weicai 数据源 dao基类
 * 
 * 
 */
public class GenericDAOIbatisImplLog<T extends BaseForm> extends
BaseGenericDAOIbatisImpl {

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericDAOIbatisImplLog() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	@Resource(name = "sqlMapClientLog")
	SqlMapClient sqlMapClientLog;

	@PostConstruct
	public void setSqlMapClientBase() {
		super.setSqlMapClient(sqlMapClientLog);
	}

}
