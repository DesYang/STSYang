package com.lideyang.cms.domain;

public class Shoucang {
	
	private int id;
	private int user_id;
	private int article_id;
	private String sdate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getArticle_id() {
		return article_id;
	}
	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	@Override
	public String toString() {
		return "Shoucang [id=" + id + ", user_id=" + user_id + ", article_id=" + article_id + ", sdate=" + sdate + "]";
	}
	public Shoucang(int id, int user_id, int article_id, String sdate) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.article_id = article_id;
		this.sdate = sdate;
	}
	public Shoucang() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
