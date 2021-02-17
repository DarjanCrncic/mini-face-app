package com.miniface.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class JSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public JSONServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json");
	    request.setCharacterEncoding("UTF-8");
	    JSONObject json = new JSONObject();

		if (session == null || session.getAttribute("username") == null) {
			doGetNotLoggedIn(request, response, session, json);
		} else {
			doGetLoggedIn(request, response, session, json);
		}
	    

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json");
	    request.setCharacterEncoding("UTF-8");
	    JSONObject json = new JSONObject();

		if (session == null || session.getAttribute("username") == null) {
			json.put("message", "No session");
			json.put("status", "error");
			doGetNotLoggedIn(request, response, session, json);
		} else {
			doGetLoggedIn(request, response, session, json);
		}

	}

	protected void doGetNotLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

	}

	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		
	}
	
	protected void doPostNotLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

	}

	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		
	}

}
