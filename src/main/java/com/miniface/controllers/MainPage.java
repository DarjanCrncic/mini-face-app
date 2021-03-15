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
			//int userID = Integer.parseInt(session.getAttribute("userID").toString());
			//FaceUserServiceImpl fusi = new FaceUserServiceImpl();
			//JSONArray arr = fusi.showFriendsList(userID);
			
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
