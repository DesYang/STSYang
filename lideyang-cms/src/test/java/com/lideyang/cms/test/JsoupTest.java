package com.lideyang.cms.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.lideyang.cms.BaseTestCase;

public class JsoupTest extends BaseTestCase{

	@SuppressWarnings("resource")
	@Test
	public void testJsoup() throws IOException {
		Document document = Jsoup.connect("https://news.163.com/19/0820/19/EN22B1B50001899O.html").get();
		
		for (int i = 0; i < 10; i++) {
			//标题
			String title = document.title();
			//内容
			String content = document.getElementById("endText").text();
			
			System.out.println(title);
			
			System.out.println(content);
			
			//通过IO流输出
			File file = new File("D:\\upload\\0820\\"+i+title.substring(0, 4)+".txt");
			
			//缓冲输出流
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.close();
		}
		
	}
}
