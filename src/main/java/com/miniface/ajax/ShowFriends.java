package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
			
		}else {
			json.put("message", "Cant retrieve data");
			json.put("status", "error");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}
	
	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JSONObject json) throws ServletException, IOException {
		
		JSONTokener jsonToken = new JSONTokener(request.getInputStream());
		JSONObject jsonRequest = new JSONObject(jsonToken);
		JSONArray filters = new JSONArray(jsonRequest.getJSONArray("searchParams"));
		JSONArray words = new JSONArray(jsonRequest.getJSONArray("searchWords"));
		String logicalOperand = new String(jsonRequest.getString("logicalOperand"));
		String wordPosition = new String(jsonRequest.getString("wordPosition"));
		
		FaceUserServiceImpl fusi = new FaceUserServiceImpl();
		JSONArray arr = fusi.findOtherPeople(Integer.parseInt((String) session.getAttribute("userID")), filters, words, logicalOperand, wordPosition);
		
		if (!arr.isEmpty() && arr.length()>0) {
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");

		} else {
			json.put("data", arr);
			json.put("message", "No data found with such parameters");
			json.put("status", "error");
			
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}
}
