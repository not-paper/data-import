package tech.notpaper.mws.util.db.connectionmanagement;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {
	
	public Connection getConnection() throws SQLException;
	public void close();
	public void commit();
	public void rollback();
}
