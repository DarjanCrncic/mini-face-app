package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.entities.FacePostEntity;

public interface FacePostDAO {
	
	public JSONArray showVissiblePosts(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray searchVissiblePosts(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, int pageNumber, int rowNumber, Connection connection, PreparedStatement statement) throws SQLException;
	
	public JSONObject createPost(int userID, int groupID, FacePostEntity post, String type, Connection connection, PreparedStatement statement) throws SQLException;

	public int deletePost(String postId, String type, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONObject editPost(String postId, String title, String body, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray showVissiblePostsForGroup(int groupID, Connection connection, PreparedStatement statement) throws SQLException;
	
	public int likePostOrComment(int userID, int likedEntity, String type, Connection connection, PreparedStatement statement) throws SQLException;

	public int createNewComment(int userID, int postId, String comment, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray getCommentsForPost(int postId, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray getLikes(int entityID, String type, Connection connection, PreparedStatement statement) throws SQLException;

	public int deleteComment(int commentID, Connection connection, PreparedStatement statement) throws SQLException;
	
}
