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
 * web_gtx 数据源 dao基类
 * 
 * @author Administrator
 * 
 */
public class GenericGtxDAOImpl extends SqlMapClientDaoSupport {
	@Resource(name = "sqlMapClientGtx")
	SqlMapClient sqlMapClientGtx;

	
	@PostConstruct
	public void setSqlMapClientBase() {
		super.setSqlMapClient(sqlMapClientGtx);
	}

	public GenericGtxDAOImpl() {
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
