/*package com.comm.util.generic.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comm.util.BaseForm;
import com.comm.util.generic.dao.condition.SqlCondition;
import com.comm.util.page.Page;

*//**
 * @ClassName: IbatisDao
 * @Description: 增强现有的ibatis DAO层(GenericDAOIbatisImpl)的封装
 * @author Guoqingcun guoqingcun@allinmd.cn
 * @param <T>
 * @date 2014-3-25 上午10:24:29
 *
 *//*
public class IbatisDao<T extends BaseForm> extends GenericDAOIbatisImpl<T> {

	*//**
	 * 动态的查询条件
	 *//*
	private static final String PROPERTY_DYNAMIC_CONDITION = "_dynamic_condition";
	*//**
	 * 动态的排序
	 *//*
	private static final String PROPERTY_DYNAMIC_ORDER_BY = "_dynamic_order_by";
	*//**
	 * 升降
	 *//*
	private static final String PROPERTY_DYNAMIC_ASC_OR_DESC = "_dynamic_asc_or_desc";
	*//**
	 * 查询
	 *//*
	private static final String STATEMENT_SELECT = "select";    
	*//**
	 * 删除
	 *//*
	private static final String STATEMENT_DELETE = "delete";
	*//**
	 * 更新
	 *//*
	private static final String STATEMENT_UPDATE = "update";
	*//**
	 * 插入
	 *//*
	private static final String STATEMENT_INSERT = "insert";
	
	*//**
	 * 查询记录数，和 STATEMENT_SELECT_PAGE 配合使用
	 *//*
	private static final String STATEMENT_SELECT_COUNT = "select_count";
	
	*//**
	 * 根据ID查询，返回一条记录
	 *//*
	private static final String STATEMENT_SELEDCT_BY_ID = "select_by_id";
	
	*//**
	 * 根据ID更新，更新一条记录
	 *//*
	private static final String STATEMENT_UPDATE_BY_ID = "update_by_id";
	*//**
	 * 根据ID删除，删除一条记录
	 *//*
	private static final String STATEMENT_DELETE_BY_ID = "delete_by_id";
	
	*//**
	 * 
	* @Title: insert
	* @Description: 添加
	* @param @param nsPrefix
	* @param @param params
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 *//*
	public int insert(String nsPrefix,Object params){
		
		return (Integer)getSqlMapClientTemplate().insert(nsPrefix+STATEMENT_INSERT, params);
	}
	
	*//**
	 * 
	* @Title: selectOne
	* @Description: 根据ID查询
	* @param @param nsPrefix
	* @param @param id
	* @param @return    设定文件
	* @return T    返回类型
	* @throws
	 *//*
	public T selectOne(String nsPrefix,Object id){
		return (T)getSqlMapClientTemplate().queryForObject(nsPrefix+STATEMENT_SELEDCT_BY_ID, id);
	}
	
	*//**
	 * 
	* @Title: update
	* @Description: 根据ID更新记录
	* @param @param nsPrefix
	* @param @param params
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 *//*
	public int updateById(String nsPrefix,Object params){
		return getSqlMapClientTemplate().update(nsPrefix+STATEMENT_UPDATE_BY_ID, params);
	}
	
	*//**
	 * 
	* @Title: delete
	* @Description: 根据ID删除记录
	* @param @param nsPrefix
	* @param @param id
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 *//*
	public int delete(String nsPrefix,Object id){
		return getSqlMapClientTemplate().delete(nsPrefix+STATEMENT_DELETE_BY_ID, id);
	}
	
	*//**
	 * 
	* @Title: selectList
	* @Description: 查询全部
	* @param @param nsPrefix
	* @param @return    设定文件
	* @return List<T>    返回类型
	* @throws
	 *//*
	public List<T> selectList(String nsPrefix){
		return selectList(nsPrefix,null,null,null);
	}
	*//**
	 * 
	* @Title: selectList
	* @Description: 查询局部
	* @param @param nsPrefix
	* @param @param condition
	* @param @return    设定文件
	* @return List<T>    返回类型
	* @throws
	 *//*
	public List<T> selectList(String nsPrefix,SqlCondition condition){
		return selectList(nsPrefix,condition,null,null);
	}
	
	*//**
	 * 
	* @Title: selectList
	* @Description: 查询且升降排序
	* @param @param nsPrefix
	* @param @param condition
	* @param @param orderBy
	* @param @param ascOrDesc
	* @param @return    设定文件
	* @return List<T>    返回类型
	* @throws
	 *//*
	public List<T> selectList(String nsPrefix,SqlCondition condition,String orderBy,String ascOrDesc){
		Map<String,Object> params = new HashMap<String, Object>(3);
		if(null != condition){
			params.put(PROPERTY_DYNAMIC_CONDITION, condition);
		}
		if(null != orderBy){
			params.put(PROPERTY_DYNAMIC_ORDER_BY, orderBy);
		}
		if(null != ascOrDesc){
			params.put(PROPERTY_DYNAMIC_ASC_OR_DESC, ascOrDesc);
		}
		
		return getSqlMapClientTemplate().queryForList(nsPrefix+STATEMENT_SELECT, params);
	}
	
	*//**
	 * 
	* @Title: selectList
	* @Description: 查询记录数
	* @param @param nsPrefix
	* @param @param condition
	* @param @param orderBy
	* @param @param ascOrDesc
	* @param @return    设定文件
	* @return List<T>    返回类型
	* @throws
	 *//*
	public int selectCount(String nsPrefix,SqlCondition condition){
		Map<String,Object> params = null;
		if(null != condition){
		    params = new HashMap<String, Object>(1);
			params.put(PROPERTY_DYNAMIC_CONDITION, condition.getConditions());
		}
		
		return (Integer) getSqlMapClientTemplate().queryForObject(nsPrefix+STATEMENT_SELECT_COUNT, params);
	}
	
	*//**
	 * 
	* @Title: selectPage
	* @Description: 分页查询
	* @param @param nsPrefix
	* @param @param condition
	* @param @param orderBy
	* @param @param ascOrDesc
	* @param @param pageIndex
	* @param @param pageSize
	* @param @return    设定文件  
	* @return Page    返回类型
	* @throws
	 *//*
	public Page selectPage(String nsPrefix,SqlCondition condition,String orderBy,String ascOrDesc,int pageIndex,int pageSize){
		Map<String,Object> params = new HashMap<String, Object>(5);
		if(null != condition){
			params.put(PROPERTY_DYNAMIC_CONDITION, condition.getConditions());
		}
		params.put("startIndex", pageIndex*pageSize-pageSize);
		params.put("endIndex", pageSize);
		params.put(PROPERTY_DYNAMIC_ORDER_BY, orderBy);
		params.put(PROPERTY_DYNAMIC_ASC_OR_DESC, ascOrDesc);
		List list = getSqlMapClientTemplate().queryForList(nsPrefix+STATEMENT_SELECT, params);
		int count = (Integer) getSqlMapClientTemplate().queryForObject(nsPrefix+STATEMENT_SELECT_COUNT, params);
		Page page = new Page(list, count, pageSize, pageIndex);
		return page;
	}
}
*/