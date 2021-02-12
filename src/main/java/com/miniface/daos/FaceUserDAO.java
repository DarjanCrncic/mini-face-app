package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.JsonArray;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserDAO {
	
	public JsonArray login(String username, String password, Connection connection, PreparedStatement statement, ResultSet set) throws SQLException;

	public int register(FaceUserEntity t, Connection c, PreparedStatement s) throws SQLException;
}
