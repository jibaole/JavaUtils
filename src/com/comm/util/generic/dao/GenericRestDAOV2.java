package com.comm.util.generic.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.restlet.data.Method;

import com.comm.util.BaseResponseObject;
import com.comm.util.page.Page;

@SuppressWarnings("rawtypes")
public interface GenericRestDAOV2<T extends Serializable, PK extends Serializable>
		extends Serializable {

	public BaseResponseObject execute(Map map, String url);
	
	public BaseResponseObject execute(Map bo, String url, Method method);

	public Map executeMap(Map map, String url, Method method);
	
	public String executeURL(Map bo, String url, Method method);
	
	public Page getPageList(String url, String param);

	List<T> getList(String url, String param);

	public T getById(String url);
	
	public int getCount(String url, Map paramMap);

	public Map getMapById(String url);

	public Map create(Map obj, String url);

	public Map update(Map bo, String url);

	public Map delete(Map bo, String url);

	public Map getBaseResponseObject(String url, String queryJson);

}
