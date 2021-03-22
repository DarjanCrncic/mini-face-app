package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.ProracuniEntryEntity;
import com.miniface.utils.ConnectionClass;
import com.miniface.utils.QueryHolder;

public class ProracuniServiceDAOImpl implements ProracuniServiceDAO {

	@Override
	public int createGroup(String name, Connection connection, PreparedStatement statement) throws SQLException {
		int result = 0;

		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_CREATE_GROUP);
		statement.setString(1, name);
		result = statement.executeUpdate();
		return result;
	}

	@Override
	public int updateEntry(ProracuniEntryEntity entry, String operation, Connection connection, PreparedStatement statement) throws SQLException {
		int result = 0;

		if (operation.equals("add")) {
			statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_CREATE_ENTRY);
			statement.setString(1, Integer.toString(entry.getType()));
			statement.setString(3, Integer.toString(entry.getLocal()));
			statement.setString(2, Boolean.toString(entry.getReturnTrip()));
			statement.setString(4, Integer.toString(entry.getWeight()));
			statement.setString(5, Integer.toString(entry.getGroupID()));
		}

		if (operation.equals("edit")) {
			statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_UPDATE_ENTRY);
			statement.setString(1, Integer.toString(entry.getType()));
			statement.setString(3, Integer.toString(entry.getLocal()));
			statement.setString(2, Boolean.toString(entry.getReturnTrip()));
			statement.setString(4, Integer.toString(entry.getWeight()));
			statement.setString(5, Integer.toString(entry.getID()));
		}

		if (operation.equals("delete")) {
			statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_DELETE_ENTRY);
			statement.setString(1, Integer.toString(entry.getID()));
		}
		
		result = statement.executeUpdate();
		return result;
	}

	@Override
	public JSONArray getGroups(Connection connection, PreparedStatement statement) throws SQLException {
		JSONArray arr = null;
		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_GET_GROUPS);
		arr = ConnectionClass.executePreparedStatement(statement);
		return arr;
	}

	@Override
	public JSONArray getEntries(int groupID, Connection connection, PreparedStatement statement) throws SQLException {
		JSONArray arr = null;
		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_GET_ENTRIES);
		statement.setString(1, Integer.toString(groupID));
		arr = ConnectionClass.executePreparedStatement(statement);
		return arr;
	}

	@Override
	public JSONArray getPrice(Connection connection, PreparedStatement statement) throws SQLException{
		JSONArray arr = null;
		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_GET_PRICE);
		arr = ConnectionClass.executePreparedStatementWithFloat(statement);
		return arr;
	}
	
	@Override
	public int updatePrice(int priceEntryID, float price, Connection connection, PreparedStatement statement) throws SQLException{
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_UPDATE_PRICE);
		statement.setString(1, Float.toString(price));
		statement.setString(2, Integer.toString(priceEntryID));
		result = statement.executeUpdate();
		return result;
	}

	@Override
	public JSONArray getTypes(Connection connection, PreparedStatement statement) throws SQLException{
		JSONArray arr = null;
		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_GET_TYPES);
		arr = ConnectionClass.executePreparedStatement(statement);
		return arr;
	}
	
	@Override
	public int lockGroup(int groupID, Connection connection, PreparedStatement statement) throws SQLException{
		int result = 0;
		statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_LOCK_GROUP);
		statement.setString(1, Integer.toString(groupID));
		result = statement.executeUpdate();
		return result;
	}
	
	@Override
	public JSONArray getPreviewData(String query, Connection connection, PreparedStatement statement) throws SQLException {
		JSONArray arr = null;
		String finalQuery = QueryHolder.SQL.PRORACUNI_GET_PREVIEW.replaceAll("%placeholder%", query);
		statement = connection.prepareStatement(finalQuery);
		arr = ConnectionClass.executePreparedStatementWithFloat(statement);
		return arr;
	}
	
	@Override
	public int savePreviewPart(JSONArray previewParts, Connection connection, PreparedStatement statement) throws SQLException {
		JSONArray arr = null;
		int result = 0;
		statement = connection.prepareStatement("SELECT max(preview_id) as maxid FROM proracuni_previews");
		arr = ConnectionClass.executePreparedStatementWithFloat(statement);
		int previewID;
		if(arr.length() == 0 || arr.getJSONObject(0).getInt("MAXID") == 0) {
			previewID = 1;
		}else {
			previewID = arr.getJSONObject(0).getInt("MAXID")+1;
		}
		
		for (int i=0; i<previewParts.length(); i++) {
			statement = connection.prepareStatement(QueryHolder.SQL.PRORACUNI_INSERT_PREVIEW_PART);
			statement.setString(1, Integer.toString(previewParts.getJSONObject(i).getInt("partType")));
			statement.setString(2, previewParts.getJSONObject(i).getString("typeTitle"));
			statement.setString(3, previewParts.getJSONObject(i).getString("subtitleTuzemno"));
			statement.setString(4, previewParts.getJSONObject(i).getString("subtitleInozemno"));
			statement.setString(5, previewParts.getJSONObject(i).getString("itemsTuzemno"));
			statement.setString(6, previewParts.getJSONObject(i).getString("itemsInozemno"));
			statement.setString(7, Integer.toString(previewID));
			result = statement.executeUpdate();
		}
		if(result > 0) {
			result = previewID;
		}else {
			result = 0;
		}
		
		return result;
	}

}
