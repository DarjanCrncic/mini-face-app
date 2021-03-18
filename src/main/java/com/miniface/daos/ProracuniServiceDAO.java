package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.ProracuniEntryEntity;

public interface ProracuniServiceDAO {

	public int createGroup(String name, Connection connection, PreparedStatement statement) throws SQLException;

	JSONArray getGroups(Connection connection, PreparedStatement statement) throws SQLException;

	int updateEntry(ProracuniEntryEntity entry, String operation, Connection connection, PreparedStatement statement) throws SQLException;

	JSONArray getEntries(int groupID, Connection connection, PreparedStatement statement) throws SQLException;

	JSONArray getPrice(Connection connection, PreparedStatement statement) throws SQLException;

	int updatePrice(int priceEntryID, float price, Connection connection, PreparedStatement statement) throws SQLException;

	JSONArray getTypes(Connection connection, PreparedStatement statement) throws SQLException;

	int lockGroup(int groupID, Connection connection, PreparedStatement statement) throws SQLException;

	JSONArray getPreviewData(String query, Connection connection, PreparedStatement statement) throws SQLException;

}
