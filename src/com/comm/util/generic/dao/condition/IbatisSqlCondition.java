/**  
* @Title: IbatisSqlCondition.java
* @Package com.comm.util.generic.dao.condition
* @Description: 
* @author Guoqingcun guoqingcun@allinmd.cn 
* @date 2014-3-25 下午1:37:37
* @version V1.0  
*/
package com.comm.util.generic.dao.condition;

import java.util.ArrayList;
import java.util.List;

import com.comm.util.generic.dao.condition.SqlMapCondition.Sign;


/**
 * @ClassName: IbatisSqlCondition
 * @Description: IBATIS动态SQL条件
 * @author Guoqingcun guoqingcun@allinmd.cn
 * @date 2014-3-25 下午1:37:37
 *
 */
public class IbatisSqlCondition implements SqlCondition {
	
	private List<SqlMapCondition> conditions;
	
	public IbatisSqlCondition() {
		conditions = new ArrayList<SqlMapCondition>();
	}

	/* (非 Javadoc)
	 * <p>Title: addCondition</p>
	 * <p>添加动态SQL条件 </p>
	 * @param columnName
	 * @param value
	 * @param sign
	 */
	@Override
	public void addCondition(String columnName, Object value,Sign sign) {
		SqlMapCondition condition = new SqlMapCondition();
		condition.setColumnName(columnName);
		condition.setValue(value);
		condition.setSign(sign);
		
		conditions.add(condition);
	}

	/* (非 Javadoc)
	 * <p>Title: getConditions</p>
	 * <p>获取动态条件集合 </p>
	 * @return
	 */
	@Override
	public List<SqlMapCondition> getConditions() {
		return conditions;
	}

	/* (非 Javadoc)
	 * <p>Title: size</p>
	 * <p>动态条件个数 </p>
	 * @return
	 */
	@Override
	public int size() {
		return null == conditions ? 0 : conditions.size();
	}

}
