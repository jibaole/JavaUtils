package com.comm.util.generic.dao;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public interface GenericSearchDAO {

	UpdateResponse createIndex(List<SolrInputDocument> solr_input_list);

	UpdateResponse updateIndexList(List<SolrInputDocument> solr_input_list);

	UpdateResponse deleteIndexById(String idValue);

	UpdateResponse deleteByIndexType();

	UpdateResponse deleteAll();

	SolrDocumentList query(Map paramMap);

	ContentStreamUpdateRequest doFileIndex(Map<String, String> paramMap,
			Map fileParam);
	SolrDocumentList getDocList(Map paramMap);


}
