package com.hjq.model;

public class Event {
	private String name;
	private String count;
	private String image;
	private String link;
	private String author;
	private String loc_name;
	private String begin_time;
	private String category_name;
	private String geo;
	public Event(String name, String count, String image, String link,
			String author, String loc_name, String begin_time,
			String category_name, String geo) {
		super();
		this.name = name;
		this.count = count;
		this.image = image;
		this.link = link;
		this.author = author;
		this.loc_name = loc_name;
		this.begin_time = begin_time;
		this.category_name = category_name;
		this.geo = geo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLoc_name() {
		return loc_name;
	}
	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	@Override
	public String toString() {
		return "Event [name=" + name + ", count=" + count + ", image=" + image
				+ ", link=" + link + ", author=" + author + ", loc_name="
				+ loc_name + ", begin_time=" + begin_time + ", category_name="
				+ category_name + ", geo=" + geo + "]";
	}

}
