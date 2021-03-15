package com.miniface.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.daos.FacePostDAOImpl;
import com.miniface.entities.FacePostEntity;
import com.miniface.utils.ConnectionClass;

public class FacePostServiceImpl implements FacePostService{
	
	static final Logger LOGGER = Logger.getLogger(FacePostServiceImpl.class);

	@Override
	public JSONObject createPost(int userID, int groupID, FacePostEntity post, String type) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONObject newPost = null;

		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FacePostDAOImpl fpdao = new FacePostDAOImpl();	
			newPost = fpdao.createPost(userID, groupID, post, type, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in createPost", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return newPost;
	}
	
	@Override
	public JSONArray showVissiblePosts(int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray vissiblePosts = null;
		try { 
			connection = ConnectionClass.getConnection();
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			vissiblePosts = fpdao.showVissiblePosts(userID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showVissiblePosts", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return vissiblePosts;
	}
	
	@Override
	public JSONArray searchVissiblePosts(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, int pageNumber, int rowNumber) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray vissiblePosts = null;
		
		try { 
			connection = ConnectionClass.getConnection();
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			vissiblePosts = fpdao.searchVissiblePosts(userID, filters, words, logicalOperand, wordPosition, pageNumber, rowNumber, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in searchVissiblePosts", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return vissiblePosts;
	}
	
	@Override
	public int deletePost(String postId, String type) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			result = fpdao.deletePost(postId, type, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in deletePost", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public JSONObject editPost(String postId, String title, String body) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONObject editedPost = null;

		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FacePostDAOImpl fpdao = new FacePostDAOImpl();	
			editedPost = fpdao.editPost(postId, title, body, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in editPost", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return editedPost;
	}
	
	@Override
	public JSONArray showVissiblePostsForGroup(int groupID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray vissiblePosts = null;
		try { 
			connection = ConnectionClass.getConnection();
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			vissiblePosts = fpdao.showVissiblePostsForGroup(groupID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showVissiblePostsForGroup", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return vissiblePosts;
	}
	
	@Override
	public int likePostOrComment(int userID, int likedEntity, String type) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			result = fpdao.likePostOrComment(userID, likedEntity, type, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in likePostOrComment", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public int createComment(int userID, int postId, String comment) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			result = fpdao.createNewComment(userID, postId, comment, connection, statement);
			connection.commit();
		} catch (SQLException ex) {
			LOGGER.error("SQLException in createComment", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return result;
	}
	
	@Override
	public JSONArray getCommentsForPost(int postId) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray comments = null;
		try { 
			connection = ConnectionClass.getConnection();
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			comments = fpdao.getCommentsForPost(postId, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getCommentsForPost", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return comments;
	}
	@Override
	public JSONArray getLikes(int entityID, String type) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray likes = null;
		try { 
			connection = ConnectionClass.getConnection();
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			likes = fpdao.getLikes(entityID, type, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getLikes", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return likes;
	}
	
	@Override
	public int deleteComment(int commentID) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			result = fpdao.deleteComment(commentID, connection, statement);
			connection.commit();
		} catch (SQLException ex) {
			LOGGER.error("SQLException in deleteComment", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return result;
	}
	
	@Override
	public JSONArray getPostByID(int postID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray post = null;
		try { 
			connection = ConnectionClass.getConnection();
			FacePostDAOImpl fpdao = new FacePostDAOImpl();
			post = fpdao.getPostByID(postID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getPostByID", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return post;
		
	}

}
