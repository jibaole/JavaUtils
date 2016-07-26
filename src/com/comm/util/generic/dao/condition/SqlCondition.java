/**  
* @Title: SqlCondition.java
* @Package com.comm.util.generic.dao.condition
* @Description: TODO(用一句话描述该文件做什么)
* @author Guoqingcun guoqingcun@allinmd.cn 
* @date 2014-3-25 下午1:16:31
* @version V1.0  
*/
package com.comm.util.generic.dao.condition;

import java.util.List;

import com.comm.util.generic.dao.condition.SqlMapCondition.Sign;

/**
 * @ClassName: SqlCondition
 * @Description: sql动态条件
 * @author Guoqingcun guoqingcun@allinmd.cn
 * @date 2014-3-25 下午1:16:31
 *
 */
public interface SqlCondition {

	public void addCondition(String columnName,Object value,Sign sign);
	
	public List<SqlMapCondition> getConditions();
	
	public int size();
}
