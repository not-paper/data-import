package tech.notpaper.mws.util.db.connectionmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import tech.notpaper.mws.dataimport.Set;

public class MySqlConnectionManager extends AbstractConnectionManager {
	private String host;
	private String port;
	private String user;
	private String pass;
	private String db;
	/*
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ConnectionManagerException("Failed to load mysql jdbc driver.", e);
		}
	}*/
	
	public MySqlConnectionManager(String host, String port, String user, String pass, String db) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.db = db;
		this.conn = buildConnection();
	}
	
	public MySqlConnectionManager(String host, String user, String pass, String db) {
		this(host, null, user, pass, db);
	}

	@Override
	protected Connection buildConnection() {
		String portString = this.port == null ? "" : ":" + this.port;
		String connectionString = "jdbc:mysql://" + this.host + portString + "/" + this.db + "?" + "user=" + this.user + "&password=" + this.pass;
		Connection c;
		try {
			c = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			throw new ConnectionManagerException("Failed to connect to the database.", e);
		}
		return c;
	}
}
