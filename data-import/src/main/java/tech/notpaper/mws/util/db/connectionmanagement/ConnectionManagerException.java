package tech.notpaper.mws.util.db.connectionmanagement;

public class ConnectionManagerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6381556262367238746L;
	
	public ConnectionManagerException(String message) {
		super(message);
	}
	
	public ConnectionManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
