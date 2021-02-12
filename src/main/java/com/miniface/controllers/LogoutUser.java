package com.miniface.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LogoutUser() {
        super();
   }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}		
		}
		
		HttpSession session = request.getSession(false);	
		if(session != null) {
			session.invalidate();
		}
		
		response.sendRedirect("login");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
