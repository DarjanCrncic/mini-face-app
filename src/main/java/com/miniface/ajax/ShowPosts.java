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
import com.miniface.utils.RequestValidator;

@WebServlet("/ShowPosts")
public class ShowPosts extends JSONServlet {
	private static final long serialVersionUID = 1L;

	public ShowPosts() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		FacePostServiceImpl fpsi = new FacePostServiceImpl();
		String type = RequestValidator.validateRequest(request, "type");
		String groupID = RequestValidator.validateRequest(request, "groupID");
		JSONArray arr = null;
		
		if(type.equals("all"))
			arr = fpsi.showVissiblePosts(Integer.parseInt((String) session.getAttribute("userID")));
		if(type.equals("group") && groupID != null) 
			arr = fpsi.showVissiblePostsForGroup(Integer.parseInt(groupID));
		
		if (arr != null && !arr.isEmpty()) {						
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");

		} else {
			json.put("message", "Cant retrieve data");
			json.put("status", "error");
			
		}
		json.put("userID", session.getAttribute("userID"));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

		JSONTokener jsonToken = new JSONTokener(request.getInputStream());
		JSONObject jsonRequest = new JSONObject(jsonToken);
		JSONArray filters = new JSONArray(jsonRequest.getJSONArray("searchParams"));
		JSONArray words = new JSONArray(jsonRequest.getJSONArray("searchWords"));
		String logicalOperand = new String(jsonRequest.getString("logicalOperand"));
		String wordPosition = new String(jsonRequest.getString("wordPosition"));
		int pageNumber = jsonRequest.getInt("pageNumber"); 
		int rowNumber = jsonRequest.getInt("rowNumber");
		FacePostServiceImpl fpsi = new FacePostServiceImpl();
		JSONArray arr = fpsi.searchVissiblePosts(Integer.parseInt((String) session.getAttribute("userID")), filters, words, logicalOperand, wordPosition, pageNumber, rowNumber);

		if(!arr.isEmpty() && arr.length() > 0){
			if(arr.length() == rowNumber+1) {
				arr.remove(rowNumber);
				json.put("more", true);
			}else {
				json.put("more", false);
			}	
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");

		} else {
			json.put("data", arr);
			json.put("message", "No data found with such parameters");
			json.put("status", "error");
		}
		json.put("userID", session.getAttribute("userID"));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

	}

}
