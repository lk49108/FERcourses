package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This is a class that implements tokens. Token is a lexical unit that groups
 * one or more consecutive characters from input text/string.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Token {

	/**
	 * TokenType Instance variable which represents what kind of a token this
	 * one is. It can be TEXT, EOF, VARIABLE, CONSTANTINTEGER, CONSTANTDOUBLE, STRING, FUNCTION or a OPERATOR.
	 */
	private TokenType typeOfToken;

	/**
	 * Object type instance variable. It represents value of/contained (in) this
	 * token.
	 */
	private Object tokenValue;

	/**
	 * This constructor creates an instance of Token.
	 * 
	 * @param type
	 *            Reference to TokenType object which will describe what type of
	 *            token this one is.
	 * @param value
	 *            Value contained in this token.
	 */
	public Token(TokenType type, Object value) {
		typeOfToken = type;
		tokenValue = value;
	}

	/**
	 * Getter for tokenValue instance variable.
	 * 
	 * @return tokenValue instance variable.
	 */
	public Object getValue() {
		return tokenValue;
	}

	/**
	 * Getter for typeOfToken instance variable.
	 * 
	 * @return typeOfToken instance variable.
	 */
	public TokenType getType() {
		return typeOfToken;
	}
}
