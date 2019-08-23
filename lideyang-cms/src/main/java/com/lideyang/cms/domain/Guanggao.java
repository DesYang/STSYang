package com.lideyang.cms.domain;

import scala.collection.generic.BitOperations.Int;

public class Guanggao {
	
	private int id;
	private String name;
	private String url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Guanggao [id=" + id + ", name=" + name + ", url=" + url + "]";
	}
	public Guanggao(int id, String name, String url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
	}
	public Guanggao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
