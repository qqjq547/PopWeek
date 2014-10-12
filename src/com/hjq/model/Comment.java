package com.hjq.model;

public class Comment {
	private String name;
	private String average;
	private String image;
	private String link;
	private String author;

	public Comment(String name, String average, String image, String link,
			String author) {
		super();
		this.name = name;
		this.average = average;
		this.image = image;
		this.link = link;
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
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

	@Override
	public String toString() {
		return "Comment [name=" + name + ", average=" + average + ", image="
				+ image + ", link=" + link + ", author=" + author + "]";
	}

}
