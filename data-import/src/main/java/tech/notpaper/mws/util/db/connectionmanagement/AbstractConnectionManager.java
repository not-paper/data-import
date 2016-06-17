package tech.notpaper.mws.util.db.connectionmanagement;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public abstract class AbstractConnectionManager implements ConnectionManager {
	
	protected Connection conn;

	@Override
	public Connection getConnection() throws SQLException {
		if(conn == null || (conn != null && conn.isClosed())) {
			this.conn = this.buildConnection();
		}
		
		return conn;
	}

	@Override
	public void close() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// suppressing error since we are closing anyway
		}
		DbUtils.closeQuietly(conn);
	}

	@Override
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to commit changes on the connection object.", e);
		}
	}

	@Override
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			//failed to roll back, opening new connection
			
		}
	}
	
	protected abstract Connection buildConnection();

}
