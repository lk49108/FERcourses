package hr.fer.zemris.java.hw14.sql;

import java.sql.Connection;

/**
 * Stores thread dependent connections toward database into connections instance variable.
 * 
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SQLConnectionProvider {

	/**
	 * Storage for connections.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Sets connection for current thread. If null is provided removes connection.
	 * 
	 * @param con Connection toward database.
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * 
	 * Gets connection which current thread is allowed to use.
	 * @return Connection towards database.
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}