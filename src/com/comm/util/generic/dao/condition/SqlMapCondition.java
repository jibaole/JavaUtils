/**  
* @Title: SqlMapCondition.java
* @Package com.comm.util.generic.dao.condition
* @Description: TODO(用一句话描述该文件做什么)
* @author Guoqingcun guoqingcun@allinmd.cn 
* @date 2014-3-25 下午1:20:00
* @version V1.0  
*/
package com.comm.util.generic.dao.condition;

/**
 * @ClassName: SqlMapCondition
 * @Description: SQL
 * @author Guoqingcun guoqingcun@allinmd.cn
 * @date 2014-3-25 下午1:20:00
 *
 */
public class SqlMapCondition {

	private String columnName;
	
	private Object value;
	
	private Sign sign;
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}


	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}



	public static enum Sign{
		IN("in","in"),
		NOT_IN("in","not in"),
		LIKE("like","like"),
		NOT_LIKE("like","not like"),
		EQUALS("compare","="),
		NO_EQUALS("compare","!="),
		GREATER("compare",">"),
		GREATER_EQUALS("compare",">="),
		LESS("compare","<"),
		LESS_EQUALS("compare","<=");
		
		private String text;
		private String value;
		
		Sign(String text,String value) {
			this.text = text;
			this.value = value;
		}
	}
	
	
}
