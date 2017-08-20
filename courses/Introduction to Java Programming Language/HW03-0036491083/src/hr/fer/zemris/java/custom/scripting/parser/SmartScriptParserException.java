package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents SmartScriptParserException.
 * This exception is thrown when text provided to parser has invalid syntax.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * This is the only instance variable of this class. It is a universal
	 * version identifier for a serializable class. Deserialization uses this
	 * number to ensure that a loaded class corresponds exactly to a serialized
	 * object. If no match is found, then an InvalidClassException is thrown.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is default constructor which creates one instance of
	 * {@link SmartScriptParserException}.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * This constructor creates one instance of
	 * {@link SmartScriptParserException}. It also gets one string type
	 * parameter message.
	 * 
	 * @param message
	 *            Reference to String type object, it can describe more
	 *            specifically why this exception has been thrown.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * This constructor creates one instance of
	 * {@link SmartScriptParserException}. It also gets one throwable type
	 * parameter.
	 * 
	 * @param cause
	 *            Reference to Throwable type object.
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * This constructor creates one instance of
	 * {@link SmartScriptParserException}. It also gets one String type
	 * parameter message, and another Throwable type parameter.
	 * 
	 * @param message
	 *            Reference to String type object, it can describe more
	 *            specifically why this exception has been thrown.
	 * @param cause
	 *            Reference to Throwable type object.
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
