package tech.notpaper.mws.util.db.connectionmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlConnectionManager extends AbstractConnectionManager {

	private String dataSource = null;

	private String host;
	private String port;
	private String user;
	private String pass;
	private String db;

	public MySqlConnectionManager(String host, String port, String user, String pass, String db) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.db = db;
	}

	public MySqlConnectionManager(String host, String user, String pass, String db) {
		this(host, null, user, pass, db);
	}

	public MySqlConnectionManager(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected Connection buildConnection() {
		if (dataSource != null) {
			Context ctx;
			DataSource ds;
			try {
				ctx = new InitialContext();
				ctx = (Context) ctx.lookup("java:comp/env");
				ds = (DataSource) ctx.lookup(dataSource);
				if (ds != null) {
					return ds.getConnection();
				} else {
					throw new ConnectionManagerException("Unable to connect to the database.");
				}
			} catch (NamingException | SQLException e) {
				throw new ConnectionManagerException("Unable to connect to the database.", e);
			}
		} else {
			String portString = this.port == null ? "" : ":" + this.port;
			String connectionString = "jdbc:mysql://" + this.host + portString + "/" + this.db + "?" + "user="
					+ this.user + "&password=" + this.pass;
			Connection c;
			try {
				c = DriverManager.getConnection(connectionString);
			} catch (SQLException e) {
				throw new ConnectionManagerException("Failed to connect to the database.", e);
			}
			return c;
		}

	}

}