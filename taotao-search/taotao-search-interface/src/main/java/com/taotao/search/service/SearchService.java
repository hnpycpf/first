package com.taotao.search.service;

import org.apache.solr.client.solrj.SolrServerException;

import com.fasterxml.jackson.databind.JsonNode;
import com.taotao.manager.pojo.Item;

import taotao.common.pojo.TaoResult;

public interface SearchService {

	TaoResult<Item> search(String q, Integer page) throws SolrServerException, Exception;

	void updateItem(String type, Long itemId);

}
