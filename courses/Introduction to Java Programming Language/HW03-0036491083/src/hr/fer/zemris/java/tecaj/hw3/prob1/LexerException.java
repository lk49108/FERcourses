package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * This class represents an exception that extends RuntimeException class. This
 * exception can be thrown in process of tokenisation of text/string. For
 * example, when <code>EOF</code> type of token was already generated (this
 * token is always generated as the last token in process of tokenisation) and
 * method <code>nextToken()</code> is called.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * This is the only instance variable of this class. It is a universal
	 * version identifier for a serializable class. Deserialization uses this
	 * number to ensure that a loaded class corresponds exactly to a serialized
	 * object. If no match is found, then an InvalidClassException is thrown.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a default constructor which creates one instance of this type of
	 * exception.
	 */
	public LexerException() {
		super();
	}

	/**
	 * This constructor creates one instance of this type of exception with
	 * appropriate message describing why it occured.
	 * 
	 * @param message
	 *            String which more precisely describes this exception.
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * This constructor creates one instance of this type of exception with
	 * appropriate message describing why it occured. Also, there is one another
	 * parameter cause.
	 * 
	 * @param message
	 *            String which more precisely describes this exception.
	 * @param cause
	 *            Reference to Throwable type object.
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * This constructor creates one instance of this type of exception. It
	 * accepts one Throwable type parameter.
	 * 
	 * @param cause
	 *            Reference to Throwable type object.
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

}
