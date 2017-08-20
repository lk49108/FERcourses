package hr.fer.zemris.java.tecaj_13.dao;

/**
 * {@link Runtime} type exception.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor. 
	 * @param message {@link String} instance.
	 * @param cause {@link Throwable} instance.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * @param message {@link String} instance.
	 */
	public DAOException(String message) {
		super(message);
	}
}