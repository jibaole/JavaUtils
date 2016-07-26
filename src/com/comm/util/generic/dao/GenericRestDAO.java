package com.comm.util.generic.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.restlet.data.Method;

import com.comm.util.BaseResponseObject;
import com.comm.util.page.Page;

public interface GenericRestDAO<T extends Serializable, PK extends Serializable>
		extends Serializable {

	public Page getPageList(String url, String param);

	public List<T> getList(String url, String param);

	public T getById(String url);

	public BaseResponseObject create(Map obj, String url);

	public BaseResponseObject update(Map bo, String url);

	public BaseResponseObject delete(Map bo, String url);

	public BaseResponseObject get(String url, String param);

	public BaseResponseObject truncate(String url);

	public String getJsonList(String url, String param);

	public String getJsonPageList(String url, String param);

	public int getCount(String url, String queryJson);

	public BaseResponseObject execute(Map map, String url);

	public BaseResponseObject execute(Map map, String url, Method method);

	public Map executeMap(Map map, String url, Method method);

	public List<Map> getMapList(String url, String paramJsonString);

	// v2
	public Map getBaseResponseObject(String url, String queryJson);

	public Map callProc(Map map, String url);

}
