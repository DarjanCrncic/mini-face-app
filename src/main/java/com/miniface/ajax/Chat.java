package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;

@WebServlet("/Chat")
public class Chat extends JSONServlet {
	private static final long serialVersionUID = 1L;
       
    public Chat() {
        super();
    }

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
	
	}


	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		doGet(request, response);
	}

}
