/**
 * 
 */
package com.lideyang.cms.web.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Special;
import com.lideyang.cms.es.dao.ArticleEsDao;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.service.SpecialService;
import com.lideyang.cms.utils.EsUtil;
import com.lideyang.cms.utils.PageUtils;
import com.lideyang.cms.web.controllers.PassportController;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月29日 下午6:54:11
 */
@Controller
@RequestMapping("/admin")
public class AdminHomeController {

	@Resource
	private SpecialService service;
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ArticleEsDao articleEsDao;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	public static Logger log = LoggerFactory.getLogger(PassportController.class);
	
	@RequestMapping({"/", "/index"})
	public String home(){
		return "admin/home";
	}
	
	@RequestMapping("/Articlecategories")
	public String chooseA(){
		return "admin/articlehome";	
	}
	
	
	@RequestMapping("/special_con")
	public String special_con(Model model){
		List<Special> list = service.searchSpecial();
		
		model.addAttribute("list", list);
		
		return "admin/article_specialhome";
	}
	
	@SuppressWarnings("all")
	@RequestMapping("/hotarticle")
	public String hotarticle(Model model) {
		
		long begin = System.currentTimeMillis();
		List<Article> articles = new ArrayList<Article>();
		BoundListOperations ops = redisTemplate.boundListOps("hotarticle");
		List range = ops.range(0, -1);
		if(range!=null&&range.size()>0) {
			articles = range;
		}else {
			articles = articleService.hotarticle();
			for (Article article : articles) {
				ops.rightPush(article);
			}
			ops.expire(1, TimeUnit.MINUTES);
		}
		
		long end = System.currentTimeMillis();
		
		model.addAttribute("list", articles);
		model.addAttribute("time", end-begin);
		
		return "admin/hotarticle";
	}
	
	@RequestMapping("/searcharticle")
	public String searcharticle(Model model,@RequestParam(defaultValue="")String title,String currentPage) {
		long begin = System.currentTimeMillis();
		//分页
		int pageSize=3;
		int count = articleEsDao.findByTitleLike(title).size();
		PageUtils pageUtils = new PageUtils(currentPage, count, pageSize);
		List<Article> articles = articleEsDao.findByTitleLike(title,new PageRequest(pageUtils.getCurrentPage()-1, pageSize));
		long end = System.currentTimeMillis();
		model.addAttribute("time", end-begin);
		model.addAttribute("articles", articles);
		model.addAttribute("page", pageUtils.getPage());
		model.addAttribute("title", title);
		return "admin/searcharticle";
	}

	@RequestMapping("/hlsearcharticle")
	public String hlsearcharticle(Model model,@RequestParam(defaultValue="")String title,String currentPage) {
		//分页+模糊+高亮
		EsUtil.articles(model, title, currentPage, articleEsDao, elasticsearchTemplate);
		
		return "admin/hlsearcharticle";
	}
}
