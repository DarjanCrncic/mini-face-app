package com.miniface.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.controllers.RegisterUser;


public class ConnectionClass {
	static final Logger LOGGER = Logger.getLogger(RegisterUser.class); 

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/test");
			connection = ds.getConnection();
		} catch (NamingException ex) {
			LOGGER.error("NamingException in getConnection",ex);
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getConnection",ex);
		}
		return connection;
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException ex) {
			LOGGER.error("SQLException in closeConnection", ex);
		}
	}

	public static void closePreparedStatement(PreparedStatement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException ex) {
			LOGGER.error("SQLException in closePreparedStatement", ex);
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException ex) {
			LOGGER.error("SQLException in closeStatement", ex);
		}
	}

	public static void closeResultSet(ResultSet set) {
		try {
			if (set != null) {
				set.close();
			}
		} catch (SQLException ex) {
			LOGGER.error("SQLException in closeResultSet", ex);
		}
	}

	public static void doRollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException ex) {
			LOGGER.error("SQLException in doRollback", ex);
		}
	}

	public static JSONArray parseResultSetAsJSON(ResultSet resultSet) {
		
		JSONArray jsonArray = new JSONArray();
		try {
			while(resultSet.next()) {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnNumber = metaData.getColumnCount();
				JSONObject json = new JSONObject();
				
				for(int i=1; i<=columnNumber; i++) {
					String columnName = metaData.getColumnName(i);
									
					switch(metaData.getColumnType(i)) {
					case java.sql.Types.NUMERIC: json.put(columnName, resultSet.getInt(columnName)); break;
					case java.sql.Types.VARCHAR: json.put(columnName, (resultSet.getString(columnName) != null) ? resultSet.getString(columnName) : ""); break;
					case java.sql.Types.TIMESTAMP: json.put(columnName, (resultSet.getTimestamp(columnName) != null) ? resultSet.getTimestamp(columnName).toString() : ""); break;

					default: json.append(columnName, ""); break;
					}
					
				}
				jsonArray.put(json);
			}
		} catch (SQLException ex) {
			LOGGER.error("SQLException in parseResultSetAsJSON", ex);
		}finally {
			ConnectionClass.closeResultSet(resultSet);
		}

		return jsonArray;
	}
	
	public static JSONArray executePreparedStatement(PreparedStatement statement) throws SQLException{
		
		ResultSet set = statement.executeQuery();
		JSONArray arr = null;
		arr = parseResultSetAsJSON(set);
		ConnectionClass.closePreparedStatement(statement);
		return arr;
	}
	
	
}
