package com.comm.util.rest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.restlet.data.CharacterSet;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class RestInvokeUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RestInvokeUtil.class);

	public static String url_prefix = "http://api.allinmd.cn:18080/services/";

	// public static String url_prefix =
	// "http://api.allinmd.cn:80/allin_api_platform/services/";

	// public static String url_prefix = "http://192.168.1.225:8080/services/";

	// private String interface_url="";

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

		String invoke_url = url_prefix + interface_url;

		logger.error("invoke(String, Method, Form) - String invoke_url=" + invoke_url); //$NON-NLS-1$

		System.out.println("访问" + invoke_url);

		ClientResource res = new ClientResource(invoke_url);
		// + StringUtils.replace(interface_url, ".", "/")); // declare

		Representation rep = null;
		try {
			if (method.equals(Method.GET)) {
				rep = res.get();
			}

			if (method.equals(Method.POST)) {

				System.out.println("res=" + res);
				System.out.println("form=" + map);

				rep = res.post(map);
				
				
			}

			if (method.equals(Method.PUT)) {
				rep = res.put(map);
			}

			if (method.equals(Method.DELETE)) {
				rep = res.delete();
			}

			rep.setCharacterSet(CharacterSet.UTF_8);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (method != null) {
				try {
				//	res.release();
				} catch (Exception e) {
					logger.error("-------> Release HTTP connection exception:",
							e);
					e.printStackTrace();
				}

			}

		}

		return rep;

	}

public static String invokeMethod(String interface_url, Method method,Map map)throws Exception {
		String responseText="";
		String invoke_url = url_prefix + interface_url;
		logger.info("invoke(String, Method, map) - String invoke_url=" + invoke_url); //$NON-NLS-1$
		ClientResource res = new ClientResource(invoke_url);
		Representation rep = null;
		try {
			if (method.equals(Method.GET)) {
				rep = res.get();
			}
			if (method.equals(Method.POST)) {
				rep = res.post(map);
			}
			if (method.equals(Method.PUT)) {
				rep = res.put(map);
			}

			if (method.equals(Method.DELETE)) {
				rep = res.delete();
			}
			if(rep!=null){
				rep.setCharacterSet(CharacterSet.UTF_8);
				responseText=rep.getText();
			}			
		} catch (Exception ex) {
			logger.error("------->invokeMethod Release HTTP connection exception:"+invoke_url+"\r\n param:"+map);
			//throw new Exception("调用接口出现异常："+ex.getMessage());
			ex.printStackTrace();
		} finally {
				try {
					if(method != null && rep!=null && rep.isAvailable()){
						rep.release();
						res.release();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("------->rest 释放链接 exception:"+e.getMessage());
				}

		}

		return responseText;

	}

	public static String invokeURL(String url, Method method,Object map)throws Exception {
		String responseText="";
		logger.info("invoke(String, Method, map) - String invoke_url=" + url); //$NON-NLS-1$
		ClientResource res = new ClientResource(url);
		Representation rep = null;
		try {
			if (method.equals(Method.GET)) {
				rep = res.get();
			}
			if (method.equals(Method.POST)) {
				rep = res.post(map);
			}
			if (method.equals(Method.PUT)) {
				rep = res.put(map);
			}
	
			if (method.equals(Method.DELETE)) {
				rep = res.delete();
			}
	
			rep.setCharacterSet(CharacterSet.UTF_8);
			responseText=rep.getText();
		} catch (Exception ex) {
			logger.error("------->invokeMethod Release HTTP connection exception:"+url+"\r\n param:"+map);
			throw new Exception("调用接口出现异常："+ex.getMessage());
			//ex.printStackTrace();
		} finally {
				try {
					if(method != null && rep.isAvailable()){
						rep.release();
						res.release();
					}
				} catch (Exception e) {
					logger.error("------->rest 释放链接 exception:"+e.getMessage());
				}
	
		}
	
		return responseText;
	
	}
}
