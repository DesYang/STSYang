package com.lideyang.cms.es.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.lideyang.cms.domain.Article;
//继承父接口 可以直接使用父接口中定义好的增删改查
public interface ArticleEsDao extends ElasticsearchRepository<Article, Integer>{

	//命名查询 方法的名字必须按照命名规则命名
	public List<Article> findByTitleLike(String title);
	//模糊+分页
	public List<Article> findByTitleLike(String title,Pageable pageable);
} 
