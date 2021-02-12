package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.JsonArray;

import com.miniface.entities.FaceUserEntity;
import com.miniface.utils.ConnectionClass;
import com.miniface.utils.SqlStringsClass;

public class FaceUserDAOImpl implements FaceUserDAO {

	@Override
	public int register(FaceUserEntity user, Connection connection, PreparedStatement statement) throws SQLException {

		int result = 0;
		statement = connection.prepareStatement(SqlStringsClass.getRegisterUser());
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getName());
		statement.setString(4, user.getSurname());
		statement.setString(5, user.getEmail());

		result = statement.executeUpdate();
		return result;
	}

	@Override
	public JsonArray login(String username, String password, Connection connection, PreparedStatement statement, ResultSet set) throws SQLException{

		statement = connection.prepareStatement(SqlStringsClass.getLoginUser());
		statement.setString(1, username);
		statement.setString(2, password);
		
		JsonArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);
			
		System.out.println(jsonArray.toString());					

		return jsonArray;
	}

}
