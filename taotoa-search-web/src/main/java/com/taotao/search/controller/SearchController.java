package com.taotao.search.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manager.pojo.Item;
import com.taotao.search.service.SearchService;

import taotao.common.pojo.TaoResult;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private SearchService searchService;
	@RequestMapping(method=RequestMethod.GET)
	public String srarch(String q,Model model,@RequestParam(value="page",defaultValue="1")Integer page) throws SolrServerException, Exception{
		
		String string = new String(q.getBytes("iso-8859-1"),"utf-8");
		TaoResult<Item> result=searchService.search(string,page);
		model.addAttribute("query",string );
		model.addAttribute("itemList", result.getRows());
		model.addAttribute("page", page);
		Long total = result.getTotal();
		Long pages=total%16==0?total/16:total/16+1;
		model.addAttribute("totalPages",pages );	
		return "search";
		
	}

}
