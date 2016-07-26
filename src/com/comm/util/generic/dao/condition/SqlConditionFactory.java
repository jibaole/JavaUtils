/**  
* @Title: SqlConditionFactory.java
* @Package com.comm.util.generic.dao.condition
* @Description: TODO(用一句话描述该文件做什么)
* @author Guoqingcun guoqingcun@allinmd.cn 
* @date 2014-3-25 下午1:15:12
* @version V1.0  
*/
package com.comm.util.generic.dao.condition;

/**
 * @ClassName: SqlConditionFactory
 * @Description: sql动态条件工厂
 * @author Guoqingcun guoqingcun@allinmd.cn
 * @date 2014-3-25 下午1:15:12
 *
 */
public class SqlConditionFactory {

	public static SqlCondition getInstance(){
		
		return new IbatisSqlCondition();
	}
}
