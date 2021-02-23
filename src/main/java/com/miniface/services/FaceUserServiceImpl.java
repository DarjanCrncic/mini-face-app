package com.miniface.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.daos.FaceUserDAOImpl;
import com.miniface.entities.FaceUserEntity;
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
		ResultSet set = null;
		JSONObject userJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			JSONArray jsonArray = fudao.login(username, password, connection, statement, set);
			if(!jsonArray.isEmpty()) {
				userJson = (JSONObject) jsonArray.get(0);
			}
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in loginFaceUser", ex);
		} finally {
			ConnectionClass.closeResultSet(set);
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return userJson;
	}

	@Override
	public int sendFriendRequest(int userID, String friendUsername) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();	
			result = fudao.sendFriendRequest(userID, friendUsername, connection, statement, set);
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
		ResultSet set = null;
		JSONArray friendsJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			friendsJson = fudao.showFriendsList(userID, connection, statement, set);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showFriendsList", ex);
		} finally {
			ConnectionClass.closeResultSet(set);
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return friendsJson;
	}
	
}
