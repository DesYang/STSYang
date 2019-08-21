package com.lideyang.cms.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.kafka.core.KafkaTemplate;

import com.alibaba.fastjson.JSON;
import com.lideyang.cms.BaseTestCase;
import com.lideyang.cms.domain.Article;

public class ReadFileTest extends BaseTestCase {

	@Resource
	private KafkaTemplate kafkaTemplate;
	
	@SuppressWarnings("resource")
	@Test
	public void readFile() throws IOException {
		File file = new File("D:\\upload\\0820");
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			//标题
			String title = file2.getName().split("\\.")[0];
			//内容
			BufferedReader br = new BufferedReader(new FileReader(file2));
			String cotent = br.readLine();
			
			System.out.println(title);
			System.out.println(cotent);
			
			Article article = new Article();
			
			article.setTitle(title);
			article.setContent(cotent);
			
			kafkaTemplate.send("first","addarticle",JSON.toJSONString(article));
		}
	}
}
