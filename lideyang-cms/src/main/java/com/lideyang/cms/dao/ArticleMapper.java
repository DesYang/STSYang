/**
 * 
 */
package com.lideyang.cms.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lideyang.cms.core.Page;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Guanggao;
import com.lideyang.cms.domain.Shoucang;
import com.lideyang.cms.utils.PageUtils;


/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月16日 下午9:18:02
 */
public interface ArticleMapper {

	/**
	 * 功能说明：保存文章<br>
	 * @param article
	 * void
	 * @return 
	 */
	public void save(Article article);
	

	/**
	 * 功能说明：递增访问量<br>
	 * @param id
	 * void
	 */
	public void increaseHit(int id);
	
	
	/**
	 * 功能说明：查询文章<br>
	 * @return
	 * List<Article>
	 */
	public List<Article> selects(@Param("article") Article article, @Param("order") LinkedHashMap<String, Boolean> orders, @Param("page") Page page);
	
	
	/**
	 * 功能说明：统计<br>
	 * @param article
	 * @return
	 * int
	 */
	public int count(@Param("article") Article article);


	/**
	 * 功能说明：统计<br>
	 * @param article
	 * @return
	 * int
	 */
	public Article selectByPrimaryKey(Integer id);
	/**
	 * 功能说明：二级联动<br>
	 * @param id
	 * @return article
	 * Article
	 */
	

	public List<Article> queryAll(@Param("article") Article article);
	/**
	 * 功能说明：查询全部文章<br>
	 * @param id
	 * @return article
	 * Article
	 */

	public Integer blogRemove(Integer id);


	public List<Article> hotarticle();


	public int count2(String title);


	public List<Article> articles(@Param("title")String title, @Param("pageUtils")PageUtils pageUtils);


	public void shenhe(int id);


	public List<Guanggao> guanggaolist();


	public void addguanggao(Guanggao guanggao);


	public void delguanggao(Integer id);


	public Guanggao getguanggao(Integer id);


	public void updateguanggao(Guanggao guanggao);


	public void shoucang(Shoucang shoucang);


	public void delarticles(Integer id);


	public Integer shenheshoucang(@Param("id")Integer id, @Param("uid")Integer uid);


	public Article article(Integer id);



}
