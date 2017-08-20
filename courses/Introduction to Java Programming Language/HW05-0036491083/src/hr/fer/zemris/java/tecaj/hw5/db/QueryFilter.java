package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;

/**
 * This class implements IFilter interface.
 * It is used for checking if given student's record satisfies specific conditions.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class QueryFilter implements IFilter{

	/**
	 * List of conditions to which given student's record is subjected to.
	 */
	List<ConditionalExpression> conditions;
	
	@Override
	public boolean accepts(StudentRecord record) {
		for(int i = 0; i <conditions.size(); i++){
			ConditionalExpression currentCondition = conditions.get(i);
			if(currentCondition.getComparisionOperator().satisfied(currentCondition.getFieldGetter().get(record), currentCondition.getStringLiteral()) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method is used for parsing(through LexerAndParser class methods command given as a String 
	 * through parameter queryString.
	 * @param queryString Command which is being parsed.
	 */
	public QueryFilter(String queryString){
		LexerAndParser lex = new LexerAndParser(queryString);
		try{
			lex.parse();
		} catch (IllegalArgumentException ex){
			throw new IllegalArgumentException("Wrong input syntax.");
		}
		conditions = lex.getConditions();
	}
	
}
