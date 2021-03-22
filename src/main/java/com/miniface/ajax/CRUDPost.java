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
import com.miniface.entities.FacePostEntity;
import com.miniface.services.FacePostServiceImpl;
import com.miniface.utils.InputValidator;
import com.miniface.utils.RequestValidator;

@WebServlet("/CRUDPost")
public class CRUDPost extends JSONServlet {
	private static final long serialVersionUID = 1L;
	private static final String success = "success";
	private static final String error = "error";

	public CRUDPost() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		String type = RequestValidator.validateRequest(request, "type");
		String postId = RequestValidator.validateRequest(request, "postId");
		String entityID = RequestValidator.validateRequest(request, "entityID");
		FacePostServiceImpl fpsi = new FacePostServiceImpl();
		JSONArray arr = null;
		
		if(postId != null && type.equals("comments")) {
			arr = fpsi.getCommentsForPost(Integer.parseInt(postId));
			
			if(arr != null) {
				json.put("data", arr);
				json.put("message", "Transaction successful");
				json.put("status", success);
			}else {
				json.put("message", "Error getting comments");
				json.put("status", error);
			}
		}
		
		if(entityID != null && (type.equals("postLikes") || type.equals("commentLikes"))) {
			arr = fpsi.getLikes(Integer.parseInt(entityID), type);
			if(arr != null) {
				json.put("data", arr.get(0));
				json.put("message", "Transaction successful");
				json.put("status", success);
			}else {
				json.put("message", "Error getting likes");
				json.put("status", error);
			}
		}
		json.put("userID", session.getAttribute("userID"));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	
	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String title = RequestValidator.validateRequest(request, "title");
		String body = RequestValidator.validateRequest(request, "body");
		String operation = RequestValidator.validateRequest(request, "operation");
		String postId = RequestValidator.validateRequest(request, "postId");
		String type = RequestValidator.validateRequest(request, "type");
		String groupID = RequestValidator.validateRequest(request, "groupID");
		String comment = RequestValidator.validateRequest(request, "comment");
		String commentID = RequestValidator.validateRequest(request, "commentID");
				
		FacePostServiceImpl fpsi = new FacePostServiceImpl();
		InputValidator inputValidator = new InputValidator();
		JSONObject newPost = null;
	
		// POST operation if add post was called
		if (operation.equals("add")) {

			FacePostEntity post = new FacePostEntity();
			post.setBody(body);
			post.setTitle(title);
			
			String[] errorMessages = inputValidator.validateInput(post);

			if (errorMessages.length == 0) {

				newPost = fpsi.createPost(Integer.parseInt((String) session.getAttribute("userID")), Integer.parseInt(groupID), post, type);

				if (newPost == null) {
					json.put("message", "Error creating post");
					json.put("status", error);
				} else {
					json.put("message", "Transaction successful");
					json.put("status", success);
				}
			} else {
				json.put("message", "Invalid inputs when creating post");
				json.put("status", error);
			}

			json.put("data", newPost);
		}
		
		// POST operation if delete post was called
		if (operation.equals("delete")) {
			int result = 0;

			if (postId != null) {
				result = fpsi.deletePost(postId, type);
			}

			if (result == 0) {
				json.put("message", "Error deleting post");
				json.put("status", error);
			} else {
				json.put("message", "Transaction successful");
				json.put("status", success);
			}
			
			json.put("data", new JSONObject().put("ID", postId));
		}
		
		// POST operation if edit post was called
		if(operation.equals("edit")) {
		
			if(postId != null && title != null && body != null) {
				newPost = fpsi.editPost(postId, title, body);
				
				if (newPost == null) {
					json.put("message", "Error editing post");
					json.put("status", error);
				} else {
					json.put("message", "Transaction successful");
					json.put("status", success);
				}
			} else {
				json.put("message", "Invalid inputs when editing post");
				json.put("status", error);
			}

			json.put("data", newPost);					
		}
		
		if(operation.equals("likePost")) {
			int result = 0;
			if(type.equals("post") && postId != null) {
				result = fpsi.likePostOrComment(Integer.parseInt((String) session.getAttribute("userID")), Integer.parseInt(postId), type);
			}
			if (result == 0) {
				json.put("message", "Error liking post");
				json.put("status", error);
			} else {
				json.put("message", "Transaction successful");
				json.put("status", success);
			}
		}
		
		if(operation.equals("likeComment")) {
			int result = 0;
			if(type.equals("comment") && commentID != null) {
				result = fpsi.likePostOrComment(Integer.parseInt((String) session.getAttribute("userID")), Integer.parseInt(commentID), type);
			}
			if (result == 0) {
				json.put("message", "Error liking comment");
				json.put("status", error);
			} else {
				json.put("message", "Transaction successful");
				json.put("status", success);
			}
		}
		
		if(operation.equals("comment")) {
			int result = 0;
			if(comment != null && postId != null) {
				result = fpsi.createComment(Integer.parseInt((String) session.getAttribute("userID")), Integer.parseInt(postId), comment);
			}
			if (result == 0) {
				json.put("message", "Error creating comment");
				json.put("status", error);
			} else {
				json.put("message", "Transaction successful");
				json.put("status", success);
			}				
		}
		
		if(operation.equals("deleteComment")) {
			int result = 0;
			if(commentID != null) {
				result = fpsi.deleteComment(Integer.parseInt(commentID));
			}
			if (result == 0) {
				json.put("message", "Error deleting comment");
				json.put("status", error);
			} else {
				json.put("message", "Transaction successful");
				json.put("status", success);
			}	
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	

	}

}
