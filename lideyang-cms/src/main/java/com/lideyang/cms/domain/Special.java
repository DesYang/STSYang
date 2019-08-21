package com.lideyang.cms.domain;

import java.util.List;

public class Special {

	private Integer id;
	private String title;
	private String abstracte;
	private String created;
	private List<Article> listArticle;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbstracte() {
		return abstracte;
	}
	public void setAbstracte(String abstracte) {
		this.abstracte = abstracte;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public List<Article> getListArticle() {
		return listArticle;
	}
	public void setListArticle(List<Article> listArticle) {
		this.listArticle = listArticle;
	}
	@Override
	public String toString() {
		return "Special [id=" + id + ", title=" + title + ", abstracte="
				+ abstracte + ", created=" + created + ", listArticle="
				+ listArticle + "]";
	}
	public Special(Integer id, String title, String abstracte, String created,
			List<Article> listArticle) {
		super();
		this.id = id;
		this.title = title;
		this.abstracte = abstracte;
		this.created = created;
		this.listArticle = listArticle;
	}
	public Special() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
