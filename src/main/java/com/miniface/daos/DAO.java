package com.miniface.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface DAO <T> {
	
	Optional<T> get(Long id, Connection c, Statement s);

	List<T> getAll(Connection c, Statement s);
	
	int save(T t, Connection c, PreparedStatement s);
	
	void update(T t, T u, Connection c, Statement s);
	
	void delete(T t, Connection c, Statement s);
}
