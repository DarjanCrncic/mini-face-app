package com.miniface.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class FaceUserEntity {
	
	@NotEmpty(message = "must enter username")
	private String username;
	@NotEmpty(message = "must enter password")
	private String password;
	@NotEmpty(message = "must enter first name")
	private String name;
	@NotEmpty(message = "must enter last name")
	private String surname;
	@NotEmpty(message = "must enter email")
	@Email(message = "must be a valid email address")
	private String email;
	
	//empty constructor
	public FaceUserEntity() {
		super();
	}

	//getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "FaceUser [username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + "]";
	}

}
