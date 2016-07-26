/**  
* @Title: ObjectUtil.java
* @Package com.comm.util
* @Description: TODO(用一句话描述该文件做什么)
* @author Guoqingcun guoqingcun@allinmd.cn 
* @date 2014-3-28 上午11:26:55
* @version V1.0  
*/
package com.comm.util;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: ObjectUtil
 * @Description: 对象工具
 * @author Guoqingcun guoqingcun@allinmd.cn
 * @date 2014-3-28 上午11:26:55
 *
 */
public class ObjectUtils {

	/**
	 * 
	* @Title: isNullOrEmpty
	* @Description: 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	* @param @param obj
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean isNullOrEmpty(Object obj){
		//对象
		if(null == obj)
			return true;
		//字符串
		if(obj instanceof CharSequence){
			return ((CharSequence)obj).length() == 0;
		}
		//集合
		if(obj instanceof Collection){
			return ((Collection)obj).isEmpty();
		}
		//map
		if(obj instanceof Map){
			return ((Map)obj).isEmpty();
		}
		//对象数组
		if(obj instanceof Object[]){
			Object[] objs = (Object[])obj;
			if(objs.length == 0){
				return true;
			}
			
			boolean isEmpty = true;
			for(Object temp : objs){
				if(isNullOrEmpty(temp)){
					isEmpty = false;
					break;
				}
			}
			return isEmpty;
		}
		
		return false;
	}
}
