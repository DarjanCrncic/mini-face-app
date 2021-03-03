package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceGroupEntity;
import com.miniface.utils.ConcatSQLSearch;
import com.miniface.utils.ConnectionClass;
import com.miniface.utils.QueryHolder;

public class FaceGroupDAOImpl implements FaceGroupDAO {
	
	@Override
	public JSONArray showGroupsList(int userID, Connection connection, PreparedStatement statement) throws SQLException {
		statement = connection.prepareStatement(QueryHolder.SQL.GET_LIST_OF_GROUPS.replaceAll("%placeholder%", ""));
		statement.setString(1, Integer.toString(userID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public JSONArray findGroups(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, Connection connection, PreparedStatement statement) throws SQLException {
		String[] caseAll = { "FG.NAME", "FG.DESCRIPTION", "(FU.NAME || ' ' || FU.SURNAME)" };
		String placeholder;
		if(words.get(0).toString().isBlank()) {
			placeholder = "";
		}else {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", filters, words, logicalOperand, wordPosition, caseAll);
		}
		statement = connection.prepareStatement(QueryHolder.SQL.GET_LIST_OF_GROUPS.replaceAll("%placeholder%", placeholder));
		statement.setString(1, Integer.toString(userID));
		
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);
		return jsonArray;
	}
	
	@Override
	public JSONArray listGroupMembers(int groupID, Connection connection, PreparedStatement statement) throws SQLException {
		statement = connection.prepareStatement(QueryHolder.SQL.GET_GROUP_MEMBERS);
		statement.setString(1, Integer.toString(groupID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public JSONArray listNotGroupMembers(int groupID, int userID, Connection connection, PreparedStatement statement) throws SQLException {
		statement = connection.prepareStatement(QueryHolder.SQL.GET_NOT_GROUP_MEMBERS);
		statement.setString(1, Integer.toString(userID));
		statement.setString(2, Integer.toString(userID));
		statement.setString(3, Integer.toString(groupID));
		JSONArray jsonArray = null;
		jsonArray = ConnectionClass.executePreparedStatement(statement);

		return jsonArray;
	}
	
	@Override
	public int sendGroupRequest(int receiverID, int groupID, Connection connection, PreparedStatement statement) throws SQLException{
		int result = 0;
		
		statement = connection.prepareStatement(QueryHolder.SQL.CREATE_GROUP_REQUEST);
		statement.setString(1, Integer.toString(receiverID));
		statement.setString(2, Integer.toString(groupID));
		result = statement.executeUpdate();
		
		return result;
	}
	
	@Override
	public int updateGroupRequest(int receiverID, int groupID, String operation, Connection connection, PreparedStatement statement) throws SQLException{
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.UPDATE_GROUP_REQUEST_STATUS);
		if(operation.equals("accept")) {
			statement.setString(1, "2");
		}
		if(operation.equals("decline")) {
			statement.setString(1, "3");
		}
		statement.setString(2, Integer.toString(receiverID));
		statement.setString(3, Integer.toString(groupID));
		result = statement.executeUpdate();
		
		if(result > 0 && operation.equals("accept")) {
			result = 0;
			statement = connection.prepareStatement(QueryHolder.SQL.CONNECT_USER_TO_GROUP);
			statement.setString(1, Integer.toString(receiverID));
			statement.setString(2, Integer.toString(groupID));
			result = statement.executeUpdate();
		}
		
		return result;
	}
	
	@Override
	public int createEditDeleteGroup(FaceGroupEntity faceGroup, String groupID, String operation, Connection connection, PreparedStatement statement) throws SQLException {
		int result = 0;
		
		if(operation.equals("create")) {
			statement = connection.prepareStatement(QueryHolder.SQL.CREATE_GROUP, new String[] { "ID" });
			statement.setString(1, faceGroup.getName());
			statement.setString(2, faceGroup.getDescription());
			statement.setString(3, Integer.toString(faceGroup.getOwnerId()));
			
			result = statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if(generatedKeys.next()) {
				int newGroupID = generatedKeys.getInt(1);
				
				statement = connection.prepareStatement(QueryHolder.SQL.CONNECT_USER_TO_GROUP);
				statement.setString(1, Integer.toString(faceGroup.getOwnerId()));
				statement.setString(2, Integer.toString(newGroupID));
				
				result = statement.executeUpdate();
			}
			generatedKeys.close();
		}
		
		if(operation.equals("edit")) {
			statement = connection.prepareStatement(QueryHolder.SQL.EDIT_GROUP);
			statement.setString(1, faceGroup.getName());
			statement.setString(2, faceGroup.getDescription());
			statement.setString(3, groupID);
			
			result = statement.executeUpdate();
		}
		
		return result;
	}

}
