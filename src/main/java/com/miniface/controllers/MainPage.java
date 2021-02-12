package com.miniface.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/main")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final Logger LOGGER = Logger.getLogger(MainPage.class); 
	
	public MainPage() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		HttpSession session = request.getSession();

		if (session.getAttribute("username") == null) {
			response.sendRedirect("login");
		} else {
			
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/main.jsp");
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}



///////////// session stuff, not needed at the moment
/*
String user = (String) session.getAttribute("user");
String username = null;
String sessionID = null;

Cookie[] cookies = request.getCookies();

if (cookies != null) {

	for (Cookie cookie : cookies) {
		System.out.println(cookie.getName() + " " + cookie.getValue());
		if (cookie.getName().equals("username")) {
			username = cookie.getValue();
		}
		if (cookie.getName().equals("JSESSIONID")) {
			sessionID = cookie.getValue();	// session id from cookies
		}
	}

} else {
	sessionID = session.getId(); // session id from url link
}
*/