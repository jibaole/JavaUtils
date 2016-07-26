package com.comm.util.json;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.comm.util.datetime.DateTimeUtil;

public class JsonUtil {
	private static final Logger logger = Logger.getLogger(JsonUtil.class);

	public static String getJsonString4JavaPOJO(Object javaObj) {
		JSONObject json = JSONObject.fromObject(javaObj);

		return json.toString();
	}

	public static String getJsonString4JavaPOJO(Object javaObj,
			String dataFormat) {
		JsonConfig jsonConfig = configJson(dataFormat);

		JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);

		return json.toString();
	}

	public static String getJsonArrayString4JavaPOJO(Object javaObj,
			String dataFormat) {
		JsonConfig jsonConfig = configJson(dataFormat);

		JSONArray json = JSONArray.fromObject(javaObj, jsonConfig);

		return json.toString();
	}

	public static JSONObject objectcollect2json(List list, int total) {
		Map map = new HashMap();
		map.put("total", Integer.valueOf(total));
		map.put("rows", list);
		return JSONObject.fromObject(map);
	}

	public static void main(String[] args) {
		getSplit("dewdew");
	}

	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.setExcludes(new String[] { "" });

		jsonConfig.setIgnoreDefaultExcludes(false);

		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.setExcludes(excludes);

		jsonConfig.setIgnoreDefaultExcludes(false);

		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		logger.error("getObject4JsonString(String, Class) - String jsonString="
				+ jsonString);

		logger.error("getObject4JsonString(String, Class) - pojoCalss="
				+ pojoCalss.getName());

		Object pojo = null;
		try {
			

			jsonString = jsonString + "  ";

			logger.error("getObject4JsonString(String, Class) - jsonObject="
					+ jsonString.length());

			JSONObject jsonObject = JSONObject.fromObject(jsonString);

			logger.error("getObject4JsonString(String, Class) - jsonObject="
					+ jsonObject);

			pojo = JSONObject.toBean(jsonObject, pojoCalss);

			logger.error("getObject4JsonString(String, Class) - pojo=" + pojo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pojo;
	}

	public static List getDTOList(String jsonString, Class clazz,
			String dataFormat) {
		JsonConfig jsonConfig = configJson(dataFormat);
		JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}
	
	
	public static List getDTOListWithTime(String jsonString, Class clazz,
			String dataFormat, String[] str) {

		JsonConfig jsonConfig = configJson(dataFormat);
		JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
		List list = new ArrayList();

		for (Iterator iter = array.iterator(); iter.hasNext();) {

			Object obj = iter.next();

			// System.out.println("obj=" + obj);

			JSONObject jsonObject = (JSONObject) obj;

			// System.out.println("obj2="
			// + JSONObject.fromObject(jsonObject, jsonConfig));
			// System.out.println("obj3=" + jsonObject.get("birthday"));

			if (str != null && str.length > 0) {
				for (String str1 : str) {

					if(str1!=null && str1.length()>0 && jsonObject.get(str1)!=null && !"null".equals(String.valueOf(jsonObject.get(str1)))){
						System.out.println("obj  parseLong="
								+ DateTimeUtil.parseLong((Long) jsonObject
										.get(str1)));

						System.out.println("obj  getTime ="
								+ DateTimeUtil.getTime(DateTimeUtil
										.parseLong((Long) jsonObject.get(str1))));

						// jsonObject.put(str1, DateTimeUtil.getTime(DateTimeUtil
						// .parseLong((Long) jsonObject.get(str1))));

						jsonObject
								.put(str1, DateTimeUtil.parseLong((Long) jsonObject
										.get(str1)));
					}
				}
			}

			list.add(JSONObject.toBean(jsonObject, clazz));

		}

		/*
		 * JsonConfig jsonConfig = configJson(dataFormat); JSONObject jsonObject
		 * = JSONObject.fromObject(jsonString);
		 * 
		 * List list = new ArrayList(); for (Iterator iter = jsonObject.keys();
		 * iter.hasNext();) { String key = (String) iter.next(); JSONArray array
		 * = jsonObject.getJSONArray(key);
		 * 
		 * for (int i = 0; i < array.size(); ++i) { JSONObject object =
		 * (JSONObject) array.get(i);
		 * 
		 * Object t = JSONObject.fromObject(jsonObject, jsonConfig);
		 * 
		 * if (t != null) { list.add(t); } } }
		 */

		return list;
	}
	
	public static Object getDTOObjectWithTime(String jsonString, Class clazz,
			String dataFormat, String[] str) {

		JSONObject jsonObject = JSONObject.fromObject(jsonString);

		if (str != null && str.length > 0) {
			for (String str1 : str) {

				if(str1!=null && str1.length()>0 && jsonObject.get(str1)!=null && !"null".equals(String.valueOf(jsonObject.get(str1)))){
					System.out.println("obj  parseLong="
							+ DateTimeUtil.parseLong((Long) jsonObject
									.get(str1)));

					System.out.println("obj  getTime ="
							+ DateTimeUtil.getTime(DateTimeUtil
									.parseLong((Long) jsonObject.get(str1))));

					jsonObject
							.put(str1, DateTimeUtil.parseLong((Long) jsonObject
									.get(str1)));
				}
			}
		}
		
		return JSONObject.toBean(jsonObject, clazz);
	}

	public static Object getObject4JsonStringForBack(String jsonString,
			Class pojoCalss) {
		logger.error("getObject4JsonString(String, Class) - String jsonString="
				+ jsonString);

		logger.error("getObject4JsonString(String, Class) - pojoCalss="
				+ pojoCalss.getName());

		Object pojo = null;
		try {
			setDataFormat2JAVA();

			logger.error("getObject4JsonString(String, Class) - jsonObject="
					+ jsonString.length());

			JSONObject jsonObject = JSONObject.fromObject(jsonString);

			logger.error("getObject4JsonString(String, Class) - jsonObject="
					+ jsonObject);

			pojo = JSONObject.toBean(jsonObject, pojoCalss);

			logger.error("getObject4JsonString(String, Class) - pojo=" + pojo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pojo;
	}

	private static void setDataFormat2JAVA() {
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd",
						"yyyy-MM-dd HH:mm:ss" }));
	}

	public static String toChinese(String strvalue) {
		try {
			if (strvalue == null) {
				return "";
			}
			strvalue = new String(strvalue.getBytes("GBK"), "UTF-8").trim();
			return strvalue;
		} catch (Exception e) {
		}
		return "";
	}

	public static List getJsonData(Object obj, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(obj);

		List list = new ArrayList();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			JSONArray array = jsonObject.getJSONArray(key);

			for (int i = 0; i < array.size(); ++i) {
				JSONObject object = (JSONObject) array.get(i);

				Object t = JSONObject.toBean(object, clazz);

				if (t != null) {
					list.add(t);
				}
			}
		}

		return list;
	}

	public static String[] getStringArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); ++i) {
			stringArray[i] = jsonArray.getString(i);
		}

		return stringArray;
	}

	public static List getList4Json(String jsonString, Class pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Object pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}

		return list;
	}

	public static String getSplit(String s) {
		int i = s.lastIndexOf("z");

		if (i == -1) {
			return s;
		}
		return s.substring(0, i);
	}

	public static String getPageItemsJsonFromPageJsonString(
			String pageJsonString) {
		String jsonString = null;
		if ((pageJsonString.indexOf(":[") < -1)
				&& (pageJsonString.indexOf(":[") < pageJsonString.indexOf("]"))) {
			jsonString = pageJsonString.substring(
					pageJsonString.indexOf(":[") + 1,
					pageJsonString.indexOf("]") + 1);
		}

		return jsonString;
	}
	
	
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static String getJsonPString(Object obj,String function) {
		String jsonResult="";
		if(obj!=null && obj!=""){
			if(obj instanceof Collection){
				jsonResult=JsonUtil.getJsonArrayString4JavaPOJO(
						obj, "yyyy-MM-dd HH:mm:ss");
			}else{
				jsonResult=JsonUtil.getJsonString4JavaPOJO(
						obj, "yyyy-MM-dd HH:mm:ss");
			}
		}
		StringBuffer strbuffer = new StringBuffer("");
		if (StringUtils.isNotEmpty(function)) {
			strbuffer.append(function).append("(").append(jsonResult)
					.append(")");
		} else {
			strbuffer.append("jsonpCallback").append("(").append(jsonResult)
			.append(")");
		}
		return strbuffer.toString();
	}
}