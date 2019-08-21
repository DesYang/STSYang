package com.lideyang.cms.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lideyang.cms.domain.User;
import com.lideyang.cms.service.ArticleService;
import com.lideyang.cms.service.UserService;

@ContextConfiguration("classpath:spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ClassTest01 {

	@Autowired
	UserService userService;
	
	@Resource
	RedisTemplate redisTemplate;
	
	@Test
	public void Test01(){
		User selectById = userService.selectById(1);
		System.out.println(selectById);
	}
	
	@Test
	public void Test02() {
		System.out.println(redisTemplate);
	}
}
