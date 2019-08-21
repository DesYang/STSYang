package com.lideyang.cms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSONObject;
import com.lideyang.cms.domain.Article;

public class ArticleConsumer implements MessageListener<String,String>{

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
		}
	}

}
