package com.taotao.portal.controller;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taotao.manager.pojo.Content;
import com.taotao.manager.service.ContentService;

@Controller
@RequestMapping("/index")
public class IntexController {
	@Value("${TAOTAO_PIC_ID}")
	private String bigPicID ;
	@Autowired
	private ContentService contentService;
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) throws JsonProcessingException{
		String queryBigPic = contentService.queryBigPic(bigPicID);
		model.addAttribute("ID",queryBigPic);
				return "index";
	}
	
	
}
