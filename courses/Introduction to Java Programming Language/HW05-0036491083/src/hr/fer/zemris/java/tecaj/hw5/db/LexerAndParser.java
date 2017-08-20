package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for parsing given command.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LexerAndParser {

	/**
	 * Instance variable which is a reference to String containing command currently being parsed.
	 */
	private String command;
	
	/**
	 * This is an instance variable (list) which contains commands recognized to be part of a command.
	 */
	private List<ConditionalExpression> conditions;
	
	/**
	 * This constructor initializes command instance variable.
	 * @param command String type reference representing command which is to be parsed.
	 */
	public LexerAndParser(String command){
		this.command = command;
	}
	
	/**
	 * With a call of this method parsing starts its execution.
	 */
	public void parse(){
		conditions = new ArrayList<>();
		while(true){
			try{
				if(findNext() == -1){
					return;
				}
			} catch(IndexOutOfBoundsException ex) {
				return;
			}
		}
	}
	
	/**
	 * This method is used for obtaining conditions that were processed out of the command.
	 * @return list of conditions.
	 */
	public List<ConditionalExpression> getConditions(){
		return conditions;
	}
	
	/**
	 * This method in a process of parsing.
	 * @return 0 if process is completed successfully and command  is still not processed to its end.
	 */
	private int findNext(){
		int index = findFirstAttribute();
		if(index == -1){
			return -1;
		}
		int indexOfNextQuoteEnd = nextQuote();
		createCondition(command.substring(index, indexOfNextQuoteEnd + 1).trim());
		try{
			command = command.substring(command.toUpperCase().indexOf("AND") + 3);
		} catch (IndexOutOfBoundsException ex){
			throw new IndexOutOfBoundsException("Whole command is parsed.");
		}
		return 0;
	}
	
	/**
	 * This method is used for finding next index (from the one we are currently located in a process of parsing) of quote sign '\"' in a processed command.
	 * @return Index of first quote sign found.
	 */
	private int nextQuote(){
		return command.indexOf('\"', command.indexOf('\"') + 1);
	}
	
	/**
	 * This method is used for
	 * a creation of condition.
	 * @param string String based on which corresponding condition is created.
	 */
	private void createCondition(String string){
		String stringValue = string.substring(string.indexOf('\"') + 1, string.length() - 1);
		if(stringValue.indexOf('*') != stringValue.lastIndexOf('*')){
			throw new IllegalArgumentException("It is illegal to have more than one '*' character in a string literal.");
		}
		IComparisonOperator operator;
		if(string.indexOf(">=") != -1){
			operator = new GreaterOrEqualComparision();
		}
		else if(string.indexOf("<=") != -1){
			operator = new LessOrEqualComparision();
		}
		else if(string.indexOf("!=") != -1){
			operator = new NotEqualComparision();
		}
		else if(string.indexOf("LIKE") != -1){
			operator = new LIKEComparision();
		}
		else if(string.indexOf(">") != -1){
			operator = new GreaterComparision();
		}
		else if(string.indexOf("<") != -1){
			operator = new LessComparision();
		}
		else if(string.indexOf("=") != -1){
			operator = new EqualComparision();
		}
		else{
			throw new IllegalArgumentException("Operator is missing in command.");
		}
		if(string.toUpperCase().startsWith("FIRSTNAME")){
			conditions.add(new ConditionalExpression(new FirstNameFieldGetter(), stringValue, operator));
			return;
		}
		else if(string.toUpperCase().startsWith("LASTNAME")){
			conditions.add(new ConditionalExpression(new LastNameFieldGetter(), stringValue, operator));
			return;
		}
		conditions.add(new ConditionalExpression(new JMBAGFieldGetter(), stringValue, operator));
		return;
	}
	
	/**
	 * This method is used for finding words like LASTNAME, FIRSTNAME and JMBAG in
	 * given command.
	 * @return Index of first word (one from a list above) found in a command.
	 */
	private int findFirstAttribute(){
		String help = command.toUpperCase();
		int index1 = help.indexOf("LASTNAME");
		int index2 = help.indexOf("FIRSTNAME");
		int index3 = help.indexOf("JMBAG");
		return min(index1, index2, index3);
	}
	
	/**
	 * This method is used for finding out which of the Integers provided is the smallest
	 * and different than -1. If all of them are equal to -1, -1 is returned.
	 * @param a First Integer.
	 * @param b Second Integer.
	 * @param c Third Integer.
	 * @return The smallest Integer from given three or -1 if all of them are equal to -1.
	 */
	private int min(int a, int b, int c){
		if(b < a){
			if(c < b){
				if(c != -1){
					return c;
				}
			}
			if(b != -1){
				return b;
			}
		}
		if(a != -1){
			return a;
		}
		if(b != -1){
			return b;
		}
		if(c != -1){
			return c;
		}
		return -1;
	}
}
