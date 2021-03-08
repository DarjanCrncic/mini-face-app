package com.miniface.daos;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceUserEntity;
import com.miniface.entities.InfoEntity;

public interface FaceUserDAO {
	
	public JSONArray login(String username, String password, Connection connection, PreparedStatement statement) throws SQLException;

	public int register(FaceUserEntity user, Connection connection, PreparedStatement statement) throws SQLException;
	
	public int sendFriendRequest(int userID, int friendUsername, Connection connection, PreparedStatement statement) throws SQLException;
	
	public JSONArray showFriendsList(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray findOtherPeople(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray showFriendPendingRequests(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public int updateFriendRequest(int friendID, int userID, String updateType, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray showGroupPendingRequsts(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public int updateImage(int userID, InputStream inputStream, Connection connection, PreparedStatement statement) throws SQLException;

	public InfoEntity getUserInfo(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public int updateInfo(int userID, String username, String country, String city, String age, String gender, Connection connection, PreparedStatement statement) throws SQLException;

	public int updateNotify(int userID, boolean notify, Connection connection, PreparedStatement statement) throws SQLException;
	
}
