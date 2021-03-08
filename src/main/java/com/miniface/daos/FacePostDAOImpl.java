package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.entities.FacePostEntity;
import com.miniface.listeners.ContextListener;
import com.miniface.utils.ConcatSQLSearch;
import com.miniface.utils.ConnectionClass;
import com.miniface.utils.QueryHolder;
import com.miniface.utils.UtilsMail;

public class FacePostDAOImpl implements FacePostDAO {

	@Override
	public JSONArray showVissiblePosts(int userID, Connection connection, PreparedStatement statement) throws SQLException {
		
		StringBuilder str = new StringBuilder();
		str.append(QueryHolder.SQL.GET_VISSIBLE_POSTS.replaceAll("%placeholder%", ""));

		statement = connection.prepareStatement(str.toString());
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(userID));
		statement.setString(3, Integer.toString(userID));
		statement.setString(4, Integer.toString(userID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}

	@Override
	public JSONArray searchVissiblePosts(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, int pageNumber, int rowNumber, Connection connection, PreparedStatement statement) throws SQLException {

		String[] caseAll = { "FP.TITLE", "FP.BODY", "FP.TYPE", "(FU.NAME || ' ' || FU.SURNAME)" };
		String placeholder;
		if(words.get(0).toString().isBlank() || words.get(0).toString().isEmpty()) {
			placeholder = "";
		}else {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", filters, words, logicalOperand, wordPosition, caseAll);
		}
		StringBuilder str = new StringBuilder();
		str.append(QueryHolder.SQL.GET_VISSIBLE_POSTS.replaceAll("%placeholder%", placeholder));
		str.append(" OFFSET " + (pageNumber-1)*rowNumber + " ROWS FETCH NEXT " + (rowNumber+1) + "ROWS ONLY");
		statement = connection.prepareStatement(str.toString());
		
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(userID));
		statement.setString(3, Integer.toString(userID));
		statement.setString(4, Integer.toString(userID));

		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}

	@Override
	public JSONObject createPost(int userID, int groupID, FacePostEntity post, String type, Connection connection, PreparedStatement statement) throws SQLException {

		int result = 0;
		JSONArray arr = new JSONArray();
		JSONObject newPost = null;

		statement = connection.prepareStatement(QueryHolder.SQL.CREATE_POST, new String[] { "ID" });
		statement.setString(1, post.getTitle());
		statement.setString(2, post.getBody());
		statement.setString(3, Integer.toString(userID));
		if(type.equals("user")) {
			statement.setString(4, "user");
		}
		if(type.equals("group")){
			statement.setString(4, "group");
		}

		statement.executeUpdate();
		ResultSet generatedKeys = statement.getGeneratedKeys();

		if (generatedKeys.next()) {
			int postID = generatedKeys.getInt(1);
			if(type.equals("user")) {
				statement = connection.prepareStatement(QueryHolder.SQL.CONNECT_USER_TO_POST);
				statement.setString(1, Integer.toString(userID));
			}
			if(type.equals("group")){
				statement = connection.prepareStatement(QueryHolder.SQL.CONNECT_GROUP_TO_POST);
				statement.setString(1, Integer.toString(groupID));
			}
			
			statement.setString(2, Integer.toString(postID));
			result = statement.executeUpdate();

			if (result == 1) {
				statement = connection.prepareStatement(QueryHolder.SQL.GET_POST_BY_ID);
				statement.setString(1, Integer.toString(postID));
				arr = ConnectionClass.executePreparedStatement(statement);
				
				sendNotifications(groupID, post, connection, statement);
			}
		}
		generatedKeys.close();
		
		if (arr.length() > 0) {
			newPost = arr.getJSONObject(0);
		}

		return newPost;
	}
	
	@Override
	public void sendNotifications(int groupID, FacePostEntity post, Connection connection, PreparedStatement statement) throws SQLException{
		statement = connection.prepareStatement(QueryHolder.SQL.GET_GROUP_MEMBERS);
		statement.setString(1, Integer.toString(groupID));
		JSONArray arr = ConnectionClass.executePreparedStatement(statement);
		
		if(arr!=null) {
			for(int i=0; i<arr.length(); i++) {
				if(arr.getJSONObject(i).optString("NOTIFY").equals("true")) {
					/// thread for sending email, uses global executor defined in ContextListener
			    	ContextListener.executor.execute(new Runnable() {
			            @Override
			            public void run() {
			                UtilsMail.sendEmail("minifaceapp@gmail.com", post.getTitle() + ":\n" + post.getBody(), "New post!");   
			            }
			        });
				}
			}
		}	
	}

	@Override
	public int deletePost(String postId, String type, Connection connection, PreparedStatement statement) throws SQLException {

		int result = 0;
		
		if(type.equals("user")) {
			statement = connection.prepareStatement(QueryHolder.SQL.DELETE_POST_FROM_USER);
		}
		if(type.equals("group")) {
			statement = connection.prepareStatement(QueryHolder.SQL.DELETE_POST_FROM_GROUP);
		}
		statement.setString(1, postId);
		result = statement.executeUpdate();

		if (result == 1) {
			result = 0;
			statement = connection.prepareStatement(QueryHolder.SQL.DELETE_POST_BY_ID);
			statement.setString(1, postId);
			result = statement.executeUpdate();
		}
		return result;
	}

	@Override
	public JSONObject editPost(String postId, String title, String body, Connection connection, PreparedStatement statement) throws SQLException {
		JSONObject editedPost = null;
		JSONArray arr = new JSONArray();
		int result = 0;

		statement = connection.prepareStatement(QueryHolder.SQL.EDIT_POST_BY_ID);
		statement.setString(3, postId);
		statement.setString(1, title);
		statement.setString(2, body);

		result = statement.executeUpdate();

		if (result == 1) {
			statement = connection.prepareStatement(QueryHolder.SQL.GET_POST_BY_ID);
			statement.setString(1, postId);
			arr = ConnectionClass.executePreparedStatement(statement);
		}

		if (arr.length() > 0)
		{
			editedPost = arr.getJSONObject(0);
		}

		return editedPost;
	}
	
	@Override
	public JSONArray showVissiblePostsForGroup(int groupID, Connection connection, PreparedStatement statement) throws SQLException {		
		statement = connection.prepareStatement(QueryHolder.SQL.SHOW_POSTS_FOR_GROUP);
		statement.setString(1, Integer.toString(groupID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public int likePostOrComment(int userID, int likedEntity, String type, Connection connection, PreparedStatement statement) throws SQLException {
		int result = 0;
		if(type.equals("post")) {
			statement = connection.prepareStatement(QueryHolder.SQL.LIKE_POST);
		}
		if(type.equals("comment")) {
			statement = connection.prepareStatement(QueryHolder.SQL.LIKE_COMMENT);
		}
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(likedEntity));
		result = statement.executeUpdate();
		return result;
	}
	
	@Override
	public int createNewComment(int userID, int postId, String comment, Connection connection, PreparedStatement statement) throws SQLException{
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.CREATE_COMMENT);
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(postId));
		statement.setString(3, comment);
		
		result = statement.executeUpdate();
		return result;
	}
	
	@Override
	public JSONArray getCommentsForPost(int postId, Connection connection, PreparedStatement statement) throws SQLException{
		statement = connection.prepareStatement(QueryHolder.SQL.GET_COMMENTS_FOR_POST);
		statement.setString(1, Integer.toString(postId));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public JSONArray getLikes(int entityID, String type, Connection connection, PreparedStatement statement) throws SQLException{
		if(type.equals("commentLikes")) {
			statement = connection.prepareStatement(QueryHolder.SQL.GET_LIKES_FOR_COMMENT);
		}
		if(type.equals("postLikes")) {
			statement = connection.prepareStatement(QueryHolder.SQL.GET_LIKES_FOR_POST);
		}
		statement.setString(1, Integer.toString(entityID));
		
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public int deleteComment(int commentID, Connection connection, PreparedStatement statement) throws SQLException {
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.DELETE_COMMENT);
		statement.setString(1, Integer.toString(commentID));
		
		result = statement.executeUpdate();
		return result;
	}
}
