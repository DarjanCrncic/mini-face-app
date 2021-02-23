package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;
import com.miniface.services.FaceUserServiceImpl;

@WebServlet("/ShowFriends")
public class ShowFriends extends JSONServlet {
	private static final long serialVersionUID = 1L;

	public ShowFriends() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JSONObject json) throws ServletException, IOException {

		FaceUserServiceImpl fusi = new FaceUserServiceImpl();
		JSONArray arr = fusi.showFriendsList(Integer.parseInt((String) session.getAttribute("userID")));
		
		if(!arr.isEmpty() && arr != null) {
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		}else {
			json.put("message", "Cant retrieve data");
			json.put("status", "error");
		}
		
		
		
	}

}
