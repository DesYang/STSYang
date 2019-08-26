package com.lideyang.cms.thread;

import com.lideyang.cms.service.ArticleService;

public class ArticleThread implements Runnable{

	private ArticleService articleService;
	
	private int id;
	
	
	
	public ArticleThread(ArticleService articleService, int id) {
		super();
		this.articleService = articleService;
		this.id = id;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		articleService.increaseHit(id);
	}

}
