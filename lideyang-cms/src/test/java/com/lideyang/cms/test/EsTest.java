package com.lideyang.cms.test;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.lideyang.cms.BaseTestCase;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.User;
import com.lideyang.cms.es.dao.ArticleEsDao;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.web.Constant;

public class EsTest extends BaseTestCase{

	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private ArticleEsDao articleEsDao;
	
	@Autowired
	private ArticleService articleService;
	@Test
	public void testEs() {
		System.out.println(elasticsearchTemplate);
	}
	
	@Test
	public void testEsQuery() {
		Iterable<Article> findAll = articleEsDao.findAll();
		for (Article article : findAll) {
			System.out.println(article);
		}
	}
	
	@Test
	public void testEsAdd() {
		List<Article> find = articleService.hotarticle();
		articleEsDao.save(find);
	}
	
	@Test
	public void testEsLike() {
		String title = "哈哈月考";
		List<Article> findByTitleLike = articleEsDao.findByTitleLike(title);
		for (Article article : findByTitleLike) {
			System.out.println(article.getTitle());
		}
	}
	
	@Test
	public void testEsLikePage() {
		String title = "哈哈月考";
		//分页page(当前页-1) size每页显示条数
		PageRequest pageRequest = new PageRequest(0, 2);
		List<Article> findByTitleLike = articleEsDao.findByTitleLike(title,pageRequest);
		for (Article article : findByTitleLike) {
			System.out.println(article.getTitle());
		}
	}
	
	@Test
	public void testEsadd111() {
		User user = new User();
		user.setId(5);
		Article article = new Article();
		article.setAuthor(user);
		List<Article> queryAll = articleService.queryAll(article);
		for (Article article2 : queryAll) {
			System.out.println(article2);
			articleEsDao.save(article2);
		}
	}
}
