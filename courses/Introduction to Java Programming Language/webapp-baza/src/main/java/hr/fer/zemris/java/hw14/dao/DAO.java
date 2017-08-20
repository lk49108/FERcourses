package hr.fer.zemris.java.hw14.dao;


import java.util.List;

import hr.fer.zemris.java.hw14.servlets.glasanje.IncrementElems;

/**
 * Interface toward dataBase part of this web-application. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface DAO {

	/**
	 * Gets all available elements from database.
	 * @param <T>
	 * @return Database elements.
	 * @throws DAOException in case of mistake.
	 */
	public <T> List<T> getBasicInputList() throws DAOException;
	
	/**
	 * Gets element from database defined with id parameter.
	 * @param <T>
	 * @param id ID of element which is to be returned.
	 * @return Element from database.
	 * @throws DAOException in case of mistake.
	 */
	public <T> T getInput(long id) throws DAOException;
	
	/**
	 * Increments(by 1) one of element's property. Element is defined with ID
	 * parameter and property with {@link IncrementElems} appropriate instance.
	 * By default this method throws {@link DAOException}. If class which implements this interface
	 * wants for this method do something meaningful it must override it.
	 * @param ID ID of element.
	 * @param elem Element of database table row which is to be incremented by 1. 
	 * @throws DAOException by default.
	 */
	public default void incrementElement(long ID, IncrementElems elem){
		throw new DAOException();
	}
}