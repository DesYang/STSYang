package com.lideyang.cms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.ui.Model;

import com.lideyang.cms.domain.Article;
import com.lideyang.cms.es.dao.ArticleEsDao;

public class EsUtil {

	public static void  articles(Model model,String title,String currentPage,ArticleEsDao articleEsDao,ElasticsearchTemplate elasticsearchTemplate) {
		// 模糊分页高亮 
		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		// 索引 
		searchQuery.withIndices("cms");
		// 分页 
		int pageSize = 3;
		// 总个数 
		// long count = articleDao.count();
		List<Article> findByTitleLike = articleEsDao.findByTitleLike(title);
		
		PageUtils pageUtils = new PageUtils(currentPage, findByTitleLike.size(), pageSize);
		// 0是第一页 2 每页显示的条数 
		searchQuery.withPageable(new PageRequest(pageUtils.getCurrentPage()-1, pageSize));
		// 高亮 
		Field highlightFields = new HighlightBuilder.Field("title")
				.preTags("<span style='color:red'>")
				.postTags("</span>")
				.fragmentSize(100);
		
		
		searchQuery.withHighlightFields(highlightFields);
		// 模糊  
		if(title.equals("")) {
			searchQuery.withQuery(QueryBuilders.matchAllQuery());
		}else {
			searchQuery.withQuery(QueryBuilders.matchQuery("title", title));
		}
		// 执行查询 
		// SearchResultMapper 自定义映射 
		AggregatedPage<Article> page = elasticsearchTemplate.queryForPage(searchQuery.build(), Article.class, new SearchResultMapper() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
				// TODO Auto-generated method stub
				List<T> list = new ArrayList<T>();
				// 遍历结果
				for(SearchHit searchHit:response.getHits().getHits()) {
					// 原始的数据 
					Map<String, Object> smap = searchHit.getSource();
					// 高亮的数据
					Map<String, HighlightField> hmap = searchHit.getHighlightFields();
					list.add((T)createEsDoc(smap,hmap));
				}
				AggregatedPage<T> result=new AggregatedPageImpl<T>(list,pageable,response.getHits().getTotalHits());
				
				
				
				return result;
			}
		});
		// 取出集合 
		List<Article> content = page.getContent();
		
		
		
		// 从es中查询的数据  
		/*Iterable<Article> findAll = articleDao.findAll();
		List<Article> articles = new ArrayList<Article>();
		for (Article article : findAll) {
			articles.add(article);
		}*/
		// 分页 
		
		model.addAttribute("page",pageUtils.getPage());
		model.addAttribute("articles", content);
		model.addAttribute("title", title);
		
	}
	
	private static Article createEsDoc(Map<String, Object> smap,Map<String, HighlightField> hmap){
		Article ed = new Article();
		// title  
		if(hmap.get("title") != null) {
			ed.setTitle(hmap.get("title").fragments()[0].toString());
		}else {
			ed.setTitle(smap.get("title").toString());
		}
		// id
		if(smap.get("id") != null) {
			ed.setId(Integer.parseInt(smap.get("id").toString()));
		}
		if(smap.get("hits") != null) {
			ed.setHits(Integer.parseInt(smap.get("hits").toString()));
		}
		if(smap.get("hot") != null) {
			ed.setHot(Boolean.parseBoolean(smap.get("hot").toString()));
		}
		
		return ed;
	}
}
