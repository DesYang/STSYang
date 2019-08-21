/**
 * 
 */
package com.lideyang.cms.web.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lideyang.cms.core.Page;
import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Category;
import com.lideyang.cms.domain.Channel;
import com.lideyang.cms.domain.Picture;
import com.lideyang.cms.domain.User;
import com.lideyang.cms.metas.Gender;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.service.UserService;
import com.lideyang.cms.utils.FileUploadUtil;
import com.lideyang.cms.utils.PageHelpUtil;
import com.lideyang.cms.web.Constant;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2018年1月10日 下午2:40:38
 */
@Controller
@RequestMapping("/my")
public class UserController {

	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService userService;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@RequestMapping({"/", "/index", "/home"})
	public String home(){
		return "user-space/home";
	}
	
	@RequestMapping({"/profile"})
	public String profile(){
		return "user-space/profile";
	}
	
	/**跳转发布文章页面**/
	/**删除部分**/
	@RequestMapping("/blog/edit")
	public String blog_edit(Integer id,Model model){
		Article article = articleService.selectByPrimaryKey(id);
		
		model.addAttribute("blog", article);
		
		return "user-space/blog_edit_new";	
	}
	
	
	/**文章保存和轮播图**/
	/**删除部分**/
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
		articleService.save(article);
		return "redirect:/my/blogs";	
	
	}
	
	/**个人设置里我的文章**/
	/**删除部分**/
	@RequestMapping("/blogs")
	public String blogs(Model model,HttpSession session,
			@RequestParam(value="page",defaultValue="1")Integer page){
		//分页信息
		PageHelper.startPage(page,3);
		 
		Article article = new Article();
		//当前登录用户
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		article.setAuthor(user);
		//当前用户发布的所有文章
		List<Article> articles = articleService.queryAll(article);	
		
		PageInfo<Article> pageInfo = new PageInfo<Article>(articles,3);
		String pageList = PageHelpUtil.page("blogs", pageInfo, null);
		
		model.addAttribute("blogs", articles);
		model.addAttribute("pageList", pageList);

		return "user-space/blog_list";
	}
	
	@ResponseBody
	@RequestMapping("/blog/remove")
	public Integer blogRemove(Integer id){
		Integer num = articleService.blogRemove(id);

		return num;
	}
	
	//用户信息完善或修改
	@RequestMapping("/user/save")
	public String usersave(User user,Model model,String sex){
		
		Gender valueOf = Gender.valueOf(sex);
		user.setGender(valueOf);
		userService.updateById(user);
		
		return "redirect:/my/userInfo";
	}
	
	//查询用户信息
	/**删除部分**/
	@RequestMapping("/userInfo")
	public String userInfo(HttpServletRequest request,Model model){
		User loginUser = (User) request.getSession().getAttribute(Constant.LOGIN_USER);
		
		User user = userService.selectById(loginUser.getId());
		
		model.addAttribute("user", user);
 
		return "user-space/useredit";
	}
	
	//上传头像页面
	@RequestMapping("/profile/avatar")
	public String goavatar(){
		return "user-space/avatar";		
	}
	
	@RequestMapping("/user/uploadPhoto")
	public String uploadPhoto(MultipartFile photo,HttpServletRequest request){
		String upload = FileUploadUtil.upload(request, photo);
		
		User user = (User) request.getSession().getAttribute(Constant.LOGIN_USER);
		
		user.setPortrait(upload);
		userService.uploadPhoto(user);
		
		return "user-space/home";
	}
	 

}
