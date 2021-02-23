package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;

@WebServlet("/HomePage")
public class HomePage extends JSONServlet {
	private static final long serialVersionUID = 1L;
       
    public HomePage() {
        super();

    }

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json ) throws ServletException, IOException {
		String name = (String) session.getAttribute("name");
		String surname = (String) session.getAttribute("surname");
		String userID = (String) session.getAttribute("userID");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("surname", surname);
		jsonObj.put("name", name);
		jsonObj.put("userID", userID);
		

		json.put("data", jsonObj);
		json.put("message", "Transaction successful");
		json.put("status", "success");
			
		response.getWriter().write(json.toString());
	}
	
	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json ) throws ServletException, IOException {

	}
}
