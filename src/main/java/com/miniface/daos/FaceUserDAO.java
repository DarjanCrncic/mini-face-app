package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserDAO {
	
	public JSONArray login(String username, String password, Connection connection, PreparedStatement statement, ResultSet set) throws SQLException;

	public int register(FaceUserEntity user, Connection connection, PreparedStatement statement) throws SQLException;
	
	public int sendFriendRequest(int userID, String friendUsername, Connection connection, PreparedStatement statement, ResultSet set) throws SQLException;
	
	public JSONArray showFriendsList(int userID, Connection connection, PreparedStatement statement, ResultSet set) throws SQLException;
	
}
