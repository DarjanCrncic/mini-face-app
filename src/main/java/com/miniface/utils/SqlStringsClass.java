package com.miniface.utils;

public class SqlStringsClass {
	
	private static final String registerUser = "INSERT INTO face_user (username, password, name, surname, email, creation_time) VALUES (?,?,?,?,?,SYSTIMESTAMP)";
	
	private static final String loginUser = "SELECT * FROM face_user WHERE username = ? and password = ?";

	public static String getRegisterUser() {
		return registerUser;
	}
	
	public static String getLoginUser() {
		return loginUser;
	}
}
