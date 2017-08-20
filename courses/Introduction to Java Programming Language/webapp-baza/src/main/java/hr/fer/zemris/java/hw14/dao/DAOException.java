package hr.fer.zemris.java.hw14.dao;

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
	 */
	public DAOException() {
	}

	/**
	 * Constructor. 
	 * @param message {@link String} instance.
	 * @param cause {@link Throwable} instance.
	 * @param enableSuppression boolean type parameter.
	 * @param writableStackTrace boolean type parameter.
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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

	/**
	 * Constructor.
	 * @param cause {@link Throwable} instance.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}