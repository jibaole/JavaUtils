package com.comm.util.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.comm.util.page.Page;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * Platform 数据源Dao基类
 * 
 * @author Administrator
 * 
 */
public class GenericPlatformDAOImpl extends SqlMapClientDaoSupport {
	@Resource(name = "sqlMapClientPlatform")
	SqlMapClient sqlMapClientPlatform;

	@PostConstruct
	public void setSqlMapClientBase() {
		super.setSqlMapClient(sqlMapClientPlatform);
	}

	public GenericPlatformDAOImpl() {
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

}
