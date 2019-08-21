package com.lideyang.cms.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lideyang.cms.service.ChannelCategoryService;

@Controller
public class ChannelController {

	@Autowired
	ChannelCategoryService service;
	
	@RequestMapping("queryAllChannel")
	@ResponseBody
	public Object queryAllChannel(){
		return service.getChannels();
	}
	
	@RequestMapping("queryCategoryByChannelId")
	@ResponseBody
	public Object queryCategoryByChannelId(Integer channel){
		return service.getCategories(channel);
	}
	
	
}
