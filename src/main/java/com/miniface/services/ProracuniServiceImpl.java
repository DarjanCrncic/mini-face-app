package com.miniface.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.miniface.daos.ProracuniServiceDAOImpl;
import com.miniface.entities.ProracuniEntryEntity;
import com.miniface.utils.ConnectionClass;

public class ProracuniServiceImpl implements ProracuniService{
	
	static final Logger LOGGER = Logger.getLogger(ProracuniService.class);
	
	@Override
	public int createGroup(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			result = psdao.createGroup(name, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in createGroup(proracuni)", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public int updateEntry(ProracuniEntryEntity entry, String operation) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			result = psdao.updateEntry(entry, operation, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in updateEntry(proracuni)", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}

	public JSONArray getGroups() {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray groups = null;
		try { 
			connection = ConnectionClass.getConnection();
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			groups = psdao.getGroups(connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getGroups(proracuni)", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return groups;
	}
	
	@Override
	public JSONArray getEntries(int groupID) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray entries = null;
		try { 
			connection = ConnectionClass.getConnection();
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			entries = psdao.getEntries(groupID, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getEntries(proracuni)", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return entries;
	}
	
	@Override
	public JSONArray getPrice() {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray price = null;
		try { 
			connection = ConnectionClass.getConnection();
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			price = psdao.getPrice( connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getPrice(proracuni)", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return price;
	}

	@Override
	public int updatePrice(int priceEntryID, float price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			result = psdao.updatePrice(priceEntryID, price, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in updatePrice(proracuni)", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public JSONArray getTypes() {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray types = null;
		try { 
			connection = ConnectionClass.getConnection();
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			types = psdao.getTypes( connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getPrice(proracuni)", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return types;
	}
	
	@Override
	public int lockGroup(int groupID) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			result = psdao.lockGroup(groupID, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in updatePrice(proracuni)", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public JSONArray getPreviewData(String query) {
		Connection connection = null;
		PreparedStatement statement = null;
		JSONArray arr = null;
		try { 
			connection = ConnectionClass.getConnection();
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			arr = psdao.getPreviewData(query, connection, statement);
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in getPreviewData(proracuni)", ex);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		return arr;
	}
	
	@Override
	public int savePreviewPart(JSONArray previewParts) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		
		try { 
			connection = ConnectionClass.getConnection();
			connection.setAutoCommit(false);
			ProracuniServiceDAOImpl psdao = new ProracuniServiceDAOImpl();
			result = psdao.savePreviewPart(previewParts, connection, statement);
			connection.commit();
			
		} catch (SQLException ex) {
			LOGGER.error("SQLException in savePreviewPart(proracuni)", ex);
			ConnectionClass.doRollback(connection);
		} finally {
			ConnectionClass.closePreparedStatement(statement);
			ConnectionClass.closeConnection(connection);
		}
		
		return result;
	}
	

}
