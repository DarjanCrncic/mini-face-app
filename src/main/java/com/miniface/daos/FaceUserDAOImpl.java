package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceUserEntity;
import com.miniface.utils.ConnectionClass;
import com.miniface.utils.QueryHolder;

public class FaceUserDAOImpl implements FaceUserDAO {
	
	@Override
	public int register(FaceUserEntity user, Connection connection, PreparedStatement statement) throws SQLException {

		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.REGISTER_USER);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getName());
		statement.setString(4, user.getSurname());
		statement.setString(5, user.getEmail());

		result = statement.executeUpdate();
		return result;
	}

	@Override
	public JSONArray login(String username, String password, Connection connection, PreparedStatement statement,
			ResultSet set) throws SQLException {

		statement = connection.prepareStatement(QueryHolder.SQL.LOGIN_USER);
		statement.setString(1, username);
		statement.setString(2, password);

		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		System.out.println(jsonArray.toString());

		return jsonArray;
	}



	@Override
	public int sendFriendRequest(int userID, String friendUsername, Connection connection, PreparedStatement statement,
			ResultSet set) throws SQLException {

		int result = 0;

		statement = connection.prepareStatement(QueryHolder.SQL.FIND_USER_ID_BY_USERNAME);
		statement.setString(1, friendUsername);
		set = statement.executeQuery();

		if (set.next()) {
			int friendID = set.getInt(1);

			statement = connection.prepareStatement(QueryHolder.SQL.CREATE_FRIEND_REQUEST);
			statement.setString(1, Integer.toString(userID));
			statement.setString(2, Integer.toString(friendID));
			statement.setString(3, "1");
			result = statement.executeUpdate();
		}
		return result;
	}

	@Override
	public JSONArray showFriendsList(int userID, Connection connection, PreparedStatement statement, ResultSet set)  throws SQLException{
		
		statement = connection.prepareStatement(QueryHolder.SQL.GET_LIST_OF_FRIENDS);
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(userID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}



}







