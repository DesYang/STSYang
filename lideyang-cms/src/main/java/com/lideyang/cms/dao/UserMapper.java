/**
 * 
 */
package com.lideyang.cms.dao;

import java.util.List;

import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.User;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2018年1月10日 下午1:37:59
 */
public interface UserMapper {

	public void insert(User user);
	
	public void deleteById(int id);

	public User selectById(int id);

	public User selectByUsername(String username);

	public int count(User user);

	public void updateById(User user);

	public void uploadPhoto(User user);

	public List<Article> shoucanglist(Integer id);
	
}
