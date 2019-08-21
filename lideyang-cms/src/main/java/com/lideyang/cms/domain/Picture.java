package com.lideyang.cms.domain;

public class Picture {
	
	private String photo;
	private String desc;
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Picture [photo=" + photo + ", desc=" + desc + "]";
	}
	public Picture(String photo, String desc) {
		super();
		this.photo = photo;
		this.desc = desc;
	}
	public Picture() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
