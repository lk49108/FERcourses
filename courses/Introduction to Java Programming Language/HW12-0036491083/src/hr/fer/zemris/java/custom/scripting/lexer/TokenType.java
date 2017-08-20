package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public enum TokenType {

	/**
	 * One of possible values of this enumeration.
	 * It represents end of document.
	 */
	EOF,

	/**
	 * One of possible values of this enumeration.
	 * It represents text.
	 */
	TEXT,

	/**
	 * One of possible values of this enumeration.
	 * It represents variable.
	 */
	VARIABLE,

	/**
	 * One of possible values of this enumeration.
	 * It represents an integer.
	 */
	NUMBER_INTEGER,

	/**
	 * One of possible values of this enumeration.
	 * It represents a real number.
	 */
	NUMBER_DOUBLE,

	/**
	 * One of possible values of this enumeration.
	 * It represents a String.
	 */
	STRING,

	/**
	 * One of possible values of this enumeration.
	 * It represents function.
	 */
	FUNCTION,

	/**
	 * One of possible values of this enumeration.
	 * It represent an operator.
	 */
	OPERATOR,

	/**
	 * One of possible values of this enumeration.
	 * It represent the word "FOR" at the beggining of For-loop construct.
	 */
	FOR,

	/**
	 * One of possible values of this enumeration.
	 * It represent beginning of a tag(its name).
	 */
	ECHO,

	/**
	 * One of possible values of this enumeration.
	 * It end tag.
	 */
	END;

}
