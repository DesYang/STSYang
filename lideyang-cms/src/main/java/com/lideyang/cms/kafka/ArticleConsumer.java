package com.lideyang.cms.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSONObject;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.es.dao.ArticleEsDao;
import com.lideyang.cms.service.ArticleService;

public class ArticleConsumer implements MessageListener<String,String>{

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleEsDao articleEsDao;
	@Override
	public void onMessage(ConsumerRecord<String, String> record) {
		// TODO Auto-generated method stub
		
		String key = record.key();
		String value = record.value();
		//System.out.println(key + value);
		
		if(key != null && key.equals("addarticle")) {
			Article article = JSONObject.parseObject(value,Article.class);
			System.out.println(article.getTitle());
			System.out.println(article.getContent());
			articleService.save(article);
			//articleEsDao.save(article);
			
		}else if(key != null && key.equals("addhits")) {
			int id = Integer.parseInt(value);
			
			articleService.increaseHit(id);
		}
	}

}
