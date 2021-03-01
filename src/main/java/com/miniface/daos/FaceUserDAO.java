package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserDAO {
	
	public JSONArray login(String username, String password, Connection connection, PreparedStatement statement) throws SQLException;

	public int register(FaceUserEntity user, Connection connection, PreparedStatement statement) throws SQLException;
	
	public int sendFriendRequest(int userID, int friendUsername, Connection connection, PreparedStatement statement) throws SQLException;
	
	public JSONArray showFriendsList(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray findOtherPeople(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray showFriendPendingRequests(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public int updateFriendRequest(int friendID, int userID, String updateType, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray showGroupPendingRequsts(int userID, Connection connection, PreparedStatement statement) throws SQLException;
	
}
