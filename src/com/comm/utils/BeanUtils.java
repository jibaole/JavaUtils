package com.comm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.comm.model.CustomerUnite;



/**
 * 
* 类名称： BeanUtils   
* 类描述：   
* 创建人：  huojianjun 
* 创建时间：2014-1-9 上午11:21:52   
* 修改人：   huojianjun  
* 修改时间：2014-1-9 上午11:21:52   
* 修改备注：      
*
 */
public class BeanUtils
{

	/**
	 * Map转成Bean
	 * @param type
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T mapToBean(Class<T> type, Map map) {
		T bean = null;
		try {
			bean = type.newInstance();
			Class c = bean.getClass();
			for (Object key : map.keySet()) {
				if (key instanceof String) {
					Field field = c.getDeclaredField((String) key);
					Object value = map.get(key);
					if (value != null) {
						field.setAccessible(true);
						field.set(bean, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * Bean转成Map
	 * @param bean
	 * @return
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map beanToMap(Object bean){
		Map<String, String> returnMap = new HashMap();
		try {
			Class c = bean.getClass();
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isFinal(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				String propertyName = field.getName();
				field.setAccessible(true);
				Object value = field.get(bean);
				if (value != null) {
					returnMap.put(propertyName, value.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) 
	{
		CustomerUnite ac = new CustomerUnite();
		ac.setInitPasswd("22222");
		ac.setCustomerRole(1);
		ac.sortType=1;
		ac.setCheckMobileTime("11111");

		Map map = new HashMap();

		long time1 = System.currentTimeMillis();

		// 改进后的效率
		map = beanToMap(ac);
		CustomerUnite bc = mapToBean(CustomerUnite.class, map);

		long time2 = System.currentTimeMillis();
		System.out.println("共用时：" + (time2 - time1) + "毫秒");

		System.out.println("title: " + ac.getInitPasswd());

	}
}
