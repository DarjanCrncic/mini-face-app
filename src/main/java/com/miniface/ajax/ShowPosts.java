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
import com.miniface.services.FacePostServiceImpl;

@WebServlet("/ShowPosts")
public class ShowPosts extends JSONServlet {
	private static final long serialVersionUID = 1L;

	public ShowPosts() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		FacePostServiceImpl fpsi = new FacePostServiceImpl();
		JSONArray arr = fpsi.showVissiblePosts(Integer.parseInt((String) session.getAttribute("userID")));

		if (!arr.isEmpty() && arr != null) {
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());

		} else {
			json.put("message", "Cant retrieve data");
			json.put("status", "error");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		}
	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

		JSONTokener jsonToken = new JSONTokener(request.getInputStream());
		JSONObject jsonRequest = new JSONObject(jsonToken);
		JSONArray filters = new JSONArray(jsonRequest.getJSONArray("searchParams"));
		JSONArray words = new JSONArray(jsonRequest.getJSONArray("searchWords"));
		String logicalOperand = new String(jsonRequest.getString("logicalOperand"));
		String wordPosition = new String(jsonRequest.getString("wordPosition"));

		FacePostServiceImpl fpsi = new FacePostServiceImpl();
		JSONArray arr = null;

		if (words.get(0).toString().isBlank() || words.get(0).toString().isEmpty()) {
			arr = fpsi.showVissiblePosts(Integer.parseInt((String) session.getAttribute("userID")));
		} else {
			arr = fpsi.searchVissiblePosts(Integer.parseInt((String) session.getAttribute("userID")), filters, words, logicalOperand, wordPosition);
		}

		if (!arr.isEmpty() && arr.length()>0) {
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());

		} else {
			json.put("data", arr);
			json.put("message", "No data found with such parameters");
			json.put("status", "error");
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		}

	}

}
