/**
 * 
 */
package com.lideyang.cms.web.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Guanggao;
import com.lideyang.cms.domain.Shoucang;
import com.lideyang.cms.domain.Special;
import com.lideyang.cms.domain.User;
import com.lideyang.cms.es.dao.ArticleEsDao;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.service.SpecialService;
import com.lideyang.cms.service.UserService;
import com.lideyang.cms.thread.ArticleThread;
import com.lideyang.cms.utils.EsUtil;
import com.lideyang.cms.utils.PageUtils;
import com.lideyang.cms.web.Constant;
import com.lideyang.cms.web.controllers.PassportController;

import scala.collection.generic.BitOperations.Int;

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
	private UserService userService;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	/*@Resource
	private KafkaTemplate kafkaTemplate;*/
	
	@Autowired
	private ArticleEsDao articleEsDao;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
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
	
	/*@RequestMapping("/searcharticle")
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
	}*/
	
	@RequestMapping("/searcharticle")
	public String searcharticle(Model model,@RequestParam(defaultValue="")String title,String currentPage) {
		long begin = System.currentTimeMillis();
		int pageSize = 3;
		int count = articleEsDao.findByTitleLike(title).size();
		PageUtils pageUtils = new PageUtils(currentPage, count, pageSize);
		List<Article> articles = articleEsDao.findByTitleLike(title, new PageRequest(pageUtils.getCurrentPage()-1, pageSize));
		long end = System.currentTimeMillis();
		
		model.addAttribute("articles", articles);
		model.addAttribute("title", title);
		model.addAttribute("page", pageUtils.getPage());
		model.addAttribute("time",end-begin );
		return "admin/searcharticle";
	}

	@RequestMapping("/hlsearcharticle")
	public String hlsearcharticle(Model model,@RequestParam(defaultValue="")String title,String currentPage) {
		//分页+模糊+高亮
		EsUtil.articles(model, title, currentPage, articleEsDao, elasticsearchTemplate);
		
		return "admin/hlsearcharticle";
	}
	
	@RequestMapping("/articles")
	public String articles(Model model,@RequestParam(defaultValue="")String title,String currentPage) {
		//分页
		int pageSize = 3;
		int count = articleService.count(title);
		PageUtils pageUtils = new PageUtils(currentPage, count, pageSize);
		
		
		List<Article> articles = articleService.articles(title,pageUtils);
		
		model.addAttribute("articles", articles);
		model.addAttribute("page", pageUtils.getPage());
		model.addAttribute("title", title);
		
		return "admin/articles";
	}
	
	@ResponseBody
	@RequestMapping("/shenhe")
	public boolean shenhe(int id) {
		articleService.shenhe(id);
		return true;
	}
	
	@RequestMapping("/toadd")
	public String toadd() {
		return "admin/addarticle";
	}
	
	@RequestMapping("/addarticle")
	public String addarticle(Article article,HttpSession session) {
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		article.setAuthor(user);
		article.setHits(new Random().nextInt(100));
		article.setHot(new Random().nextBoolean());
		article.setOrglink("www.baidu.com");
		
		articleService.save(article);
		articleEsDao.save(article);
		
		return "redirect:/admin/articles";
	}
	
	@RequestMapping("/guanggaolist")
	public String guanggaolist(Model model) {
		
		List<Guanggao> guanggaolist = articleService.guanggaolist();
		model.addAttribute("guanggaolist", guanggaolist);
		return "admin/guanggaolist";
	}
	
	@RequestMapping("/toaddguanggao")
	public String toaddguangao() {
		return "admin/addguanggao";
	}
	
	@RequestMapping("/addguangao")
	public String addguangao(Guanggao guanggao,MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException {
		//上传
		String filename = file.getOriginalFilename();		
		String url = UUID.randomUUID().toString()+filename;
		//路径
		String realPath = request.getSession().getServletContext().getRealPath("/");
		File file2 = new File(realPath+"\\upload\\"+url);
		//上传图片
		file.transferTo(file2);
		//新增到数据库
		guanggao.setUrl(url);
		articleService.addguanggao(guanggao);
		
		return "redirect:/admin/guanggaolist";
	}
	
	@RequestMapping("delguanggao")
	@ResponseBody
	public Boolean delguanggao(Integer id) {
		articleService.delguanggao(id);
		return true;
	}
	
	@RequestMapping("toupdateguanggao")
	public String toupdateguanggao(Integer id,Model model) {
		Guanggao guanggao = articleService.getguanggao(id);
		model.addAttribute("guanggao", guanggao);
		return "/admin/updateguanggao";
	}
	
	@RequestMapping("updateguanggao")
	public String updateguanggao(Guanggao guanggao) {
		articleService.updateguanggao(guanggao);
		
		return "redirect:/admin/guanggaolist";
	}
	
	@RequestMapping("shoucang")
	@ResponseBody
	public boolean shoucang(Integer id,HttpSession session) {
		Shoucang shoucang = new Shoucang();
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		System.out.println(user.getId());
		shoucang.setArticle_id(id);
		shoucang.setUser_id(user.getId());
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
		String format = simpleDateFormat.format(date);
		shoucang.setSdate(format);
		articleService.shoucang(shoucang);
		return true;
	}
	
	@RequestMapping("delarticles")
	@ResponseBody
	public boolean delarticles(Integer id) {
		articleService.delarticles(id);
		return true;
	}
	
	@RequestMapping("shenheshoucang")
	@ResponseBody
	public boolean shenheshoucang(Integer id,HttpSession session) {
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		System.out.println("a"+id);
		System.out.println("u"+user.getId());
		Integer num = articleService.shenheshoucang(id,user.getId());
		
		if(num>0) {
			return false;
		}else {
			return true;
		}
	}
	
	@RequestMapping("article")
	public String article(Integer id,Model model,HttpSession session) {
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		int user_id = 0;
		if(user!=null) {
			user_id=user.getId();
		}
		String key = "hits_"+id+"_"+user_id;
		BoundValueOperations ops = redisTemplate.boundValueOps(key);		
		Object obj = ops.get();
		if(obj == null) {
			/*redis*/
			//articleService.increaseHit(id);
			
			/*kafka*/
			//kafkaTemplate.send("first", "addhits", id+"");
			
			/*spring线程*/
			taskExecutor.execute(new ArticleThread(articleService,id));
			ops.set("");
			ops.expire(10, TimeUnit.SECONDS);
			
		}
		
		Article article = articleService.selectByPrimaryKey(id);
		model.addAttribute("l", article);
		return "admin/article";
	}
	
}
