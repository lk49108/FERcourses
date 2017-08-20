package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * 
 * Singleton class which knows what kind of instance he must return(and which
 * allows access to database part of web application).
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DAOProvider {

	/**
	 * {@link JPADAOPoll} instance.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter for {@link #dao} property.
	 * 
	 * @return Object which encapsulates access to database.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}