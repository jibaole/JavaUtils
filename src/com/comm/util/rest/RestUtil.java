package com.comm.util.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;

public class RestUtil {
	public static String url_prefix = "http://api.allinmd.cn:18080/services/";

	private static Log log = LogFactory.getLog(RestUtil.class);

	/**
	 * 调用获取响应后数据
	 * 
	 * requestMethod 请求方式 GET/POST
	 * 
	 * interface_url 接口URL
	 * 
	 * @return 返回值
	 */
	/*
	 * public static String getResourceResponse(String requestMethod, String
	 * interface_url) { HttpURLConnection httpConnection = null; BufferedReader
	 * in = null; try { URL url = new URL(url_prefix + interface_url);
	 * httpConnection = (HttpURLConnection) url.openConnection();
	 * httpConnection.setDoOutput(true);
	 * httpConnection.setRequestMethod(requestMethod); int code =
	 * httpConnection.getResponseCode();
	 * 
	 * if (code == 200) { System.out.println("restEasy 响应成功"); }
	 * 
	 * StringBuffer sbResponse = new StringBuffer(); Reader reader = new
	 * InputStreamReader( httpConnection.getInputStream(), "utf-8");
	 * 
	 * in = new BufferedReader(reader); String line; while ((line =
	 * in.readLine()) != null) { sbResponse.append(line); }
	 * 
	 * System.out.println("==" + sbResponse.toString()); return
	 * sbResponse.toString();
	 * 
	 * } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * return null;
	 * 
	 * }
	 *//**
	 * 调用获取响应后数据
	 * 
	 * requestMethod 请求方式 GET/POST
	 * 
	 * interface_url 接口URL
	 * 
	 * @return 返回值
	 */
	/*
	 * public static String getResourceResponse(Method method, String
	 * interface_url) { HttpURLConnection httpConnection = null; BufferedReader
	 * in = null; try { ClientResource resource = new ClientResource(url_prefix
	 * + interface_url); resource.setMethod(method);
	 * 
	 * // resource.
	 * 
	 * Representation re = resource.get();
	 * 
	 * System.out.println("text:" + re.getText());
	 * 
	 * } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * return null; cart.get/{startTime},{endtime} order.get }
	 */

	public static Representation invoke(String interface_url, Method method,
			Map map) {
		// System.out.println("访问" + url_prefix + interface_url);
		// ClientResource res = new ClientResource(url_prefix + interface_url);
		// + StringUtils.replace(interface_url, ".", "/")); // declare

		AdapterRepresentation rep = new AdapterRepresentation();

		System.out.println("invoke url==" + url_prefix + interface_url);

		// Representation rep = null;

		if (method.equals(Method.GET)) {
			rep.setText(get(url_prefix + interface_url));
		}

		if (method.equals(Method.POST)) {
			try {
				rep.setText(post(url_prefix + interface_url,
						new UrlEncodedFormEntity(mapToList(map), "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
				rep.setText(null);
			}
		}

		if (method.equals(Method.PUT)) {
			try {
				rep.setText(put(url_prefix + interface_url,
						new UrlEncodedFormEntity(mapToList(map), "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
				rep.setText(null);
			}
		}

		if (method.equals(Method.DELETE)) {
			rep.setText(delete(url_prefix + interface_url));
		}

		try {

			// System.out.println("      结果rep=" + rep.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		rep.setCharacterSet(CharacterSet.UTF_8);

		return rep;

	}

	public static Representation invoke_deprive(String interface_url,
			Method method, Form form) {
		AdapterRepresentation rep = new AdapterRepresentation();

		log.debug(url_prefix + interface_url);

		if (method.equals(Method.GET)) {
			rep.setText(get(url_prefix + interface_url));
		}
		if (method.equals(Method.POST)) {
			try {
				rep.setText(post(url_prefix + interface_url,
						new UrlEncodedFormEntity(formToList(form), "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
				rep.setText(null);
			}
		}
		if (method.equals(Method.PUT)) {
			try {
				rep.setText(put(url_prefix + interface_url,
						new UrlEncodedFormEntity(formToList(form), "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
				rep.setText(null);
			}
		}
		if (method.equals(Method.DELETE)) {
			rep.setText(delete(url_prefix + interface_url));
		}

		return rep;
	}

	private static String get(String serviceUrl) {

		System.out.println("getList uri=" + serviceUrl);

		// serviceUrl=serviceUrl.replaceAll("\\{", "\\\\{");
		// serviceUrl=serviceUrl.replaceAll("\\}", "\\\\}");
		// serviceUrl=serviceUrl.replaceAll("\"", "\\"");

		// serviceUrl="http://api.allinmd.cn:18080/services/cms/video/getPageList?queryJson=\\{\"firstResult\":\"0\",\"maxResult\":\"10\"\\}";

		serviceUrl = "http://api.allinmd.cn:18080/services/cms/video/getPageList?queryJson=121232";

		System.out.println("getList uri 222=" + serviceUrl);

		HttpGet get = new HttpGet(serviceUrl);
		HttpResponse response = null;
		try {
			response = HttpConnectionManager.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.abort();
		}
		return null;
	}

	private static String getList(String serviceUrl) {

		HttpGet httpRequest = null;
		HttpResponse response = null;
		try {

			String paramStr = "";

			System.out.println("getList uri=" + serviceUrl);

			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				serviceUrl += paramStr;
			}

			serviceUrl.replaceAll("{", "\\{");
			serviceUrl.replaceAll("}", "\\}");

			System.out.println("getList uri 2=" + serviceUrl);

			httpRequest = new HttpGet(serviceUrl);

			/* 发送请求并等待响应 */
			// HttpResponse httpResponse = httpClient.execute(httpRequest);

			response = HttpConnectionManager.getHttpClient().execute(
					httpRequest);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, HTTP.UTF_8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpRequest.abort();
		}
		return null;
	}

	private static String post(String serviceUrl, HttpEntity reqEntity) {
		HttpPost post = new HttpPost(serviceUrl);
		HttpResponse response = null;
		try {
			// post.setHeader("Content-Type",
			// "application/x-www-form-urlencoded;charset=UTF-8");
			post.setEntity(reqEntity);

			response = HttpConnectionManager.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.abort();
		}
		return null;
	}

	private static String put(String serviceUrl, HttpEntity reqEntity) {
		HttpPut put = new HttpPut(serviceUrl);
		HttpResponse response = null;
		try {
			put.setHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			put.setEntity(reqEntity);
			response = HttpConnectionManager.getHttpClient().execute(put);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			put.abort();
		}
		return null;
	}

	private static String delete(String serviceUrl) {
		HttpDelete delete = new HttpDelete(serviceUrl);
		HttpResponse response = null;
		try {
			response = HttpConnectionManager.getHttpClient().execute(delete);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			delete.abort();
		}
		return null;
	}

	private static List<NameValuePair> formToList(Form form) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		if (form != null && !form.isEmpty()) {
			Iterator<Parameter> i = form.iterator();
			while (i.hasNext()) {
				Parameter p = i.next();
				postParameters.add(new BasicNameValuePair(p.getName(), p
						.getValue()));
			}
		}
		return postParameters;

	}

	private static List<NameValuePair> mapToList(Map map) {

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		if (map != null && !map.isEmpty()) {

			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();

				postParameters.add(new BasicNameValuePair(String.valueOf(key),
						String.valueOf(val)));
			}

			System.out.println("postParameters=" + postParameters.size());

		}

		System.out.println("postParameters=" + postParameters);
		return postParameters;

	}

}
