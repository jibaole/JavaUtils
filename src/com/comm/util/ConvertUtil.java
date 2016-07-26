package com.comm.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.restlet.data.Form;

import com.comm.model.TbRecruitApplicantProfile;
import com.comm.util.json.GsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConvertUtil {

	@SuppressWarnings({ "rawtypes" })
	public static HashMap objToHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		try {
			JSONObject jsonObject = toJSONObject(object);
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				Object value = jsonObject.get(key);

				System.out.println("key=" + key + ";value=" + value);

				data.put(key, value);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	public static Map jsonStringToHashMap(String str) {
		return GsonUtil.parseGsonToMap(str);
	}

	private static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	public static Form objToForm(Object obj) {

		Form form = new Form();
		try {
			Field[] allDeclaredField = obj.getClass().getDeclaredFields();
			Field[] allPublicField = obj.getClass().getFields();
			HashMap<String, Field> fieldMap = new HashMap<String, Field>();
			for (Field field : allDeclaredField) {
				fieldMap.put(field.getName(), field);
			}
			for (Field field : allPublicField) {
				fieldMap.put(field.getName(), field);
			}
			for (Entry<String, Field> fieldEntry : fieldMap.entrySet()) {
				String varName = fieldEntry.getKey();
				try {
					boolean accessFlag = fieldEntry.getValue().isAccessible();
					// 修改访问控制权限
					fieldEntry.getValue().setAccessible(true);
					Object o = fieldEntry.getValue().get(obj);
					if (o != null)
						form.add(varName, o.toString());
					fieldEntry.getValue().setAccessible(accessFlag);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return form;
	}

	public static Map<String, String> objToMap(Object obj) {

		Map<String, String> map = new HashMap<String, String>();
		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;

	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		TbRecruitApplicantProfile tbWebSysMenu = new TbRecruitApplicantProfile();

		tbWebSysMenu.setHighEducation("111");
		tbWebSysMenu.setPage(1);
		// Map map = ReflectUtil.getValueMap(tbWebSysMenu);
		//
		// for (Object dataKey : map.keySet()) {
		//
		// System.out.println(dataKey);
		// System.out.println(map.get(dataKey));
		//
		// }

		//
		Form form = ConvertUtil.objToForm(tbWebSysMenu);
		Object obj;
		for (Iterator iterator = form.iterator(); iterator.hasNext();) {
			obj = iterator.next();
			System.out.println(obj.toString());
		}

	}

}
