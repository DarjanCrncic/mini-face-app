package com.miniface.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.entities.FacePostEntity;

public interface FacePostService {
	
	public JSONObject createPost(int userID, int groupID, FacePostEntity post, String type);
	
	public JSONArray showVissiblePosts(int userID);

	public JSONArray searchVissiblePosts(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition);

	public int deletePost(String postId, String type);

	public JSONObject editPost(String postId, String title, String body);

	public JSONArray showVissiblePostsForGroup(int groupID);

	public int likePostOrComment(int userID, int likedEntity, String type);

	public int createComment(int userID, int postId, String comment);

	public JSONArray getCommentsForPost(int postId);

	public JSONArray getLikes(int entityID, String type);

	public int deleteComment(int commentID);
}
