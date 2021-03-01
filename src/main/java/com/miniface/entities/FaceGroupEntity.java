package com.miniface.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FaceGroupEntity {
	
	@NotEmpty(message = "must enter group name")
	private String name;
	@NotEmpty(message = "must enter description")
	private String description;
	@NotNull(message = "must have an owner")
	private int ownerId;

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

	@Override
	public String toString() {
		return "FaceGroup [name=" + name + ", description=" + description + ", ownerId=" + ownerId + "]";
	}
}
