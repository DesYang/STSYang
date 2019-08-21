package com.lideyang.cms.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.lideyang.cms.core.Page;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Special;
import com.lideyang.cms.domain.User;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月28日 下午4:48:47
 */
public interface ArticleService {

	/**
	 * 功能说明：<br>
	 * @param conditions
	 * @param page
	 * @param orders  排序，默认按创建时间倒排序
	 * @return
	 * List<Article>
	 */
	public abstract List<Article> gets(Article conditions, Page page, LinkedHashMap<String, Boolean> orders);

	public abstract void increaseHit(Integer id);


	public abstract Integer blogRemove(Integer id);

	public abstract List<Article> queryAll(Article article);

	public abstract Article selectByPrimaryKey(Integer id);

	public abstract void save(Article article);

	public abstract List<Article> hotarticle();
	

}