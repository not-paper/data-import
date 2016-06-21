package tech.notpaper.mws.util.db.connectionmanagement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlConnectionManager extends AbstractConnectionManager {

	public MySqlConnectionManager(String host, String port, String user, String pass, String db) {
		this.conn = buildConnection();
	}

	public MySqlConnectionManager(String host, String user, String pass, String db) {
		this(host, null, user, pass, db);
	}

	@Override
	protected Connection buildConnection() {
		Context ctx;
		DataSource ds;
		try {
			ctx = new InitialContext();
			ctx = (Context) ctx.lookup("java:comp/env");
			ds = (DataSource) ctx.lookup("jdbc/mws");
			if (ds != null) {
				return ds.getConnection();
			} else {
				throw new ConnectionManagerException("Unable to connect to the database.");
			}
		} catch (NamingException | SQLException e) {
			throw new ConnectionManagerException("Unable to connect to the database.", e);
		}
	}
}