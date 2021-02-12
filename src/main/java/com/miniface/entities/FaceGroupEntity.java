package com.miniface.entities;

import java.util.Date;

public class FaceGroupEntity {
	
	private String name;
	private String description;
	private int ownerId;
	private Date creationTime;
	private Date updateTime;
	
	// empty constructor
	public FaceGroupEntity() {
		super();
	}
	
	// getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString() {
		return "FaceGroup [name=" + name + ", description=" + description + ", ownerId=" + ownerId + ", creationTime="
				+ creationTime + ", updateTime=" + updateTime + "]";
	}
}
