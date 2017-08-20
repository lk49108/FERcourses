package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This enumeration contains type of
 * modes in which {@link Lexer} can be situated.
 * Two modes are possible.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public enum LexerState {

	/**
	 * This is the first mode in which {@link Lexer} can be situated.
	 * It is used outside of tags.
	 */
	TEXT,

	/**
	 * This is the second mode in which {@link Lexer} can be situated.
	 * It is used when {@link Lexer}r decodes data inside of tags.
	 */
	TAG;
}
