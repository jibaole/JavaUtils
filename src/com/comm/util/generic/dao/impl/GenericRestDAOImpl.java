package com.comm.util.generic.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Method;
import org.restlet.representation.Representation;

import com.comm.util.BaseResponseObject;
import com.comm.util.MapUtil;
import com.comm.util.json.JsonUtil;
import com.comm.util.page.Page;
import com.comm.util.rest.RestInvokeUtil;

public class GenericRestDAOImpl<T extends Serializable, PK extends Serializable> {

	String url_getPageList;

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericRestDAOImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	/**
	 * 
	 * @param bo
	 * @param url
	 * @return
	 */
	public Page getPageList(String url, String paramJsonString) {

		String conn_url = assembleUrl(url, paramJsonString);

		Page pageObj = new Page();

		Representation representation = RestInvokeUtil.invoke(conn_url,
				Method.GET, null);

		representation.setCharacterSet(CharacterSet.UTF_8);

		try {
			String text = representation.getText();
			if (StringUtils.isNotBlank(text)) {
				JSONObject json = JSONObject.fromObject(text);
				List<Map<String, Object>> list = (List<Map<String, Object>>) json
						.get("items");
				if (list != null && list.size() > 0) {
					pageObj = (Page) JsonUtil.getObject4JsonString(text,
							Page.class);
				}
				return pageObj;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return pageObj;

	}

	public List<T> getList(String url, String paramJsonString) {
		List<T> list = null;
		try {

			String conn_url = assembleUrl(url, paramJsonString);

			Representation representation = RestInvokeUtil.invoke(conn_url,
					Method.GET, null);

			representation.setCharacterSet(CharacterSet.UTF_8);

			String jsonResultString = representation.getText();
			if (StringUtils.isNotEmpty(jsonResultString)){
				jsonResultString = jsonResultString.substring(
						jsonResultString.indexOf(":[") + 1,
						jsonResultString.lastIndexOf("]") + 1);
			}

			if (StringUtils.isNotEmpty(jsonResultString)
					&& !jsonResultString.equalsIgnoreCase("[]")) {
				list = (List<T>) JsonUtil.getDTOList(jsonResultString,
						entityClass, "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;

	}

	public T getById(String url) {

		T t = null;

		// 调用接口
		Representation representation = RestInvokeUtil.invoke(url, Method.GET,
				null);
		String jsonString = null;
		try {
			jsonString = representation.getText();

			if (StringUtils.isNotEmpty(jsonString)
					&& !jsonString.equalsIgnoreCase("[]")) {
				t = (T) JsonUtil.getObject4JsonString(jsonString, entityClass);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return t;

	}

	public BaseResponseObject create(Map obj, String url) {

		BaseResponseObject baseResponseObject = null;
		try {

			Representation representation = RestInvokeUtil.invoke(url,
					Method.POST, obj);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public BaseResponseObject delete(Map map, String url) {
		BaseResponseObject baseResponseObject = null;

		try {

			Representation representation = RestInvokeUtil.invoke(url,
					Method.POST, map);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public Map callProc(Map map, String url) {
		BaseResponseObject baseResponseObject = null;

		try {

			Representation representation = RestInvokeUtil.invoke(url,
					Method.PUT, map);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return MapUtil.transBean2Map(baseResponseObject);
	}

	public BaseResponseObject update(Map map, String url) {
		BaseResponseObject baseResponseObject = null;

		try {

			Representation representation = RestInvokeUtil.invoke(url,
					Method.PUT, map);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public BaseResponseObject truncate(String url) {
		BaseResponseObject baseResponseObject = null;

		try {

			Map map = new HashMap();

			Representation representation = RestInvokeUtil.invoke(url,
					Method.POST, map);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public BaseResponseObject get(String url, String queryJson) {

		BaseResponseObject baseResponseObject = null;
		try {

			String conn_url = assembleUrl(url, queryJson);

			Representation representation = RestInvokeUtil.invoke(conn_url,
					Method.GET, null);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public Map getBaseResponseObject(String url, String queryJson) {

		Map map = null;
		try {

			String conn_url = assembleUrl(url, queryJson);

			Representation representation = RestInvokeUtil.invoke(conn_url,
					Method.GET, null);

			representation.setCharacterSet(CharacterSet.UTF_8);

			String text = representation.getText();

			System.out.println("ad=" + text);

			if (StringUtils.isNotBlank(text)) {
				map = JsonUtil.parseJSON2Map(text);
				System.out.println("m=" + map.get("responseMapData"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	public String getJsonList(String url, String paramJsonString) {

		String conn_url = assembleUrl(url, paramJsonString);

		Representation representation = RestInvokeUtil.invoke(conn_url,
				Method.GET, null);

		representation.setCharacterSet(CharacterSet.UTF_8);

		String returnString = "";

		try {
			returnString = representation.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String getJsonPageList(String url, String paramJsonString) {

		String conn_url = assembleUrl(url, paramJsonString);

		Representation representation = RestInvokeUtil.invoke(conn_url,
				Method.GET, null);
		representation.setCharacterSet(CharacterSet.UTF_8);

		String returnString = "";

		try {
			returnString = representation.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public int getCount(String url, String queryJson) {
		String result = "";
		try {

			String conn_url = assembleUrl(url, queryJson);

			Representation representation = RestInvokeUtil.invoke(conn_url,
					Method.GET, null);

			representation.setCharacterSet(CharacterSet.UTF_8);

			result = representation.getText();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Integer.valueOf(result);

	}

	protected String assembleUrl(String interface_url, String paramJsonString) {

		return new StringBuffer("").append(interface_url).append("?queryJson=")
				.append(paramJsonString).toString();
	}

	public List<Map> getMapList(String url, String paramJsonString) {
		List<Map> list = null;
		try {
			String conn_url = assembleUrl(url, paramJsonString);

			Representation representation = RestInvokeUtil.invoke(conn_url,
					Method.GET, null);
			representation.setCharacterSet(CharacterSet.UTF_8);
			String jsonResultString = representation.getText();
			jsonResultString = jsonResultString.substring(
					jsonResultString.indexOf(":[") + 1,
					jsonResultString.indexOf("]") + 1);

			if (StringUtils.isNotEmpty(jsonResultString)
					&& !jsonResultString.equalsIgnoreCase("[]")) {
				list = (List<Map>) JsonUtil.getDTOList(jsonResultString,
						Map.class, "yyyy-MM-dd hh:mm:ss");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;

	}

	public BaseResponseObject execute(Map map, String url) {
		BaseResponseObject baseResponseObject = new BaseResponseObject(false,
				"", "");
		try {
			String text = RestInvokeUtil.invokeMethod(url, Method.POST, map);
			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			// logger.error("--------->update exception:"+ex.getMessage());
			// ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public BaseResponseObject execute(Map map, String url, Method method) {
		BaseResponseObject baseResponseObject = new BaseResponseObject(false,
				"", "");
		try {
			if (Method.GET.equals(method)) {
				String paramJsonString = JsonUtil.getJsonString4JavaPOJO(map);
				url = assembleUrl(url, paramJsonString);
			}
			String text = RestInvokeUtil.invokeMethod(url, method, map);
			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			// logger.error("--------->update exception:"+ex.getMessage());
			// ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public BaseResponseObject upload(Map obj, String url) {

		BaseResponseObject baseResponseObject = null;
		try {

			Representation representation = RestInvokeUtil.invoke(url,
					Method.POST, obj);

			representation.setCharacterSet(CharacterSet.UTF_8);
			String text = representation.getText();

			if (StringUtils.isNotEmpty(text)) {
				baseResponseObject = (BaseResponseObject) JsonUtil
						.getObject4JsonString(text, BaseResponseObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseResponseObject;
	}

	public Map executeMap(Map map, String url, Method method) {
		Map respMap = null;
		try {
			if (Method.GET.equals(method)) {
				String paramJsonString = JsonUtil.getJsonString4JavaPOJO(map);
				url = assembleUrl(url, paramJsonString);
			}
			String text = RestInvokeUtil.invokeMethod(url, method, map);
			if (StringUtils.isNotEmpty(text)) {
				respMap = (Map) JsonUtil.getObject4JsonString(text, Map.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respMap;
	}

	public static void main(String[] args) {

		/*
		 * GenericRestDAOImpl.getBaseResponseObject(
		 * "customer/follow/people/getMapList",
		 * "{firstResult:\"1\",maxResult:\"2\"}");
		 */

	}

}