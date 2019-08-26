package com.lideyang.cms.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.kafka.core.KafkaTemplate;

import com.alibaba.fastjson.JSON;
import com.lideyang.cms.BaseTestCase;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Channel;
import com.lideyang.cms.utils.WordSortUtil;

import scala.util.Random;

public class ReadFileTest extends BaseTestCase {

	@Resource
	private KafkaTemplate kafkaTemplate;
	
	
	
	@Test
	public void test1() throws IOException {
		File file = new File("D:\\upload\\0820");
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			//内容
			BufferedReader br = new BufferedReader(new FileReader(file2));
			String cotent = br.readLine();
			String wordSortDesc = WordSortUtil.wordSortDesc(cotent);
			String[] split = wordSortDesc.split(",");
			for (String string : split) {
				System.out.println(string);
			}
		}	
	}
	
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
			
			Article article = new Article();
			
			article.setTitle(title);
			article.setContent(cotent);
			
			//点击量
			article.setHits(new Random().nextInt(100));
			article.setHot(new Random().nextBoolean());
			//频道
			Channel channel = new Channel();
			channel.setId(new Random().nextInt(7)+1);
			article.setChannel(channel);
			//摘要
			article.setSummary("我好难啊");
			//关键字
			article.setKeywords(title+WordSortUtil.wordSortDesc(cotent));
			//System.out.println(title+WordSortUtil.wordSortDesc(cotent));
			article.setCreated(new Date());
			
						
			kafkaTemplate.send("first","addarticle",JSON.toJSONString(article));
		}
	}
}
