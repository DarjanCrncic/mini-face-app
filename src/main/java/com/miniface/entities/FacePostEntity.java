package com.miniface.entities;

import javax.validation.constraints.NotEmpty;

public class FacePostEntity {
	
	@NotEmpty(message = "must enter title")
	private String title;
	@NotEmpty(message = "must enter body")
	private String body;
	
	
	//empty constructor
	public FacePostEntity() {
		super();
	}
	
	//getters and setters
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "FacePost [title=" + title + ", body=" + body + "]";
	}
		
}
