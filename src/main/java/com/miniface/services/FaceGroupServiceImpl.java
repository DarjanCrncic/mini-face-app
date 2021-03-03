package com.miniface.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.miniface.daos.FaceGroupDAOImpl;
import com.miniface.entities.FaceGroupEntity;
import com.miniface.utils.ConnectionClass;

public class FaceGroupServiceImpl implements FaceGroupService{

	static final Logger LOGGER = Logger.getLogger(FaceGroupServiceImpl.class);
		
	@Override
	public int createEditDeleteGroup(FaceGroupEntity faceGroup, String groupID, String operation) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();	
			result = fgdao.createEditDeleteGroup(faceGroup, groupID, operation, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in createEditDeleteGroup", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public JSONArray showGroupsList(int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray groupsJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();
			groupsJson = fgdao.showGroupsList(userID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in showGroupsList", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return groupsJson;
	}
	
	@Override
	public JSONArray findGroups(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray groupsJson = null;
			
		try { 
			connection = ConnectionClass.getConnection();
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();
			groupsJson = fgdao.findGroups(userID, filters, words, logicalOperand, wordPosition, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in findGroups", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return groupsJson;
	}
	
	@Override
	public JSONArray listGroupMembers(int groupID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray members = null;
		
		try {
			connection = ConnectionClass.getConnection();
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();
			members = fgdao.listGroupMembers(groupID, connection, statement);
		} catch (SQLException ex) {
			LOGGER.error("SQLException in listGroupMembers", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return members;

	}

	@Override
	public JSONArray listNotGroupMembers(int groupID, int userID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray notMembers = null;
		
		try {
			connection = ConnectionClass.getConnection();
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();
			notMembers = fgdao.listNotGroupMembers(groupID, userID, connection, statement);
		} catch (SQLException ex) {
			LOGGER.error("SQLException in listNotGroupMembers", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return notMembers;
	}
	
	@Override
	public int sendGroupRequest(int receiverID, int groupID) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();	
			result = fgdao.sendGroupRequest(receiverID, groupID, connection, statement);
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
	public int updateGroupRequest(int receiverID, int groupID, String operation) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try {
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			FaceGroupDAOImpl fgdao = new FaceGroupDAOImpl();	
			result = fgdao.updateGroupRequest(receiverID, groupID, operation, connection, statement);
			connection.commit();

		} catch (SQLException ex) {
			LOGGER.error("SQLxception in updateGroupRequest", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

}
