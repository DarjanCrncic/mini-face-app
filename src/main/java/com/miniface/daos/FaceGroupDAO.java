package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;

import com.miniface.entities.FaceGroupEntity;

public interface FaceGroupDAO {

	public JSONArray showGroupsList(int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray findGroups(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray listGroupMembers(int groupID, Connection connection, PreparedStatement statement) throws SQLException;

	public JSONArray listNotGroupMembers(int groupID, int userID, Connection connection, PreparedStatement statement) throws SQLException;

	public int sendGroupRequest(int receiverID, int groupID, Connection connection, PreparedStatement statement) throws SQLException;

	public int updateGroupRequest(int receiverID, int groupID, String operation, Connection connection, PreparedStatement statement) throws SQLException;

	public int createEditDeleteGroup(FaceGroupEntity faceGroup, String groupID, String operation, Connection connection, PreparedStatement statement) throws SQLException;

}
