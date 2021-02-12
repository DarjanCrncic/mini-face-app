package com.miniface.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestValidator {
	
	public static String validateRequest(HttpServletRequest request, String parametar) {
		
		String output = null;
		output = request.getParameter(parametar).strip();
		
		if(output.isBlank() || output.isEmpty()) {
			output = null;
		}
		
		return output;
	}
	
}
