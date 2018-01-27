package com.taotao.search.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.fabric.xmlrpc.base.Array;
import com.taotao.manager.mapper.ItemMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.search.service.SearchService;

import taotao.common.pojo.TaoResult;
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private CloudSolrServer server;
	@Value("${ROWS}")
	private Integer rows;
	@Autowired
	private ItemMapper itemMapper;
	@Override
	public TaoResult<Item> search(String q, Integer page) throws Exception {
		
		SolrQuery query=new SolrQuery();
		query.set("df", "item_title");
		if(q!=null){
			query.setQuery(q);	
		}
		else{
			query.setQuery("*:*");
		}
		query.setFilterQueries("item_status:1");
		query.setStart((page-1)*rows);
		query.setRows(rows);
		query.setHighlight(true);
		query.setHighlightSimplePre("<font color=red >");
		query.addHighlightField("item_title");
		query.setHighlightSimplePost("</font>");
		QueryResponse response = server.query(query);
		SolrDocumentList results = response.getResults();
		Map<String, Map<String, List<String>>> map = response.getHighlighting();
		ArrayList<Item> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			Item item=new Item();
			item.setId(Long.parseLong(solrDocument.get("id").toString()));
			item.setImage(solrDocument.get("item_image").toString());
			item.setPrice(Long.parseLong(solrDocument.get("item_price").toString()));
			item.setCid(Long.parseLong(solrDocument.get("item_cid").toString()));
			 List<String> list = map.get(solrDocument.get("id")).get("item_title");
			if(list!=null&&list.size()>0){
				item.setTitle(list.get(0));	
			}
			else{
				item.setTitle(solrDocument.get("item_title").toString());
			}
			itemList.add(item);
		}
		TaoResult<Item> tao=new TaoResult<>();
		tao.setRows(itemList);
		tao.setTotal(results.getNumFound());
		return tao;
	}
	@Override
	public void updateItem(String type, Long itemId) {
		if(type!=null&&type.equals("save")){
			Item item = itemMapper.selectByPrimaryKey(itemId);
			SolrInputDocument input =new SolrInputDocument();
			input.addField("id", item.getId());
			input.addField("item_title", item.getTitle());
			input.addField("item_price", item.getPrice());
			input.addField("item_image", item.getImage());
			input.addField("item_cid", item.getCid());
			input.addField("item_status", item.getStatus());
			try {
				server.add(input);
				server.commit();
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
