package com.miniface.daos;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceUserEntity;
import com.miniface.entities.InfoEntity;
import com.miniface.utils.ConcatSQLSearch;
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
	public JSONArray login(String username, String password, Connection connection, PreparedStatement statement) throws SQLException {

		statement = connection.prepareStatement(QueryHolder.SQL.LOGIN_USER);
		statement.setString(1, username);
		statement.setString(2, password);

		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		System.out.println(jsonArray.toString());

		return jsonArray;
	}



	@Override
	public int sendFriendRequest(int userID, int friendID, Connection connection, PreparedStatement statement) throws SQLException {

		int result = 0;

		statement = connection.prepareStatement(QueryHolder.SQL.CREATE_FRIEND_REQUEST);
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(friendID));
		statement.setString(3, "1");
		result = statement.executeUpdate();
		
		return result;
	}

	@Override
	public JSONArray showFriendsList(int userID, Connection connection, PreparedStatement statement)  throws SQLException{
		
		statement = connection.prepareStatement(QueryHolder.SQL.GET_LIST_OF_FRIENDS);
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(userID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public JSONArray findOtherPeople(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, Connection connection, PreparedStatement statement) throws SQLException{
		
		String[] caseAll = { "FU.SURNAME", "FU.NAME" };
		
		if(words.get(0).toString().isBlank()) {
			statement = connection.prepareStatement(QueryHolder.SQL.GET_OTHER_PEOPLE.replaceAll("%placeholder%", ""));
		}else {
			String placeholder = ConcatSQLSearch.createSQLQueryAddition("and", filters, words, logicalOperand, wordPosition, caseAll);
			statement = connection.prepareStatement(QueryHolder.SQL.GET_OTHER_PEOPLE.replaceAll("%placeholder%", placeholder));
		}
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(userID));
		statement.setString(3, Integer.toString(userID));
		
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);
		return jsonArray;
	}
	
	@Override
	public JSONArray showFriendPendingRequests(int userID, Connection connection, PreparedStatement statement) throws SQLException {

		statement = connection.prepareStatement(QueryHolder.SQL.GET_ALL_FRIEND_PENDING_REQUESTS);
		statement.setString(1, Integer.toString(userID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public int updateFriendRequest(int friendID, int userID, String updateType, Connection connection, PreparedStatement statement) throws SQLException{
		
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.UPDATE_FRIEND_REQUEST_STATUS);
		if(updateType.equals("accept")) {
			statement.setString(1, "2");
		}
		if(updateType.equals("decline")) {
			statement.setString(1, "3");
		}
		statement.setString(2, Integer.toString(friendID));
		statement.setString(3, Integer.toString(userID));
		result = statement.executeUpdate();
		
		if(result > 0 && updateType.equals("accept")) {
			result = 0;
			statement = connection.prepareStatement(QueryHolder.SQL.ADD_TO_FRIENDS_TABLE);
			statement.setString(1, Integer.toString(friendID));
			statement.setString(2, Integer.toString(userID));
			result = statement.executeUpdate();
		}
		
		return result;
	}
	
	@Override
	public JSONArray showGroupPendingRequsts(int userID, Connection connection, PreparedStatement statement) throws SQLException{
		statement = connection.prepareStatement(QueryHolder.SQL.GET_ALL_GROUP_PENDING_REQUESTS);
		statement.setString(1, Integer.toString(userID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public int updateImage(int userID, InputStream inputStream, Connection connection, PreparedStatement statement) throws SQLException {
		
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.UPDATE_USER_IMAGE);
		statement.setBlob(1, inputStream);
		statement.setString(2, Integer.toString(userID));
		
		result = statement.executeUpdate();
		return result;
	}
	
	
	@Override
	public InfoEntity getUserInfo(int userID, Connection connection, PreparedStatement statement) throws SQLException {
		
		InfoEntity infoEntity = new InfoEntity();
		
		statement = connection.prepareStatement(QueryHolder.SQL.GET_USER_INFO);
		statement.setString(1, Integer.toString(userID));
		ResultSet set = statement.executeQuery();
		
		if(set.next()) {
			infoEntity.setBlob(set.getBlob("IMAGE"));
			if(set.getBlob("IMAGE")==null) {
				infoEntity.setImage(null);
			}else {
				infoEntity.setImage(set.getBlob("IMAGE").getBinaryStream());
			}
			infoEntity.setAge(set.getInt("AGE"));
			infoEntity.setCity(set.getString("CITY"));
			infoEntity.setCountry(set.getString("COUNTRY"));
			infoEntity.setName(set.getString("NAME"));
			infoEntity.setGender(set.getString("GENDER"));
			infoEntity.setUsername(set.getString("USERNAME"));
		}
		
		return infoEntity;
	}
	
	@Override
	public int updateInfo(int userID, String username, String country, String city, String age, String gender, Connection connection, PreparedStatement statement) throws SQLException{
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.UPDATE_USER_INFO);
		statement.setString(1, username);
		statement.setString(2, country);
		statement.setString(3, city);
		statement.setString(4, age);
		statement.setString(5, gender);		
		statement.setString(6, Integer.toString(userID));
				
		result = statement.executeUpdate();
		return result;
	}

}







