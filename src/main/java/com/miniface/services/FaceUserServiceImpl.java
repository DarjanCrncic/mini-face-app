package com.miniface.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.log4j.Logger;

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
			LOGGER.error("SQLxception in addFaceUser", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public JsonObject loginFaceUser(String username, String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		JsonObject userJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceUserDAOImpl fudao = new FaceUserDAOImpl();
			JsonArray jsonArray = fudao.login(username, password, connection, statement, set);
			if(!jsonArray.isEmpty()) {
				userJson = jsonArray.getJsonObject(0);
			}
			
		} catch (SQLException ex) {
			LOGGER.error("SQLxception in loginFaceUser", ex);
		} finally {
			ConnectionClass.closeResultSet(set);
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return userJson;
	}
	
}
