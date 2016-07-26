package com.comm.util.generic.dao.impl;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.util.PropertiesUtil;
//import org.apache.struts2.convention.StringTools;
import org.compass.core.util.CollectionUtils;

import com.comm.util.string.StringTool;


public class GenericSearchDAOImpl<T extends Serializable, PK extends Serializable> {

	String url_getPageList;

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericSearchDAOImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	// private String server_url =
	// "http://192.168.1.67/solr/c1_shard1_replica1";

	// private String cloud_server_url = "101.201.177.37:2181";
	private String cloud_server_url = "192.168.1.63:2181";

	// private String server_url =
	// "http://192.168.1.67/solr/c1_shard1_replica1";

	// CloudSolrServer server = new CloudSolrServer("localhost:9983");
	// server.setDefaultCollection("collection1");

	public CloudSolrServer getColudSolrServer(final String zkHost) {

		CloudSolrServer server = null;

		try {
			// return new SolrServer(server);

			/*
			 * String url = PropertiesUtil.getText("solr.serverUrl");
			 * 
			 * String username = PropertiesSon.getText("solr.username");
			 * 
			 * String password = PropertiesSon.getText("solr.password");
			 * 
			 * String host = Properties.getText("solr.host");
			 * 
			 * int port = Integer.parseInt(PropertiesSon.getText("solr.port"));
			 * 
			 * DefaultHttpClient httpclient = new DefaultHttpClient();
			 * 
			 * httpclient.getCredentialsProvider().setCredentials( new
			 * AuthScope(host, port), new UsernamePasswordCredentials(username,
			 * password));
			 */

			// Configuration conf = HBaseConfiguration.create();

			DefaultHttpClient httpclient = new DefaultHttpClient();

			// httpclient.set
			server = new CloudSolrServer(zkHost, httpclient);

			server.setDefaultCollection("c1");//allin
			
		//	server.setDefaultCollection("allin");//

			server.connect();

			// server.setSoTimeout(1000); // socket read timeout

			// server.setConnectionTimeout(100);

			// server.setDefaultMaxConnectionsPerHost(100);

			// server.setMaxTotalConnections(100);

			// server.setFollowRedirects(false); // defaults to false

			// allowCompression defaults to false.

			// Server side must support gzip or deflate for this to have any
			// effect. s

			// server.setAllowCompression(true);

			// server.setMaxRetries(1); // defaults to 0. > 1 not recommended.

		} catch (Exception e) {
			e.printStackTrace();
			// throw new
			// SearchInitException("Connect to solr server error use server '" +
			// server + "'");
		} finally {

		}

		return server;

	}

	/**
	 * 如果使用的是一个本地的solrServer的话
	 * 
	 * @return
	 */
	/*
	 * public EmbeddedSolrServer EmbeddedSolrServer(){ //the instance can be
	 * reused return null;// return new EmbeddedSolrServer(new Path,server_url);
	 * }
	 */

	/**
	 * 如果使用的是一个远程的solrServer的
	 * 
	 * @return
	 */
	public HttpSolrServer getSolrServer() {
		/*
		 * if (StringUtils.isEmpty(server_url)) { //
		 * logger.error("null solr server path!"); // throw new
		 * SearchInitException("Give a null solr server path"); }
		 */

		HttpSolrServer server = null;

		try {
			// return new SolrServer(server);

			// server = new HttpSolrServer(server_url);

			server.setSoTimeout(1000); // socket read timeout

			server.setConnectionTimeout(100);

			server.setDefaultMaxConnectionsPerHost(100);

			server.setMaxTotalConnections(100);

			server.setFollowRedirects(false); // defaults to false

			// allowCompression defaults to false.

			// Server side must support gzip or deflate for this to have any
			// effect. s

			server.setAllowCompression(true);

			server.setMaxRetries(1); // defaults to 0. > 1 not recommended.

		} catch (Exception e) {
			e.printStackTrace();
			// throw new
			// SearchInitException("Connect to solr server error use server '" +
			// server + "'");
		} finally {

		}

		return server;

	}

	public void destroy() {

	}

	/**
	 * 操作索引
	 * 
	 * @param indexType
	 * @param solrInputData
	 * @return
	 */

	public synchronized UpdateResponse doIndex(String indexType,
			List<SolrInputDocument> solrInputData) {
		UpdateResponse UpdateResponse = null;
		CloudSolrServer solrServer = null;
		try {

			List<SolrInputDocument> sids = null;

			if (!CollectionUtils.isEmpty(solrInputData)) {
				sids = new ArrayList<SolrInputDocument>();

				solrServer = null;
			//	solrServer = getColudSolrServer(cloud_server_url);
				solrServer = this.getColudSolrServer(cloud_server_url);
				for (SolrInputDocument sid : solrInputData) {
					// 初始化一些字段
					// sb.initPublicFields();
					// 保证每个对象的唯一性,而且通过对象的主键可以明确的找到这个对象在solr中的索引

					String replaceId = "uniqueKey-" + indexType + "-"
							+ sid.get("id").getValue().toString();

					System.out.println("replaceId===" + replaceId);

					sid.removeField("id");
					sid.addField("id", replaceId);
					sid.addField("indexType", indexType);

					// UpdateResponse=solrServer.add(sids, 10000);
					// UpdateResponse = solrServer.add(sid, 10000);
					// solrServer.setZkConnectTimeout(20000);
					// solrServer.setZkClientTimeout(20000);
					// UpdateResponse = solrServer.add(sid);

				//	sids.add(sid);
				//	break;

					solrServer.add(sid, 10000);
					
				}

				//UpdateResponse = solrServer.add(sids, 10000);

				//solrServer.commit();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				try {
					solrServer.close();
					// solrServer.shutdown();
					solrServer = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return UpdateResponse;

	}

	/**
	 * 创建索引
	 * 
	 * @param indexType
	 * @param solrInputData
	 * @return
	 */
	public synchronized UpdateResponse createIndex(String indexType,
			List<SolrInputDocument> solrInputData) {
		return this.doIndex(indexType, solrInputData);
	}

	/**
	 * 更新单个索引
	 * 
	 * @param indexType
	 * @param solrInputDocument
	 */
	public synchronized UpdateResponse updateIndex(String indexType,
			SolrInputDocument solrInputDocument) {
		return this.updateIndexList(indexType,
				Collections.singletonList(solrInputDocument));
	}

	/**
	 * 更新多個索引<br/>
	 * 在solr中更新索引也就是创建索引(当有相同ID存在的时候,仅仅更新,否则新建)<br/>
	 * {@link SolrSearchEngine#doIndex(java.util.List)}
	 * 
	 * @param solrInputData
	 *            需要更新的beans
	 * @throws Exception
	 */
	public synchronized UpdateResponse updateIndexList(String indexType,
			List<SolrInputDocument> solrInputData) {
		return this.doIndex(indexType, solrInputData);
	}

	/**
	 * paramMap :
	 * 
	 * firstResult maxResult keyword
	 * 
	 * 话题，病例，人，视频查询使用
	 * 
	 * @param paramMap
	 * @return
	 */

	public SolrDocumentList query(Map paramMap) {

		SolrDocumentList list = null;
		QueryResponse returnQueryResponse = null;
		CloudSolrServer solrServer = null;

		try {

			System.out.println("query paramMap=" + paramMap);

			String firstResult = StringTool.getMapString(paramMap,
					"firstResult");
			String maxResult = StringTool.getMapString(paramMap, "maxResult");

			Map eqMap = null; // 精确匹配查询的参数集合
			Map likeMap = null;// 模糊匹配查询的参数集合
			Map orderMap = null;// 排序查询的参数集合(与传入的顺序有关)

			if (paramMap.containsKey("eqMap")) {
				eqMap = (Map) paramMap.get("eqMap");
			}

			if (paramMap.containsKey("likeMap")) {
				likeMap = (Map) paramMap.get("likeMap");
			}

			if (paramMap.containsKey("orderMap")) {
				orderMap = (Map) paramMap.get("orderMap");
			}

			solrServer = this.getColudSolrServer(cloud_server_url);

			SolrQuery q = new SolrQuery();
			
			String indexType = "";
			StringBuffer sb = new StringBuffer("(");
			// 处理精确匹配查询的参数
			if (eqMap != null) {		
				for (Object obj : eqMap.keySet()) {
					if (!"indexType".equals(obj.toString())) {
						sb.append(obj.toString());
						sb.append(":");

						sb.append(eqMap.get(obj).toString() + " OR ");
					} else if ("indexType".equals(obj.toString())) {
						indexType = eqMap.get(obj).toString();
					}
					System.out.println("query sb=" + sb.toString());
				}
				if (sb.length() > 3) {
					  String query=sb.toString().substring(0,sb.length()-3);
			         //添加索引查询条件
			          if(!"doc".equals(indexType) && !StringUtils.isEmpty(indexType)){
			        	  q.setQuery(query+" ) AND indexType:"+indexType);
			          }else if("doc".equals(indexType)){
			        	  q.setQuery(query+" ) AND attr_indextype:"+indexType); 
			          }
				}
			} else {
				q.setQuery("*:*");
			}

			// 处理模糊匹配查询的参数
			if (likeMap != null) {			
				for (Object obj : likeMap.keySet()) {
					if(!"indexType".equals(obj.toString()) && !"attr_indextype".equals(obj.toString()) ){
						sb.append(obj.toString());
						sb.append(":");
						sb.append("*");
						sb.append(likeMap.get(obj).toString());
						sb.append("*" + " OR ");
					}else{
						indexType=likeMap.get(obj).toString();
					}
					System.out.println("query sb=" + sb.toString());
					// q.addFilterQuery(sb.toString());
					// q.setQuery(sb.toString());
				}
				if (sb.length() > 3) {
					  String query=sb.toString().substring(0,sb.length()-3);
			         //添加索引查询条件
			          if(!"doc".equals(indexType)){
			        	  q.setQuery(query+" ) AND indexType:"+indexType);
			          }else if("doc".equals(indexType)){
			        	  q.setQuery(query+" ) AND attr_indextype:"+indexType); 
			          }
				}
			}
			// 处理排序参数（与传入的顺序有关）

			System.out.println("orderMap=" + orderMap);

			if (orderMap != null) {

				for (Object obj : orderMap.keySet()) {

					String order_flag = StringTool.getMapString(orderMap,
							obj.toString());

					if (order_flag.length() > 0) {

						switch (Integer.parseInt(order_flag)) {
						case 1:// 升序
							q.addSort(obj.toString(), SolrQuery.ORDER.asc);
							break;
						case 2:// 降序
							q.addSort(obj.toString(), SolrQuery.ORDER.desc);
							break;
						default:
							;
						}

					}
				}
			}

			q.setStart(Integer.parseInt(firstResult));
			q.setRows(Integer.parseInt(maxResult));
			System.out.println("query =" + q.getMap());
			System.out.println("solrServer =" + solrServer);

			if("topic".equals(indexType)){
				 q.setFields("topicId"); 		
			}else if("case".equals(indexType)){
				q.setFields("caseId");				
			}else if("doc".equals(indexType)){
				q.setFields("id");						
			}else if("customer".equals(indexType)){
				q.setFields("customerId");	
				
			}	
			list = solrServer.query(q).getResults();
			System.out.println("solrServer.query(q)==="+solrServer.query(q));
			System.out.println("list  numFound==="+list.getNumFound());
			solrServer.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return list;

	}

	public synchronized UpdateResponse deleteIndexById(String indexType,
			String idValue) {

		UpdateResponse returnUpdateResponse = null;
		CloudSolrServer solrServer = null;
		try {
			solrServer = this.getColudSolrServer(cloud_server_url);

			StringBuffer sb = new StringBuffer("");
			sb.append("id:");
			sb.append("uniqueKey-" + indexType + "-");
			sb.append(idValue);

			System.out.println("query sb=" + sb.toString());

			returnUpdateResponse = solrServer.deleteByQuery(sb.toString());

			solrServer.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return returnUpdateResponse;

	}

	public synchronized UpdateResponse deleteByIndexType(String indexType) {
		UpdateResponse ur = null;
		CloudSolrServer solrServer = null;
		try {
			solrServer = this.getColudSolrServer(cloud_server_url);
			ur = solrServer.deleteByQuery("indexType:" + indexType);

			solrServer.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return ur;

	}

	public synchronized UpdateResponse deleteDocByIndexType(String indexType) {
		UpdateResponse ur = null;
		CloudSolrServer solrServer = null;
		try {
			solrServer = this.getColudSolrServer(cloud_server_url);
			ur = solrServer.deleteByQuery("attr_indextype:" + indexType);

			solrServer.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return ur;

	}
	
	public synchronized UpdateResponse deleteAll() {
		UpdateResponse ur = null;
		CloudSolrServer solrServer = null;
		try {
			solrServer = this.getColudSolrServer(cloud_server_url);
			ur = solrServer.deleteByQuery("*:*");

			solrServer.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return ur;

	}

	public synchronized ContentStreamUpdateRequest doFileIndex(
			String indexType, Map<String, String> docParamMap, Map fileParamMap) {
		ContentStreamUpdateRequest up = null;
		CloudSolrServer solrServer = null;
		try {

			System.out.println("doFileIndex  9999999=" + 99999999);

			// String contentType = "application/pdf";
			String contentType = StringTool.getMapString(fileParamMap,
					"contentType");// application/pdf

			String file_name = StringTool.getMapString(fileParamMap,
					"file_name");

			String id = StringTool.getMapString(docParamMap, "id");

			System.out.println("file_name=" + file_name);

			// File file = new File(file_name);

			up = new ContentStreamUpdateRequest("/update/extract");
			String replaceId = "uniqueKey-" + indexType + "-" + id;

			// String contentType = "application/pdf";
			System.out.println("contentType=" + contentType);

			// up.addFile(new File("/root/3.pdf"), "application/pdf");
			up.addFile(new File(file_name), "application/pdf");

			// up.addFile(new File(file_name),);

		
			for (Map.Entry<String, String> entry : docParamMap.entrySet()) {
				System.out.println("key = " + entry.getKey() + " | value = "
						+ StringTool.getMapString(docParamMap, entry.getKey()));
				
				if(!entry.getKey().equalsIgnoreCase("id")){
					up.setParam("literal." + entry.getKey(),
							StringTool.getMapString(docParamMap, entry.getKey()));

				}
				
				
			}
			

			up.setParam("literal.id", replaceId);
			up.setParam("literal.indexType", "doc");

			up.setParam("uprefix", "attr_");
			up.setParam("fmap.content", "attr_content");
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
			solrServer = this.getColudSolrServer(cloud_server_url);
			solrServer.request(up);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return up;

	}

	public static void main(String[] args) {

		/*
		 * GenericRestDAOImpl.getBaseResponseObject(
		 * "customer/follow/people/getMapList",
		 * "{firstResult:\"1\",maxResult:\"2\"}");
		 */

		GenericSearchDAOImpl GenericSearchDAOImpl = new GenericSearchDAOImpl();

		// GenericSearchDAOImpl.doFileIndex();
		try {
			// String urlString = "http://localhost:8080/solr/core1";
			CloudSolrServer solr = GenericSearchDAOImpl
					.getColudSolrServer(GenericSearchDAOImpl.cloud_server_url);
			ContentStreamUpdateRequest up = new ContentStreamUpdateRequest(
					"/update/extract");

			String contentType = "application/pdf";
			up.addFile(new File("/root/1.pdf"), contentType);
			up.setParam("literal.id", "123");
			up.setParam("uprefix", "attr_");
			up.setParam("fmap.content", "attr_content");
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);

			solr.request(up);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 获取doc数据
	 * 
	 * @param paramMap
	 * @return
	 */

	public SolrDocumentList getDocList(Map paramMap) {

		SolrDocumentList list = null;

		QueryResponse returnQueryResponse = null;

		CloudSolrServer solrServer = null;

		try {

			System.out.println("query paramMap=" + paramMap);

			String firstResult = StringTool.getMapString(paramMap,
					"firstResult");
			String maxResult = StringTool.getMapString(paramMap, "maxResult");

			Map eqMap = null; // 精确匹配查询的参数集合
			Map likeMap = null;// 模糊匹配查询的参数集合
			Map orderMap = null;// 排序查询的参数集合(与传入的顺序有关)

			if (paramMap.containsKey("eqMap")) {
				eqMap = (Map) paramMap.get("eqMap");
			}

			if (paramMap.containsKey("likeMap")) {
				likeMap = (Map) paramMap.get("likeMap");
			}

			if (paramMap.containsKey("orderMap")) {
				orderMap = (Map) paramMap.get("orderMap");
			}

			solrServer = this.getColudSolrServer(cloud_server_url);

			SolrQuery q = new SolrQuery();
			// q.setQuery("id:uniqueKey-customer-1397586886349");

			// 处理精确匹配查询的参数
			if (eqMap != null) {
				StringBuffer sb = new StringBuffer("(");
				for (Object obj : eqMap.keySet()) {
					if (!"attr_indextype".equals(obj.toString())) {
						sb.append(obj.toString());
						sb.append(":");
						sb.append(eqMap.get(obj).toString() + " OR ");
					}
					System.out.println("query sb=" + sb.toString());

				}
				if (sb.length() > 3) {
					String query = sb.toString().substring(0, sb.length() - 3);
					// 添加索引查询条件
					q.setQuery(query + " ) AND attr_indextype:doc");
				}
			} else {
			//	q.setQuery("*:*");
				
				q.setQuery("attr_indextype:doc");
			}

			System.out.println("eqMap  a=" + q.getQuery());
			
			// 处理模糊匹配查询的参数

			if (likeMap != null) {
				StringBuffer sb = new StringBuffer("");
				for (Object obj : likeMap.keySet()) {
					sb.append(obj.toString());
					sb.append(":");
					sb.append("*");
					sb.append(likeMap.get(obj).toString());
					sb.append("*" + " OR ");
					System.out.println("query sb=" + sb.toString());
					// q.addFilterQuery(sb.toString());
					// q.setQuery(sb.toString());
				}
				if (sb.length() > 3) {
					String query = sb.toString().substring(0, sb.length() - 3);
					q.setQuery(query);
				}
			}

			// 处理排序参数（与传入的顺序有关）

			System.out.println("orderMap=" + orderMap);

			if (orderMap != null) {

				for (Object obj : orderMap.keySet()) {

					String order_flag = StringTool.getMapString(orderMap,
							obj.toString());

					if (order_flag.length() > 0) {

						switch (Integer.parseInt(order_flag)) {
						case 1:// 升序
							q.addSort(obj.toString(), SolrQuery.ORDER.asc);
							break;
						case 2:// 降序
							q.addSort(obj.toString(), SolrQuery.ORDER.desc);
							break;
						default:
							;
						}

					}
				}
			}

			q.setStart(Integer.parseInt(firstResult));
			q.setRows(Integer.parseInt(maxResult));
			
			System.out.println("query =" + q.getMap());
			System.out.println("solrServer =" + solrServer);
			System.out.println("solrServer.query(q) =" + solrServer.query(q));

			list = solrServer.query(q).getResults();
		
			System.out.println("query list=" + list.getNumFound());

			// returnQueryResponse = solrServer.query(paramMap);

			solrServer.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (solrServer != null) {
				solrServer.shutdown();
				solrServer = null;
			}
		}

		return list;

	}

}