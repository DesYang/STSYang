/**
 * 
 */
package com.lideyang.cms.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lideyang.cms.core.Page;
import com.lideyang.cms.dao.ArticleMapper;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Guanggao;
import com.lideyang.cms.domain.Shoucang;
import com.lideyang.cms.domain.User;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.utils.PageUtils;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年4月21日 下午9:06:07
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Resource
	ArticleMapper articleMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Article> gets(Article conditions, Page page, LinkedHashMap<String, Boolean> orders) {
		List<Article> articles = articleMapper.selects(conditions, orders, page);
		if(page != null && page.getPageCount() == 0){
			int totalCount = articleMapper.count(conditions);
			page.setTotalCount(totalCount);
		}
		return articles;
	}

	@Override
	public void increaseHit(Integer id) {
		// TODO Auto-generated method stub
		articleMapper.increaseHit(id);
	}

	@Override
	public Integer blogRemove(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.blogRemove(id);
	}

	@Override
	public List<Article> queryAll(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.queryAll(article);
	}

	@Override
	public Article selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void save(Article article) {
		// TODO Auto-generated method stub
		articleMapper.save(article);
	}

	@Override
	public List<Article> hotarticle() {
		// TODO Auto-generated method stub
		return articleMapper.hotarticle();
	}

	@Override
	public int count(String title) {
		// TODO Auto-generated method stub
		return articleMapper.count2(title);
	}

	@Override
	public List<Article> articles(String title, PageUtils pageUtils) {
		// TODO Auto-generated method stub
		return articleMapper.articles(title,pageUtils);
	}

	@Override
	public void shenhe(int id) {
		// TODO Auto-generated method stub
		articleMapper.shenhe(id);
	}

	@Override
	public List<Guanggao> guanggaolist() {
		// TODO Auto-generated method stub
		return articleMapper.guanggaolist();
	}

	@Override
	public void addguanggao(Guanggao guanggao) {
		// TODO Auto-generated method stub
		articleMapper.addguanggao(guanggao);
	}

	@Override
	public void delguanggao(Integer id) {
		// TODO Auto-generated method stub
		articleMapper.delguanggao(id);
	}

	@Override
	public Guanggao getguanggao(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.getguanggao(id);
	}

	@Override
	public void updateguanggao(Guanggao guanggao) {
		// TODO Auto-generated method stub
		articleMapper.updateguanggao(guanggao);
	}

	@Override
	public void shoucang(Shoucang shoucang) {
		// TODO Auto-generated method stub
		articleMapper.shoucang(shoucang);
	}

	@Override
	public void delarticles(Integer id) {
		// TODO Auto-generated method stub
		articleMapper.delarticles(id);
	}

	@Override
	public Integer shenheshoucang(Integer id,Integer uid) {
		// TODO Auto-generated method stub
		return articleMapper.shenheshoucang(id,uid);
	}
	
	
}
