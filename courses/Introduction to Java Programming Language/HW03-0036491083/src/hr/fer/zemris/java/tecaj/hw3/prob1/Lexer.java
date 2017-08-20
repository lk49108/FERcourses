package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * This is a class which purpose is to divide input String into series of
 * tokens(each is represented as an instance of Token class). There are two
 * possible working modes of this class (BASIC and EXTENDED). Switching between
 * two states is handled with method <code>setState()</code>. This method is
 * automatically being called(and it switches working mode from one mode to
 * another) when SYMBOL type token with value of '#' character is created by
 * calling method <code>nextToken()</code>. There are four type of tokens: WORD,
 * NUMBER, SYMBOL and EOF. In BASIC mode of work, WORD is considered to be an
 * array of character for which statement <code>character.isLetter()</code>
 * returns <code>true</code> value. Number is an array of one or more digits
 * which can be parsed to a <code>Long</code> type. Symbol is every lone
 * standing character when all NUMBERS and WORDS are removed from string.
 * Whitespaces do not generate any tokens. They are being ignored. EOF type
 * token is the token being generated as the last one. When he is generated,
 * that means that we have tokenized whole string. If next token is requested
 * after EOF token has been made, {@link LexerException} will be thrown. Also,
 * if '\\' character is situated prior to digit or another '\\' character, this
 * subsequent digit or '\\' character are considered being a letter. This method
 * is called escaping. In EXTENDED mode of work every array consecutive
 * characters that are not whitespaces is considered to be a WORD type of token.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */

public class Lexer {

	/**
	 * Input text/string which is being tokenised represented as an array of
	 * chars.
	 */
	private char[] data;

	/**
	 * Last token that method <code>nextToken</code> returned.
	 */
	private Token token;

	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;

	/**
	 * Instance variable representing in which mode of work does this class
	 * work. It can be BASIC or EXTENDED type of work.
	 */
	private LexerState state;

	/**
	 * Constructor which accepts text/string. Assigns characters of text to
	 * instance variable <code>data</code> which is reference to an array of
	 * chars. <code>state</code> instance variable is being set to BASIC.
	 * 
	 * @param text
	 *            Text/string which characters are being assigned to
	 *            <code>data</code> instance variable.
	 * @throws IllegalArgumentException
	 *             when null reference is provided as an argument.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Null reference was provided.");
		}
		text = text.trim();
		data = new char[text.length()];
		for (int i = 0; i < text.length(); i++) {
			data[i] = text.charAt(i);
		}
		currentIndex = 0;
		setState(LexerState.BASIC);
	}

	/**
	 * Method through which work mode of this class is adjusted (it actually
	 * just assigns the value of a parameter to <code>state</code> instance
	 * variable.
	 * 
	 * @param state
	 *            <code>LexerState</code> type reference with a value (BASIC or
	 *            EXTENDED) to which working state of this class is adjusted.
	 * @throws IllegalArgumentException
	 *             if null reference is provided as a parameter of this method.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State can not be null type.");
		}
		this.state = state;
	}

	/**
	 * Method which, when called, generates and returns next token.
	 * 
	 * @return Returns next Token.
	 * @throws LexerException
	 *             if EOF token was already returned, e.g. there are no more
	 *             tokens to be extracted from input string (represented as
	 *             <code>data</code> instance variable).
	 */
	public Token nextToken() {

		// Throwing LexerException if we tokenised whole input string.
		// != null expression because at the beginning token instance variable
		// is set implicitly to null so this would produce NullPointerException
		if (getToken() != null && getToken().typeOfToken == TokenType.EOF) {
			throw new LexerException();
		}

		// Last token is always EOF type.
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state == LexerState.BASIC) {
			return nextTokenBasic();
		}

		return nextTokenExtended();
	}

	/**
	 * Method which returns last token returned from <code>nextToken</code>. Can
	 * be called multiple times. Does not start creation/generation of next
	 * token.
	 * 
	 * @return <code>token</code> instance variable.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * BASIC MODE OF WORK. This method generates next (likely to be) token out
	 * of char array parameter in BASIC mode of work. Decoding of next string
	 * method starts from a specific index that is got through value of
	 * <code>currentIndex</code> instance variable.
	 * 
	 * @param data
	 *            Array of characters from which next (likely to be) token is
	 *            generated.
	 * @param index
	 *            Index inside an array of characters from which this method
	 *            starts generating next string.
	 * @return Newly generated string (likely to be next token).
	 * @throws LexerException
	 *             When input string which is being tokenised is invalid(there
	 *             is a mistake somewhere) at some specific index. '\\'
	 *             character not being followed by another '\\' character or a
	 *             digit is considered as a mistake.
	 * 
	 */
	private String getNextBasic(char[] data, int index) {
		int endIndex = index;

		try {

			if (Character.isWhitespace(data[index])) {
				while (Character.isWhitespace(data[endIndex])) {
					endIndex++;
				}
			}

			else if (Character.isLetter(data[index]) || (data[index] == '\\')) {
				while (true) {
					int checker = endIndex;
					if (data[endIndex] == '\\'
							&& !(data[endIndex + 1] == '\\' || Character.isDigit(data[endIndex + 1]))) {
						throw new LexerException("\\ must be followed with another \\ or by digit.");
					} else if (Character.isLetter(data[endIndex])) {
						endIndex++;
					} else if (data[endIndex] == '\\'
							&& (data[endIndex + 1] == '\\' || Character.isDigit(data[endIndex + 1]))) {
						endIndex += 2;
					}
					// If none of above expressions were found true. That means
					// that we have come
					// to the end of the next token.
					if (endIndex == checker) {
						break;
					}
				}
			}

			else if (Character.isDigit(data[index])) {
				while (Character.isDigit(data[endIndex])) {
					endIndex++;
				}
			}

			// switching to new regime of work.
			else if (data[index] == '#') {
				setState(LexerState.EXTENDED);
				endIndex++;
			}

			// If none of above if, else if sentences were executed, that means
			// that
			// next token must be SYYMBOL type, so we are just increasing
			// endIndex variable by 1.
			else {
				endIndex++;
			}

		} catch (ArrayIndexOutOfBoundsException ex) {
		}
		return new String(data, index, endIndex - index);
	}

	/**
	 * BASIC MODE OF WORK. This method accepts <code>String</code> type
	 * parameter which can be a representation of any type of a token(that is
	 * not yet fully processed). This method determines which type of token this
	 * input token is and finalizes the creation of subsequent token.
	 * 
	 * @param pattern
	 *            <code>String</code> type parameter. Represents token being
	 *            processed.
	 * @return Newly created token.
	 */
	private Token getNextTokenBasic(String pattern) {
		if (Character.isLetter(pattern.charAt(0)) || pattern.charAt(0) == '\\') {
			// We are deleting all '\\' characters in a string that are there
			// only for escaping reasons.
			pattern = eraser(pattern);
			return new Token(TokenType.WORD, pattern);
		}
		if (Character.isDigit(pattern.charAt(0))) {
			try {
				return new Token(TokenType.NUMBER, Long.parseLong(pattern));
			} catch (NumberFormatException ex) {
				throw new LexerException("Number is to big for long type.");
			}
		}
		return new Token(TokenType.SYMBOL, pattern.charAt(0));
	}

	/**
	 * BASIC MODE OF WORK. This method erases all the '\\' signs which are
	 * actually not representing any sign, they are there just for escaping
	 * reasons.
	 * 
	 * @param pattern
	 *            <code>String</code> type parameter from which all '\\'
	 *            characters that are there just for escaping reasons are being
	 *            deleted.
	 * @return Newly created string without any redundant '\\' characters.
	 */
	private String eraser(String pattern) {
		int i = 0;

		while (i < pattern.length() - 1) {
			if (pattern.charAt(i) == '\\'
					&& (Character.isDigit(pattern.charAt(i + 1)) || pattern.charAt(i + 1) == '\\')) {
				pattern = pattern.substring(0, i) + pattern.substring(i + 1);
			}
			i++;
		}
		return pattern;
	}

	/**
	 * BASIC MODE OF WORK. This method creates next token in text/string. It
	 * also adjusts <code>token</code> instance variable value. This method is
	 * being called from <code>nextToken()</code> method.
	 * 
	 * @return newly created token.
	 * 
	 */
	private Token nextTokenBasic() {
		while (true) {
			String nextSubstring = getNextBasic(data, currentIndex);
			currentIndex += nextSubstring.length();

			// Checking if nextSubstring is contained only of whitespace
			// characters.
			nextSubstring = nextSubstring.trim();
			if (nextSubstring.length() == 0) {
				continue;
			}

			token = getNextTokenBasic(nextSubstring);

			return token;
		}

	}

	/**
	 * EXTENDED MODE OF WORK. This method creates next token in text/string. It
	 * also adjusts <code>token</code> instance variable value. This method is
	 * being called from <code>nextToken()</code> method.
	 * 
	 * @return newly created token.
	 */
	private Token nextTokenExtended() {
		while (true) {
			String nextSubstring = getNextExtended(data, currentIndex);
			currentIndex += nextSubstring.length();

			// Checking if nextSubstring is contained only of whitespace
			// characters.
			nextSubstring = nextSubstring.trim();
			if (nextSubstring.length() == 0) {
				continue;
			}

			token = getNextTokenExtended(nextSubstring);

			return token;
		}
	}

	/**
	 * EXTENDED MODE OF WORK. This method accepts <code>String</code> type
	 * parameter that is actually a token(that is not yet fully processed). This
	 * method determines which type of token this input token is and finalizes
	 * the creation of subsequent token.
	 * 
	 * @param pattern
	 *            <code>String</code> type parameter. Represents token being
	 *            processed.
	 * @return Newly created token.
	 */
	private Token getNextTokenExtended(String pattern) {
		if (pattern.charAt(0) == '#') {
			return new Token(TokenType.SYMBOL, pattern.charAt(0));
		}
		return new Token(TokenType.WORD, pattern);
	}

	/**
	 * EXTENDED MODE OF WORK. This method generates next (likely to be) token
	 * out of char array parameter in EXTENDED mode of work. Decoding of next
	 * string method starts from a specific index that is got through value of
	 * <code>currentIndex</code> instance variable.
	 * 
	 * @param data
	 *            Array of characters from which next (likely to be) token is
	 *            generated.
	 * @param index
	 *            Index inside an array of characters from which this method
	 *            starts generating next string.
	 * @return Newly generated string (likely to be next token).
	 */
	private String getNextExtended(char[] data, int index) {
		int endIndex = index;

		try {

			if (Character.isWhitespace(data[index])) {
				while (Character.isWhitespace(data[endIndex])) {
					endIndex++;
				}
			}

			else if (data[index] == '#') {
				setState(LexerState.BASIC);
				endIndex++;
			} else {
				while (!(Character.isWhitespace(data[endIndex]) || data[endIndex] == '#')) {
					endIndex++;
				}
			}

		} catch (ArrayIndexOutOfBoundsException ex) {
		}
		return new String(data, index, endIndex - index);

	}

}
