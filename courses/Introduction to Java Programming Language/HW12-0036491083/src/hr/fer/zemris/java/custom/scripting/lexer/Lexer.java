package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This lexer is used for production of tokens.
 * It creates tokens based on text he got through its constructor.
 * It has two modes of work. First one, TEXT mode, is used outside of tags.
 * It creates only one type of token, text type token.
 * The other mode is called tag mode. It is used inside of tags.
 * There are various tokens that can be produced in this mode, {@link TokenType}.
 * Created tokens can then be returned through getter method.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Lexer {

	/**
	 * Reference to {@link ObjectStack} type object.
	 * It is used for production of tokens inside of tags.
	 * All elements of tag are at the beggining added to stack
	 * and then, one by one, poped out of stack.
	 */
	ObjectStack tagStack = new ObjectStack();

	/**
	 * This instance variable represents text/String that is to be tokenised in a process
	 * of tokenisation.
	 */
	StringBuilder text;

	/**
	 * This instance variable represents a state in which is this lexer at the moment.
	 */
	LexerState state;

	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;

	/**
	 * Instance variable representing token that was last created.
	 */
	Token token;

	/**
	 * Constructor which initializes text instance variable and state (it is initialized to TEXT mode
	 * of work). currentIndex instance variable is initialized to 0.
	 * It throws a {@link IllegalArgumentException} if parameter is null reference. 
	 * @param text Reference to String which is to be tokenised.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Null reference was provided.");
		}
		this.text = new StringBuilder(text);
		setState(LexerState.TEXT);
		currentIndex = 0;
	}

	/**
	 * This method is used for setting mode of work
	 * of this lexer. IllegalArgumentException is thrown if 
	 * parameter is null reference.
	 * @param state State to which state instance variable is setted.
	 */
	private void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State can not be null type.");
		}
		this.state = state;
	}

	/**
	 * Returns last produced token.
	 * @return token instance variable.
	 */
	private Token getToken() {
		return token;
	}

	/**
	 * This method start a production of next token.
	 * @return Returns newly created token.
	 */
	public Token nextToken() {
		while(true){
		// Throwing LexerException if we tokenised whole input string.
		// != null expression because at the beginning token instance variable
		// is set implicitly to null so this would produce NullPointerException
		if (getToken() != null && getToken().getType() == TokenType.EOF) {
			throw new SmartScriptParserException();
		}

		// Last token is always EOF type.
		if (currentIndex >= text.length() && tagStack.isEmpty()) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state == LexerState.TEXT) {
			Token help = nextTokenText();
			if(!help.getValue().equals("")){
			return help;
			}
			continue;
			
		}
		
		//Future tokens are pushed onto stack.
		if (tagStack.isEmpty()) {
			nextTagTokens();
		}
		
		//If nothing was pushed. This means there is nothing in tag(content of it is empty).
		if(tagStack.isEmpty()){
			throw new SmartScriptParserException("Wrong input syntax, inside of tag must be something writen, at least,"
					+ "valid name of the tag.");
		}
		
		/**
		 * Elements are poped from a stack and then sent to tokenEditor method for a production of appropriate 
		 * token.
		 */
		token = tokenEditor((String) tagStack.pop());
		if (tagStack.isEmpty()) {
			setState(LexerState.TEXT);
		}
		return token;
		}
	}

	/**
	 * This method is used for production of TEXT type of tokens.
	 * @return text type of token.
	 */
	private Token nextTokenText() {
		int endIndex = currentIndex;
		try {
			//We are searching for a next tag beggining.
			if (!(text.charAt(currentIndex) == '{' && text.charAt(currentIndex + 1) == '$')) {
				while (!(text.charAt(endIndex) == '{' && text.charAt(endIndex + 1) == '$')) {
					endIndex++;
				}
			}
			//We came to the end of a string that is being tokenised.
		} catch (IndexOutOfBoundsException ex) {
			token = new Token(TokenType.TEXT, text.substring(currentIndex));
			currentIndex = text.length();
			return token;
		}
		//As we came to the beginning of a tag we are changing lexer's mode of work.
		setState(LexerState.TAG);
		if(currentIndex == endIndex){
			token = new Token(TokenType.TEXT, "");
			return token;
		}
		int helpIndex = currentIndex;
		currentIndex = endIndex;
		if (text.charAt(endIndex - 1) == '\\') {
			token = new Token(TokenType.TEXT, text.substring(helpIndex, endIndex - 1));
			return token;
		}
		token = new Token(TokenType.TEXT, text.substring(helpIndex, endIndex));
		return token;
	}

	/**
	 * Pushing Tokens from inside of tag onto stack.
	 * In this method elements of a tag are being pushed onto the same tag.
	 */
	private void nextTagTokens() {
		if (getToken().getType() == TokenType.TEXT) {
			currentIndex = text.indexOf("$", currentIndex) + 1;
		}
		int indexOfClosing = -1;
		try{
			indexOfClosing = text.indexOf("$}", currentIndex);
			String insideOfTag = text.substring(currentIndex, indexOfClosing);
			insideOfTag = insideOfTag.trim();
			String[] partsOfTag = splitMy(insideOfTag);
			for (int i = partsOfTag.length - 1; i >= 0; i--) {
				partsOfTag[i] = partsOfTag[i].trim();
				if (!(partsOfTag[i].isEmpty())) {
					tagStack.push(partsOfTag[i]);
				}
			}
			currentIndex = indexOfClosing + 2;
		} catch (IndexOutOfBoundsException ex){
			throw new SmartScriptParserException("Wrong syntax.");
		}
	}

	/**
	 * Splits text inside of tag.
	 * @param string String to be split.
	 * @return Array of Strings representing parts of string.
	 */
	private String[] splitMy(String string){
		List<String> list = new ArrayList<>();
		boolean insideOfString = false;
		int indexBegin = 0;
		boolean escaping = true;
		for(int i = 0; i < string.length(); i++){
			
			//Checking for escaping sequences
			if(insideOfString && escaping){
				int end = string.indexOf('\"', i);
				String substring = string.substring(i, end);
				
				String substringChanged = substring.replace("\\n", "\n");
				substringChanged = substringChanged.replace("\\r", "\r");
				substringChanged = substringChanged.replace("\\t", "\t");
				substringChanged = substringChanged.replace("\\\"", "\"");
				substringChanged = substringChanged.replace("\\\\", "\\");
				
				string = string.replace(substring, substringChanged);

				escaping = false;
			}
			
			if(!insideOfString && string.charAt(i) == '\"'){
				insideOfString = true;
				indexBegin = i;
				escaping = true;
				
			} else if (insideOfString && string.charAt(i) == '\"'){
				insideOfString = false;
				list.add(string.substring(indexBegin, i + 1));
				indexBegin = i + 1;
				
			} else if(!insideOfString && (string.charAt(i) == ' ' 
					|| string.charAt(i) == '\n' || string.charAt(i) == '\r')){
				while((string.charAt(i) == ' ' 
						|| string.charAt(i) == '\n' 
						|| string.charAt(i) == '\r') && i < string.length()){
					i++;
				}
				
				if(!string.substring(indexBegin, i).trim().equals("")){
					list.add(string.substring(indexBegin, i).trim());
				}

				indexBegin = i;
				i--;
			} 
		}
		
		list.add(string.substring(indexBegin, string.length()));
		String[] array = new String[list.size()];
		
		int i = 0;
		
		for(Object part : list.toArray()){
			array[i] = (String)part;
			i++;
		}
		return array;
	}
	
	
	/**
	 * This method is used for a production of tokens from elements inside of 
	 * a tag. 
	 * @param value String value of a future tag.
	 * @return Newly created token.
	 */
	private Token tokenEditor(String value) {
		if (value.toUpperCase().equals("FOR")) {
			return new Token(TokenType.FOR, "FOR");
		}
		if (value.toUpperCase().startsWith("FOR")){
			String substring = value.substring(3);
			tagStack.push(substring);
			return new Token(TokenType.FOR, "FOR");
		}
		
		if (value.toUpperCase().equals("END")) {
			return new Token(TokenType.END, "END");
		}

		if (isTagName(value)) {
			return new Token(TokenType.ECHO, value);
		}

		if (value.charAt(0) == '=') {
			String substring = value.substring(1);
			tagStack.push(substring);
			return new Token(TokenType.ECHO, "=");
		}

		if (isVariable(value)) {
			return new Token(TokenType.VARIABLE, value);
		}

		if (isFunction(value)) {
			return new Token(TokenType.FUNCTION, value);
		}

		if (isOperator(value)) {
			return new Token(TokenType.OPERATOR, value);
		}

		if (isNumberInteger(value)) {
			return new Token(TokenType.NUMBER_INTEGER, value);
		}

		if (isNumberDouble(value)) {
			return new Token(TokenType.NUMBER_DOUBLE, value);
		}

		if (isString(value)) {
			return new Token(TokenType.STRING, value);
		}
		throw new SmartScriptParserException(
				"Tag is incorectly constructed or there is a syntax mistake inside of it.");

	}

	/**
	 * This method is used for checking if a particular String is actually a string. 
	 * @param value String that is being checked for actually being a String.
	 * @return True if a String is actually a String, and false otherwise.
	 */
	private boolean isString(String value) {
		if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
			return true;
		}
		return false;
	}

	/**
	 * This method is used for checking if particular String represents/is a real number.
	 * @param value String that is being checked for being a real number.
	 * @return Returns true if parameter is actually a real number. False is being returned otherwise.
	 */
	private boolean isNumberDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException | NullPointerException ex) {
			return false;
		}
		return true;
	}


	/**
	 * This method is used for checking if particular String represents/is an integer.
	 * @param value String that is being checked for being an integer.
	 * @return Returns true if parameter is actually an integer. False is being returned otherwise.
	 */
	private boolean isNumberInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException | NullPointerException ex) {
			return false;
		}
		return true;
	}

	/**
	 * This method checks if parameter is being a variable.
	 * @param value String that is being checked for being variable.
	 * @return True if string actually is a variable, false otherwise.
	 */
	private boolean isVariable(String value) {
		if (!Character.isLetter(value.charAt(0))) {
			return false;
		}
		for (int i = 1; i < value.length(); i++) {
			if (!(Character.isLetter(value.charAt(i)) || Character.isDigit(value.charAt(i))
					|| value.charAt(i) == '_')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks if parameter represents a function.
	 * @param value parameter of type String that is being checked for being a function.
	 * @return True if checked parameter is a representation of a funcrtion, false otherwise.
	 */
	private boolean isFunction(String value) {
		if (!(value.charAt(0) == '@')) {
			return false;
		}
		try {
			if (!Character.isLetter(value.charAt(1))) {
				return false;
			}
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
		for (int i = 2; i < value.length(); i++) {
			if (!(Character.isLetter(value.charAt(i)) || Character.isDigit(value.charAt(i))
					|| value.charAt(i) == '_')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method which checks if a given parameter represents an operator.
	 * @param value String type parameter which is being checked for being an operator.
	 * @return True if value is representing an operator, false otherwise.
	 */
	private boolean isOperator(String value) {
		if (value.length() != 1) {
			return false;
		}
		switch (value) {
		case "+":
			return true;
		case "-":
			return true;
		case "*":
			return true;
		case "/":
			return true;
		case "^":
			return true;
		default:
			return false;
		}
	}

	/**
	 * This method checks if parameter being provided represents a tag name.
	 * @param value String value parameter which is being checked for being a tag name.
	 * @return True if value is a tag name, false otherwise.
	 */
	private boolean isTagName(String value) {
		if (!(getToken().getType() == TokenType.TEXT)) {
			return false;
		}
		if (isVariable(value)) {
			return true;
		}
		if (value.equals("=")) {
			return true;
		}
		return false;
	}

}
