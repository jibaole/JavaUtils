package com.comm.util.generic.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.comm.util.BaseForm;
import com.comm.util.page.Page;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * web_weicai 数据源 dao基类
 * 
 * 
 */
public class BaseGenericDAOIbatisImpl<T extends BaseForm> extends
		SqlMapClientDaoSupport {

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public BaseGenericDAOIbatisImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	@Resource(name = "sqlMapClientAllin")
	SqlMapClient sqlMapClientAllin;

	@PostConstruct
	public void setSqlMapClientBase() {
		super.setSqlMapClient(sqlMapClientAllin);
	}

	public Long create(String nsPrefix, Map paramMap) {

		Long pk = (Long) getSqlMapClientTemplate().insert(
				nsPrefix + "abatorgenerated_insert", paramMap);
		return pk;

	}

	public void update(String nsPrefix, Map paramMap) {
		super.getSqlMapClientTemplate().update(
				nsPrefix + "abatorgenerated_updateByPrimaryKey", paramMap);
	}


	
	public void truncate(String nsPrefix) {
		super.getSqlMapClientTemplate().update(nsPrefix + "truncate");
	}

	public T getById(String nsPrefix, Long id) {

		return (T) getSqlMapClientTemplate().queryForObject(
				nsPrefix + "abatorgenerated_selectByPrimaryKey", id);
	}

	public Page getPageList(String nsPrefix, Map paramMap) {

		Page pageObj = new Page(1, 10);
		pageObj.setItems(getList(nsPrefix, paramMap));
		pageObj.setTotal(getCount(nsPrefix, paramMap));

		return pageObj;

	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String nsPrefix, Map paramMap) {
		return getSqlMapClientTemplate().queryForList(nsPrefix + "getList",
				paramMap);
	}

	public int getCount(String nsPrefix, Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				nsPrefix + "getCount", paramMap);
	}

	/**
	 * 分页查询
	 * 
	 * @param ibatisSqlName
	 *            sql名称，必须定义一个ibatisSqlName+"Total"的sql
	 * @param filter
	 *            条件
	 * @param page
	 *            第几页
	 * @param pageSize
	 *            每页数据量
	 * @return
	 */
	public Page findPageByFilter(String ibatisSqlName, Map filter, int page,
			int pageSize) {
		Page ps = null;
		int startRow = (page - 1) * pageSize;
		if (filter == null) {
			filter = new HashMap();
		}
		filter.put("startRow", startRow);
		filter.put("endRow", pageSize);
		try {
			int totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject(ibatisSqlName + "Total", filter);
			List resultList = getSqlMapClientTemplate().queryForList(
					ibatisSqlName, filter);
			ps = new Page(resultList, totalCount, pageSize, page);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return ps;
	}

	/**
	 * 分页查询
	 * 
	 * @param ibatisSqlName
	 *            sql名称，必须定义一个ibatisSqlName+"Total"的sql
	 * @param filter
	 *            条件(已将startRow,endRow放入filter中)
	 * @return
	 */
	public Map<String, Object> findPageByFilter(String ibatisSqlName, Map filter) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		try {
			int totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject(ibatisSqlName + "Total", filter);
			List resultList = getSqlMapClientTemplate().queryForList(
					ibatisSqlName, filter);
			pageMap.put("total", totalCount);
			pageMap.put("rows", resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageMap;
	}
}
