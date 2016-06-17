package tech.notpaper.mws.util.db.connectionmanagement;

public class ConnectionManagerFactory {
	
	public static ConnectionManager getMySqlConnectionManager(String host, String port, String user, String pass, String db) {
		return new MySqlConnectionManager(host, port, user, pass, db);
	}
	
	public static ConnectionManager getMySqlConnectionManager(String host, String user, String pass, String db) {
		return new MySqlConnectionManager(host, user, pass, db);
	}
}
