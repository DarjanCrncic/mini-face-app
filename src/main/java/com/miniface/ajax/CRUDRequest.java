package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;
import com.miniface.services.FaceGroupServiceImpl;
import com.miniface.services.FaceUserServiceImpl;
import com.miniface.utils.RequestValidator;

@WebServlet("/CRUDRequest")
public class CRUDRequest extends JSONServlet {
	private static final long serialVersionUID = 1L;

	public CRUDRequest() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException, JSONException {
		
		FaceUserServiceImpl fusi = new FaceUserServiceImpl();
		JSONArray arr = null;
	
		if(RequestValidator.validateRequest(request, "type").equals("friend"))
			arr = fusi.showFriendPendingRequests(Integer.parseInt((String) session.getAttribute("userID")));
		if(RequestValidator.validateRequest(request, "type").equals("group"))
			arr = fusi.showGroupPendingRequsts(Integer.parseInt((String) session.getAttribute("userID")));
		
		if (arr != null && !arr.isEmpty()) {
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");

		} else {
			json.put("message", "Cant retrieve data");
			json.put("status", "error");

		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException, JSONException {
		String operation = RequestValidator.validateRequest(request, "operation");
		String friendID = RequestValidator.validateRequest(request, "friendID");
		String type = RequestValidator.validateRequest(request, "type");
		String groupID = RequestValidator.validateRequest(request, "groupID");
		String receiverID = RequestValidator.validateRequest(request, "receiverID");

		FaceUserServiceImpl fusi = new FaceUserServiceImpl();
		FaceGroupServiceImpl fgsi = new FaceGroupServiceImpl();
		int result = 0;

		if (operation.equals("create") && friendID != null && type.equals("friend")) {
			result = fusi.sendFriendRequest(Integer.parseInt((String) session.getAttribute("userID")), Integer.parseInt(friendID));

			if (result == 0) {
				json.put("message", "Error sending friend request");
				json.put("status", "error");
			} else {
				json.put("message", "Friend request sent");
				json.put("status", "success");
			}
		}
		
		if((operation.equals("accept") || operation.equals("decline")) && friendID != null && type.equals("friend")) {
			result = fusi.updateFriendRequest(Integer.parseInt(friendID), Integer.parseInt((String) session.getAttribute("userID")), operation);
			
			if (result == 0) {
				json.put("message", "Error accepting/declining friend request");
				json.put("status", "error");
			} else {
				if(operation.equals("accept")) {
					json.put("message", "Friend request accepted");
				}else {
					json.put("message", "Friend request declined");
				}
				json.put("status", "success");
			}
		}
		
		if (operation.equals("create") && groupID != null && receiverID != null && type.equals("group")) {
			result = fgsi.sendGroupRequest(Integer.parseInt(receiverID), Integer.parseInt(groupID));

			if (result == 0) {
				json.put("message", "Error sending group request");
				json.put("status", "error");
			} else {
				json.put("message", "Group request sent");
				json.put("status", "success");
			}		
		}
		
		if((operation.equals("accept") || operation.equals("decline")) && groupID != null && type.equals("group")) {
			result = fgsi.updateGroupRequest(Integer.parseInt((String) session.getAttribute("userID")), Integer.parseInt(groupID), operation);
			
			if (result == 0) {
				json.put("message", "Error accepting/declining group request");
				json.put("status", "error");
			} else {
				if(operation.equals("accept")) {
					json.put("message", "Group request accepted");
				}else {
					json.put("message", "Group request declined");
				}
				json.put("status", "success");
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	
		
	}

}
