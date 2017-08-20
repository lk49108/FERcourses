/**
 * 
 */
package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.sql.SQLDAOPoll;

/**
 * 
 * Singleton class which knows what kind of instance he must return(and which
 * allows access to database part of web application).
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DAOPollProvider {

	/**
	 * {@link SQLDAOPoll} instance.
	 */
	private static DAO dao = new SQLDAOPoll();
	
	/**
	 * Getter for {@link #dao} property.
	 * 
	 * @return Object which encapsulates access to database.
	 */
	public static DAO getDao() {
		return dao;
	}
}
