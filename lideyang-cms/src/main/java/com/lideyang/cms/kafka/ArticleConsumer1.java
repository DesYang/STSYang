package com.lideyang.cms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSONObject;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.service.ArticleService;

public class ArticleConsumer1 implements MessageListener<String, String>{

	private ArticleService articleService;
	@Override
	public void onMessage(ConsumerRecord<String, String> record) {
		// TODO Auto-generated method stub
		
		String key = record.key();
		String value = record.value();
		if(key!=null && key.equals("addarticle")) {
			Article article = JSONObject.parseObject(value,Article.class);
			//新增到数据库
			articleService.save(article);
		}	
	}

}
