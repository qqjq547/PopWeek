package com.hjq.model;

public class MainChild {

    private String title;
    private String description;
    private String image_url;
    private String count;
	public MainChild() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MainChild(String title, String description, String image_url,
			String count) {
		super();
		this.title = title;
		this.description = description;
		this.image_url = image_url;
		this.count = count;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "MainChild [title=" + title + ", description=" + description
				+ ", image_url=" + image_url + ", count=" + count + "]";
	}
    
	
}
