/*package com.comm.util.generic.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allin.ws.business.cms.video.dao.VideoDAO;
import com.allin.ws.business.log.cms.caseinfo.dao.LogCaseAttachmentDAO;
import com.allin.ws.business.log.cms.caseinfo.dao.LogCaseBaseinfoDAO;
import com.allin.ws.business.log.cms.caseinfo.dao.LogCaseSupplementDAO;
import com.allin.ws.business.log.cms.caseinfo.dao.LogCaseTagDAO;
import com.allin.ws.business.log.cms.customer.dao.LogCustomerAuthDAO;
import com.allin.ws.business.log.cms.customer.dao.LogCustomerBaseinfoDAO;
import com.allin.ws.business.log.cms.customer.dao.LogCustomerUniteInfoDAO;
import com.allin.ws.business.log.cms.doc.dao.LogCmsDocAuthorDAO;
import com.allin.ws.business.log.cms.doc.dao.LogCmsDocContentDAO;
import com.allin.ws.business.log.cms.doc.dao.LogCmsDocDAO;
import com.allin.ws.business.log.cms.doc.dao.LogCmsDocTagDAO;
import com.allin.ws.business.log.cms.topic.dao.LogCmsTopicAttachmentDAO;
import com.allin.ws.business.log.cms.topic.dao.LogCmsTopicDAO;
import com.allin.ws.business.log.cms.topic.dao.LogCmsTopicTagDAO;
import com.allin.ws.business.log.cms.video.dao.LogCmsVideoAttachmentDAO;
import com.allin.ws.business.log.cms.video.dao.LogCmsVideoAuthorDAO;
import com.allin.ws.business.log.cms.video.dao.LogCmsVideoDAO;
import com.allin.ws.business.log.cms.video.dao.LogCmsVideoTagDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerCollectionDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerContinuingEducationDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerEducationDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFellowshipAttachmentDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFellowshipDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFellowshipStateDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFellowshipSubDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFollowFansDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFollowPeopleDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFollowResourceDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerFundDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerHonorDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerOccupationDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerOpusDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerPatentDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerPreferDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerReprintDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerSocialDAO;
import com.allin.ws.business.log.customer.dao.LogCustomerUniteDAO;
import com.allin.ws.business.log.fellowship.dao.LogFellowshipAttachmentDAO;
import com.allin.ws.business.log.fellowship.dao.LogFellowshipDAO;
import com.allin.ws.business.log.fellowship.dao.LogFellowshipRuleDAO;
import com.allin.ws.business.log.fellowship.dao.LogFellowshipSubDAO;
import com.allin.ws.util.SpringUtil;
import com.comm.model.CaseBaseinfo;
import com.comm.model.CmsVideo;
import com.comm.model.CustomerFollowFans;
import com.comm.model.CustomerFollowPeople;
import com.comm.model.CustomerUnite;
import com.comm.util.BaseForm;
import com.comm.util.MapUtil;
import com.comm.util.page.Page;
import com.comm.util.string.StringTool;
import com.ibatis.sqlmap.client.SqlMapClient;

*//**
 * web_weicai 数据源 dao基类
 * 
 * 
 *//*
public class GenericDAOIbatisImpl<T extends BaseForm> extends
		SqlMapClientDaoSupport {

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericDAOIbatisImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	@Resource(name = "sqlMapClientAllin")
	SqlMapClient sqlMapClientAllin;
	@PostConstruct
	public void setSqlMapClientBase() {
		super.setSqlMapClient(sqlMapClientAllin);
	}

	public Long create(String nsPrefix, Map paramMap) {

		Long pk = (Long) getSqlMapClientTemplate().insert(
				nsPrefix + "abatorgenerated_insert", paramMap);
		try{
			String tableName = "log_"+nsPrefix;
			paramMap.put("opDesc", "create");
			createLog(tableName,paramMap,nsPrefix);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			return pk;
		}
	}

	public void update(String nsPrefix, Map paramMap) {
		super.getSqlMapClientTemplate().update(
				nsPrefix + "abatorgenerated_updateByPrimaryKey", paramMap);
		try{
			//判断是否需要插入log，某些操作如浏览数更新的不需要插入log
			String logFlag =StringTool.getMapString(paramMap, "logFlag");
			if (StringUtils.isEmpty(logFlag)){
				String tableName = "log_"+nsPrefix;
				paramMap.put("opDesc", "update");
				createLog(tableName,paramMap,nsPrefix);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void delete(String nsPrefix, Map paramMap) {
		try{
			String tableName = "log_"+nsPrefix;
			paramMap.put("opDesc", "delete");
			createLog(tableName,paramMap,nsPrefix);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			super.getSqlMapClientTemplate().delete(
					nsPrefix + "abatorgenerated_deleteByPrimaryKey", paramMap);
		}
		
		
	}

	public void truncate(String nsPrefix) {
		super.getSqlMapClientTemplate().update(nsPrefix + "truncate");
	}

	
	public void callProc(String nsPrefix,String procName) {
		super.getSqlMapClientTemplate().update(nsPrefix + procName);
	}

	
	public T getById(String nsPrefix, Long id) {

		return (T) getSqlMapClientTemplate().queryForObject(
				nsPrefix + "abatorgenerated_selectByPrimaryKey", id);
	}

	public Page getPageList(String nsPrefix, Map paramMap) {

		Page pageObj = new Page(1, 10);
		pageObj.setItems(getList(nsPrefix, paramMap));
		pageObj.setTotal(getCount(nsPrefix, paramMap));

		return pageObj;

	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String nsPrefix, Map paramMap) {
		if (!paramMap.containsKey("firstResult")) {
			paramMap.put("firstResult", 0);
		}
		if (!paramMap.containsKey("maxResult")) {
			paramMap.put("maxResult", 10);
		}
		return getSqlMapClientTemplate().queryForList(nsPrefix + "getList",
				paramMap);
	}

	public int getCount(String nsPrefix, Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				nsPrefix + "getCount", paramMap);
	}

	*//**
	 * 分页查询
	 * 
	 * @param ibatisSqlName
	 *            sql名称，必须定义一个ibatisSqlName+"Total"的sql
	 * @param filter
	 *            条件
	 * @param page
	 *            第几页
	 * @param pageSize
	 *            每页数据量
	 * @return
	 *//*
	public Page findPageByFilter(String ibatisSqlName, Map filter, int page,
			int pageSize) {
		Page ps = null;
		int startRow = (page - 1) * pageSize;
		if (filter == null) {
			filter = new HashMap();
		}
		filter.put("startRow", startRow);
		filter.put("endRow", pageSize);
		try {
			int totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject(ibatisSqlName + "Total", filter);
			List resultList = getSqlMapClientTemplate().queryForList(
					ibatisSqlName, filter);
			ps = new Page(resultList, totalCount, pageSize, page);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return ps;
	}

	*//**
	 * 分页查询
	 * 
	 * @param ibatisSqlName
	 *            sql名称，必须定义一个ibatisSqlName+"Total"的sql
	 * @param filter
	 *            条件(已将startRow,endRow放入filter中)
	 * @return
	 *//*
	public Map<String, Object> findPageByFilter(String ibatisSqlName, Map filter) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		try {
			int totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject(ibatisSqlName + "Total", filter);
			List resultList = getSqlMapClientTemplate().queryForList(
					ibatisSqlName, filter);
			pageMap.put("total", totalCount);
			pageMap.put("rows", resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageMap;
	}
	*//**
	 * 所有的日志插入操作类
	 *//*


	public void createLog(String tableName,Map paramMap,String nsPrefix){
		if(paramMap!=null&&!paramMap.isEmpty()){
			Map temp_paramMap = new HashMap();
			for (Object obj : paramMap.entrySet()) {
	            Entry entry = (Entry) obj;
	            String key = (String) entry.getKey();
	            temp_paramMap.put(key, entry.getValue());
	        }
			MapUtil.changeVisitSiteId(temp_paramMap, paramMap);
			
			String optype = StringTool.getMapString(temp_paramMap, "opDesc");
			if(!optype.equals("delete")){
				temp_paramMap.remove("id");
			}
			
			
				
			if(tableName.equals("log_cms_video.")){
				LogCmsVideoDAO logCmsVideoDAO = (LogCmsVideoDAO)SpringUtil.getBean("logCmsVideoDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "videoId"));
					Map new_paramMap = MapUtil.transBean2Map((CmsVideo) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					//optype
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsVideoDAO.create(new_paramMap);
				}else{
					logCmsVideoDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_case_attachment.")){
				LogCaseAttachmentDAO logCaseAttachmentDAO = (LogCaseAttachmentDAO)SpringUtil.getBean("logCaseAttachmentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCaseAttachmentDAO.create(new_paramMap);
				}else{
					logCaseAttachmentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_case_baseinfo.")){
				LogCaseBaseinfoDAO logCaseBaseinfoDAO = (LogCaseBaseinfoDAO)SpringUtil.getBean("logCaseBaseinfoDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "caseId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCaseBaseinfoDAO.create(new_paramMap);
				}else{
					logCaseBaseinfoDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_case_supplement.")){
				LogCaseSupplementDAO logCaseSupplementDAO = (LogCaseSupplementDAO)SpringUtil.getBean("logCaseSupplementDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "caseId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCaseSupplementDAO.create(new_paramMap);
				}else{
					logCaseSupplementDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_case_tag.")){
				LogCaseTagDAO logCaseTagDAO = (LogCaseTagDAO)SpringUtil.getBean("logCaseTagDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCaseTagDAO.create(new_paramMap);
				}else{
					logCaseTagDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_auth.")){
				LogCustomerAuthDAO logCustomerAuthDAO = (LogCustomerAuthDAO)SpringUtil.getBean("logCustomerAuthDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "customerId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerAuthDAO.create(new_paramMap);
				}else{
					logCustomerAuthDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_baseinfo.")){
				LogCustomerBaseinfoDAO logCustomerBaseinfoDAO = (LogCustomerBaseinfoDAO)SpringUtil.getBean("logCustomerBaseinfoDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "customerId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerBaseinfoDAO.create(new_paramMap);
				}else{
					logCustomerBaseinfoDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_unite_info.")){
				LogCustomerUniteInfoDAO logCustomerUniteInfoDAO = (LogCustomerUniteInfoDAO)SpringUtil.getBean("logCustomerUniteInfoDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerUniteInfoDAO.create(new_paramMap);
				}else{
					logCustomerUniteInfoDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_doc_author.")){
				LogCmsDocAuthorDAO logCmsDocAuthorDAO = (LogCmsDocAuthorDAO)SpringUtil.getBean("logCmsDocAuthorDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsDocAuthorDAO.create(new_paramMap);
				}else{
					logCmsDocAuthorDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_doc_content.")){
				LogCmsDocContentDAO logCmsDocContentDAO = (LogCmsDocContentDAO)SpringUtil.getBean("logCmsDocContentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "docId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsDocContentDAO.create(new_paramMap);
				}else{
					logCmsDocContentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_doc.")){
				LogCmsDocDAO logCmsDocDAO = (LogCmsDocDAO)SpringUtil.getBean("logCmsDocDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "docId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsDocDAO.create(new_paramMap);
				}else{
					logCmsDocDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_doc_tag.")){
				LogCmsDocTagDAO logCmsDocTagDAO = (LogCmsDocTagDAO)SpringUtil.getBean("logCmsDocTagDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsDocTagDAO.create(new_paramMap);
				}else{
					logCmsDocTagDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_topic_attachment.")){
				LogCmsTopicAttachmentDAO logCmsTopicAttachmentDAO = (LogCmsTopicAttachmentDAO)SpringUtil.getBean("logCmsTopicAttachmentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsTopicAttachmentDAO.create(new_paramMap);
				}else{
					logCmsTopicAttachmentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_topic.")){
				LogCmsTopicDAO logCmsTopicDAO = (LogCmsTopicDAO)SpringUtil.getBean("logCmsTopicDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "topicId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsTopicDAO.create(new_paramMap);
				}else{
					logCmsTopicDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_topic_tag.")){
				LogCmsTopicTagDAO logCmsTopicTagDAO = (LogCmsTopicTagDAO)SpringUtil.getBean("logCmsTopicTagDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsTopicTagDAO.create(new_paramMap);
				}else{
					logCmsTopicTagDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_video_attachment.")){
				LogCmsVideoAttachmentDAO logCmsVideoAttachmentDAO = (LogCmsVideoAttachmentDAO)SpringUtil.getBean("logCmsVideoAttachmentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsVideoAttachmentDAO.create(new_paramMap);
				}else{
					logCmsVideoAttachmentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_video_author.")){
				LogCmsVideoAuthorDAO logCmsVideoAuthorDAO = (LogCmsVideoAuthorDAO)SpringUtil.getBean("logCmsVideoAuthorDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCmsVideoAuthorDAO.create(new_paramMap);
				}else{
					logCmsVideoAuthorDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_cms_video_tag.")){
				LogCmsVideoTagDAO LogCmsVideoTagDAO = (LogCmsVideoTagDAO)SpringUtil.getBean("LogCmsVideoTagDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					LogCmsVideoTagDAO.create(new_paramMap);
				}else{
					LogCmsVideoTagDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_collection.")){
				LogCustomerCollectionDAO logCustomerCollectionDAO = (LogCustomerCollectionDAO)SpringUtil.getBean("logCustomerCollectionDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerCollectionDAO.create(new_paramMap);
				}else{
					logCustomerCollectionDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_continuing_education.")){
				LogCustomerContinuingEducationDAO logCustomerContinuingEducationDAO = (LogCustomerContinuingEducationDAO)SpringUtil.getBean("logCustomerContinuingEducationDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerContinuingEducationDAO.create(new_paramMap);
				}else{
					logCustomerContinuingEducationDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_education.")){
				LogCustomerEducationDAO logCustomerEducationDAO = (LogCustomerEducationDAO)SpringUtil.getBean("logCustomerEducationDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerEducationDAO.create(new_paramMap);
				}else{
					logCustomerEducationDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_fellowship_attachment.")){
				LogCustomerFellowshipAttachmentDAO logCustomerFellowshipAttachmentDAO = (LogCustomerFellowshipAttachmentDAO)SpringUtil.getBean("logCustomerFellowshipAttachmentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFellowshipAttachmentDAO.create(new_paramMap);
				}else{
					logCustomerFellowshipAttachmentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_fellowship.")){
				LogCustomerFellowshipDAO logCustomerFellowshipDAO = (LogCustomerFellowshipDAO)SpringUtil.getBean("logCustomerFellowshipDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFellowshipDAO.create(new_paramMap);
				}else{
					logCustomerFellowshipDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_fellowship_state.")){
				LogCustomerFellowshipStateDAO logCustomerFellowshipStateDAO = (LogCustomerFellowshipStateDAO)SpringUtil.getBean("logCustomerFellowshipStateDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFellowshipStateDAO.create(new_paramMap);
				}else{
					logCustomerFellowshipStateDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_fellowship_sub.")){
				LogCustomerFellowshipSubDAO logCustomerFellowshipSubDAO = (LogCustomerFellowshipSubDAO)SpringUtil.getBean("logCustomerFellowshipSubDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFellowshipSubDAO.create(new_paramMap);
				}else{
					logCustomerFellowshipSubDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_follow_fans.")){
				LogCustomerFollowFansDAO logCustomerFollowFansDAO = (LogCustomerFollowFansDAO)SpringUtil.getBean("logCustomerFollowFansDAO");
				if(optype.equals("delete")){
					//customerId  fansCustomerId
					List<CustomerFollowFans> listobject = (List<CustomerFollowFans>)getList(nsPrefix, temp_paramMap);
					Long id = listobject.get(0).getId();
					
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFollowFansDAO.create(new_paramMap);
				}else{
					logCustomerFollowFansDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_follow_people.")){
				LogCustomerFollowPeopleDAO logCustomerFollowPeopleDAO = (LogCustomerFollowPeopleDAO)SpringUtil.getBean("logCustomerFollowPeopleDAO");
				if(optype.equals("delete")){
					//customerId  fansCustomerId
					List<CustomerFollowPeople> listobject = (List<CustomerFollowPeople>)getList(nsPrefix, temp_paramMap);
					Long id = listobject.get(0).getId();
					
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFollowPeopleDAO.create(new_paramMap);
				}else{
					logCustomerFollowPeopleDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_follow_resource.")){
				LogCustomerFollowResourceDAO logCustomerFollowResourceDAO = (LogCustomerFollowResourceDAO)SpringUtil.getBean("logCustomerFollowResourceDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFollowResourceDAO.create(new_paramMap);
				}else{
					logCustomerFollowResourceDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_fund.")){
				LogCustomerFundDAO logCustomerFundDAO = (LogCustomerFundDAO)SpringUtil.getBean("logCustomerFundDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerFundDAO.create(new_paramMap);
				}else{
					logCustomerFundDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_honor.")){
				LogCustomerHonorDAO logCustomerHonorDAO = (LogCustomerHonorDAO)SpringUtil.getBean("logCustomerHonorDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerHonorDAO.create(new_paramMap);
				}else{
					logCustomerHonorDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_occupation.")){
				LogCustomerOccupationDAO logCustomerOccupationDAO = (LogCustomerOccupationDAO)SpringUtil.getBean("logCustomerOccupationDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerOccupationDAO.create(new_paramMap);
				}else{
					logCustomerOccupationDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_opus.")){
				LogCustomerOpusDAO logCustomerOpusDAO = (LogCustomerOpusDAO)SpringUtil.getBean("logCustomerOpusDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerOpusDAO.create(new_paramMap);
				}else{
					logCustomerOpusDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_patent.")){
				LogCustomerPatentDAO logCustomerPatentDAO = (LogCustomerPatentDAO)SpringUtil.getBean("logCustomerPatentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					
					new_paramMap.remove("id");
					logCustomerPatentDAO.create(new_paramMap);
				}else{
					logCustomerPatentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_prefer.")){
				LogCustomerPreferDAO logCustomerPreferDAO = (LogCustomerPreferDAO)SpringUtil.getBean("logCustomerPreferDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerPreferDAO.create(new_paramMap);
				}else{
					logCustomerPreferDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_reprint.")){
				LogCustomerReprintDAO logCustomerReprintDAO = (LogCustomerReprintDAO)SpringUtil.getBean("logCustomerReprintDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerReprintDAO.create(new_paramMap);
				}else{
					logCustomerReprintDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_social.")){
				LogCustomerSocialDAO logCustomerSocialDAO = (LogCustomerSocialDAO)SpringUtil.getBean("logCustomerSocialDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerSocialDAO.create(new_paramMap);
				}else{
					logCustomerSocialDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_customer_unite.")){
				LogCustomerUniteDAO logCustomerUniteDAO = (LogCustomerUniteDAO)SpringUtil.getBean("logCustomerUniteDAO");
				if(optype.equals("delete")){
					//customerId  fansCustomerId
					List<CustomerUnite> listobject = (List<CustomerUnite>)getList(nsPrefix, temp_paramMap);
					Long id = listobject.get(0).getCustomerId();
					
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logCustomerUniteDAO.create(new_paramMap);
				}else{
					logCustomerUniteDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_fellowship_attachment.")){
				LogFellowshipAttachmentDAO logFellowshipAttachmentDAO = (LogFellowshipAttachmentDAO)SpringUtil.getBean("logFellowshipAttachmentDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logFellowshipAttachmentDAO.create(new_paramMap);
				}else{
					logFellowshipAttachmentDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_fellowship.")){
				LogFellowshipDAO logFellowshipDAO = (LogFellowshipDAO)SpringUtil.getBean("logFellowshipDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "fellowshipId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logFellowshipDAO.create(new_paramMap);
				}else{
					logFellowshipDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_fellowship_rule.")){
				LogFellowshipRuleDAO logFellowshipRuleDAO = (LogFellowshipRuleDAO)SpringUtil.getBean("logFellowshipRuleDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "id"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logFellowshipRuleDAO.create(new_paramMap);
				}else{
					logFellowshipRuleDAO.create(temp_paramMap);
				}
			}
			if(tableName.equals("log_fellowship_sub.")){
				LogFellowshipSubDAO logFellowshipSubDAO = (LogFellowshipSubDAO)SpringUtil.getBean("logFellowshipSubDAO");
				if(optype.equals("delete")){
					Long id = Long.valueOf(StringTool.getMapString(temp_paramMap, "fellowshipSubId"));
					Map new_paramMap = MapUtil.transBean2Map((T) getSqlMapClientTemplate().queryForObject(
							nsPrefix + "abatorgenerated_selectByPrimaryKey", id));
					new_paramMap.put("opDesc", optype);
					new_paramMap.remove("id");
					logFellowshipSubDAO.create(new_paramMap);
				}else{
					logFellowshipSubDAO.create(temp_paramMap);
				}
			}
			
		}
	}
}
*/