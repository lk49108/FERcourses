package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.sql.SQLDAOPollOption;

/**
 * 
 * Singleton class which knows what kind of instance he must return(and which
 * allows access to database part of web application).
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DAOPollOptionProvider {

	/**
	 * {@link SQLDAOPollOption} instance.
	 */
	private static DAO dao = new SQLDAOPollOption();
	
	/**
	 * Getter for {@link #dao} property.
	 * 
	 * @return Object which encapsulates access to database.
	 */
	public static DAO getDao() {
		return dao;
	}

	
	
}