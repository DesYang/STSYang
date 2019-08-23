/**
 * 
 */
package com.lideyang.cms.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lideyang.cms.core.Page;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Category;
import com.lideyang.cms.domain.Channel;
import com.lideyang.cms.domain.Guanggao;
import com.lideyang.cms.domain.Picture;
import com.lideyang.cms.domain.Slide;
import com.lideyang.cms.domain.Special;
import com.lideyang.cms.domain.User;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.service.SlideService;
import com.lideyang.cms.service.SpecialService;
import com.lideyang.cms.utils.FileUploadUtil;
import com.lideyang.cms.web.Constant;

/**
 * 说明:首页
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2018年1月10日 下午8:19:15
 */
@Controller
public class HomeController {

	@Resource
	private ArticleService articleService;
	
	@Resource
	private SlideService slideService;
	
	@Resource
	private SpecialService specialService;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@RequestMapping({"/", "/index", "/home"})
	public String home(
			@RequestParam(required = false) Integer channel, //频道
			@RequestParam(required = false) Integer category,//分类
			@RequestParam(defaultValue = "1") Integer page,//分类
			Model model){
		
		//------------------------------------
		
		long begin = System.currentTimeMillis();
		
		//拼条件
		Article conditions = new Article();
		conditions.setDeleted(false);
		conditions.setStatus(1);

		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//默认首页显示热门文章
				if(category == null && channel == null){
					conditions.setHot(true);
					
					//热门文章时显示幻灯片
					//List<Slide> slides = slideService.getTops(5);
					List<Slide> slides = new ArrayList<Slide>();
					BoundListOperations ops = redisTemplate.boundListOps("slides");
					List range = ops.range(0, -1);
					if(range!=null && range.size()>0) {
						slides=range;
					}else {
						slides=slideService.getTops(5);
						for (Slide slide : slides) {
							ops.rightPush(slide);
						}
						ops.expire(1, TimeUnit.MINUTES);
					}
					model.addAttribute("slides", slides);
				}
			}
		});
		 
		t1.start();
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<Article> articles = null;
				Page _page = new Page(page, 30);
				// TODO Auto-generated method stub
				//如果频道或分类不为空，则显示分类或频道数据
				if(category != null){
					conditions.setCategory(new Category(category));
				}else if(channel != null){
					conditions.setChannel(new Channel(channel));
				}
				
				articles = articleService.gets(conditions, _page, null);
				model.addAttribute("articles", articles);
			}
		});
		
		t2.start();

		
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//---------------右侧放10条最新文章---------------------
				Article lastArticlesConditions = new Article();
				lastArticlesConditions.setDeleted(false);
				lastArticlesConditions.setStatus(1);
				
				Page lastArticlesPage = new Page(1, 10);
				lastArticlesPage.setTotalCount(100);//设置了总记录数，可以节省统计查询，提高性能。
				
				List<Article> lastArticles = articleService.gets(lastArticlesConditions, lastArticlesPage, null);
				model.addAttribute("lastArticles", lastArticles);
			}
		});
		
		t3.start();
		
		Thread t7 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<Guanggao> guanggaolist = articleService.guanggaolist();
				Guanggao guanggao = guanggaolist.get(new Random().nextInt(guanggaolist.size()));
				model.addAttribute("guanggao", guanggao);
			}
			
		});
		
		t7.start();
		
		/*Thread t6 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//---------------右侧放5条最新图片---------------------
				Article lastArticlesConditions = new Article();
				lastArticlesConditions.setDeleted(false);
				lastArticlesConditions.setStatus(1);
				
				Page lastArticlesPage = new Page(1, 5);
				lastArticlesPage.setTotalCount(100);//设置了总记录数，可以节省统计查询，提高性能。
				
				List<Article> lastArticlesPhoto = articleService.gets(lastArticlesConditions, lastArticlesPage, null);
				model.addAttribute("lastArticlesPhoto", lastArticlesPhoto);
			}
		});
			
		t6.start();*/
		
		Thread t4 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(channel != null){
					model.addAttribute("channel", new Channel(channel));
				}
				model.addAttribute("category", category);
			}
		});
		
		t4.start();

		Thread t5 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<Special> listSpecial = specialService.searchSpecialById();
					
				model.addAttribute("listSpecial", listSpecial);
			}
		});
		
		t5.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			//t6.join();
			t7.join();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		model.addAttribute("time", end-begin);
		
		return "home";
	}
	
	@RequestMapping("/article")
	public String article(Integer id,Model model){
		
		articleService.increaseHit(id);
		Article article = articleService.selectByPrimaryKey(id);
		if(article.getContent()!=null&&article.getContent().length()>0){
			List<Picture> parseArray = JSONArray.parseArray(article.getContent(),Picture.class);
			model.addAttribute("pictures", parseArray);
		}
		
		model.addAttribute("blog", article);
		
		return "blog";
	}
	
	@RequestMapping("/blog/save")
	public String String_save(Article article,HttpSession session,MultipartFile file,HttpServletRequest request,MultipartFile[] photo,String[] desc){
		List<Picture> pictures = new ArrayList<Picture>();
		for (int i = 0; i < desc.length; i++) {
			Picture picture = new Picture();
			String upload = FileUploadUtil.upload(request, photo[i]);
			if(!upload.equals("")){
				picture.setPhoto(upload);
			}
			if(!desc.equals("")){
			picture.setDesc(desc[i]);
			}
			if(picture!=null){
				pictures.add(picture);
			}			
		}
		String upload = FileUploadUtil.upload(request, file);
		if(!upload.equals("")){
			article.setPicture(upload);
		}
		
		if(pictures!=null){
			article.setContent(JSON.toJSONString(pictures));
		}
		
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		System.out.println(article);
		article.setAuthor(user);
		article.setPicture(upload);
		article.setHits(0);
		article.setHot(true);
		article.setStatus(1);
		article.setDeleted(false);
		article.setCreated(new Date());
		System.out.println(article.getStyle());
		articleService.save(article);
		return "redirect:/my/blogs";	
	
	}

}
