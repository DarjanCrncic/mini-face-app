package com.miniface.services;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.daos.FaceUserDAOImpl;
import com.miniface.entities.FaceUserEntity;
import com.miniface.entities.InfoEntity;
import com.miniface.utils.ConnectionClass;

public class FaceUserServiceImpl implements FaceUserService {
	
	static final Logger LOGGER = Logger.getLogger(FaceUserServiceImpl.class);
	
	@Override
	public int addFaceUser(FaceUserEntity user) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();	
			result = fudao.register(user, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLException in addFaceUser", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public JSONObject loginFaceUser(String username, String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONObject userJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			JSONArray jsonArray = fudao.login(username, password, connection, statement);
			if(!jsonArray.isEmpty()) {
				userJson = (JSONObject) jsonArray.get(0);
			}
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in loginFaceUser", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return userJson;
	}

	@Override
	public int sendFriendRequest(int userID, int friendID) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();	
			result = fudao.sendFriendRequest(userID, friendID, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in sendFriendRequest", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public JSONArray showFriendsList(int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray friendsJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			friendsJson = fudao.showFriendsList(userID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showFriendsList", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return friendsJson;
	}
	
	@Override
	public JSONArray findOtherPeople(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray othersJson = null;

		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			othersJson = fudao.findOtherPeople(userID, filters, words, logicalOperand, wordPosition, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in findOtherPeople", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return othersJson;
	}

	@Override
	public JSONArray showFriendPendingRequests(int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray allRequests = null;
		
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			allRequests = fudao.showFriendPendingRequests(userID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showFriendPendingRequests", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return allRequests;
	}
	
	@Override
	public int updateFriendRequest(int friendID, int userID, String updateType) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();	
			result = fudao.updateFriendRequest(friendID, userID, updateType, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in updateFriendRequest", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public JSONArray showGroupPendingRequsts(int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray allRequests = null;
		
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			allRequests = fudao.showGroupPendingRequsts(userID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showGroupPendingRequsts", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return allRequests;
	}
	
	@Override
	public int updateImage(int userID, InputStream inputStream) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();	
			result = fudao.updateImage(userID, inputStream, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in updateImage", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public InfoEntity getUserInfo(int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		InfoEntity infoEntity = null;
		
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			infoEntity = fudao.getUserInfo(userID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getUserInfo", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return infoEntity;
	}
	
	@Override
	public int updateInfo(int userID, String username, String country, String city, String age, String gender) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();	
			result = fudao.updateInfo(userID, username, country, city, age, gender, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in updateInfo", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
}
