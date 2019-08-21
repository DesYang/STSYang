/**
 * 
 */
package com.lideyang.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 说明:文章表(cms_article)
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月16日  下午8:34:11
 */
//存入es时,指定索引的名字和类型
@Document(indexName="cms",type="article")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	/**主键**/
	private Integer id;
	
	/**标题**/
	private String title;
	
	/**摘要：如果用户不填，就从内容里提取前面144个字**/
	private String summary;
	
	/**内容：大文本字段**/
	@NotNull(message = "内容不能空")
	@Size(min = 10, message = "内容至少10个字以上")
	private String content;
	
	/**默认图片：如果没有上传，则从正文中提取图片，如果正文中没有，则为空。**/
	private String picture;
	
	/**所属频道：可快速搜索频道下所有文章**/
	private Channel channel;
	
	/**所属分类**/
	private Category category;
	
	/**作者**/
	private User author;
	
	/**点击量**/
	private Integer hits;

	/**是否热门（是否上头条）**/
	private Boolean hot;
	
	/**状态：0:审核不通过，1:审核通过**/
	private Integer status;
	
	/**是否删除：true-删除，false-不删除**/
	private Boolean deleted;

	/**创建时间**/
	private Date created;
	
	/**更新时间**/
	private Date updated;
	
	/**关键字**/
	private String keywords;
	
	/**原文链接**/
	private String orglink;

	private String style;
	//---------------------------------------------------------------------

	
	public Article() {
		super();
	}

	public Article(Integer id) {
		super();
		this.id = id;
	}


	//---------------------------------------------------------------------
	
	
	public String getKeywords() {
		return keywords;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOrglink() {
		return orglink;
	}

	public void setOrglink(String orglink) {
		this.orglink = orglink;
	}
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Boolean getHot() {
		return hot;
	}

	public void setHot(Boolean hot) {
		this.hot = hot;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	
	//---------------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Article(Integer id, String title, String summary, String content,
			String picture, Channel channel, Category category, User author,
			Integer hits, Boolean hot, Integer status, Boolean deleted,
			Date created, Date updated, String keywords, String orglink) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.picture = picture;
		this.channel = channel;
		this.category = category;
		this.author = author;
		this.hits = hits;
		this.hot = hot;
		this.status = status;
		this.deleted = deleted;
		this.created = created;
		this.updated = updated;
		this.keywords = keywords;
		this.orglink = orglink;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", summary="
				+ summary + ", content=" + content + ", picture=" + picture
				+ ", channel=" + channel + ", category=" + category
				+ ", author=" + author + ", hits=" + hits + ", hot=" + hot
				+ ", status=" + status + ", deleted=" + deleted + ", created="
				+ created + ", updated=" + updated + ", keywords=" + keywords
				+ ", orglink=" + orglink + ", style=" + style + "]";
	}

	
}
