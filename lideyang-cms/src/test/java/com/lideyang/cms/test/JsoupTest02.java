package com.lideyang.cms.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class JsoupTest02 {

	@SuppressWarnings("all")
	@Test
	public void toJsoup() throws IOException {
		Document document = Jsoup.connect("https://news.163.com/19/0824/08/ENB4V3OA0001875P.html").get();
		
		
		for (int i = 0; i < 10; i++) {
			String title = document.title();
			String text = document.getElementById("endText").text();
			
			File file = new File("D:\\upload\\0820"+i+title.substring(0, 5)+".txt");
		}
	}
}
