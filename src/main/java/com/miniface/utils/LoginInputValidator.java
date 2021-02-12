package com.miniface.utils;

import java.util.ArrayList;

// Validator for input information during login, checks username and password

public class LoginInputValidator {
	
	public static String[] validateLoginInput(String username, String password) {
		ArrayList<String> list = new ArrayList<>();
		
		if(username == null ) {
			list.add("Username invalid");
		}
		
		if(password == null ) {
			list.add("Password invalid");
		}
		
		return list.toArray(new String[list.size()]);
	}
}
