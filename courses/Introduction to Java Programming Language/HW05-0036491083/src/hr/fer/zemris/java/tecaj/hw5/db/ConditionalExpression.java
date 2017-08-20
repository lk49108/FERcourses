package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This class is used for 
 * storing conditions based on which 
 * data stored in list is filtered in a way
 * only student records which obey conditions 
 * are printed out.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ConditionalExpression {

	/**
	 * This instance variable holds holds information which
	 * type of condition is this, i.e. does we search for specific record based on JMBAG, 
	 * last name or first name.
	 */
	private IFieldValueGetter valueGetter;
	
	/**
	 * This instance variable is used for storing string literal based on which we look
	 * for some specific student records.
	 */
	private String stringLiteral;
	
	/**
	 * This instance variable contains operator we use in our search dor specific student records. It can be
	 * <, >, <=, >=, != (not equal), LIKE.
	 */
	private IComparisonOperator operator;
	
	/**
	 * This is a constructor which is used for creation of instance of this class, i.e.
	 * it creates specific condition which student records are subjected in their selection.
	 * All parameters are just joined to its corresponding instance variable.
	 * @param valueGetter IFieldValueGetter type reference. 
	 * @param stringLiteral String type reference.
	 * @param operator IComparisonOperator type reference.
	 */
	public ConditionalExpression(IFieldValueGetter valueGetter, String stringLiteral, IComparisonOperator operator){
		if(valueGetter == null || stringLiteral == null || operator== null){
			throw new IllegalArgumentException();
		}
		this.valueGetter = valueGetter;
		this.stringLiteral = stringLiteral;
		this.operator = operator;
	}

	/**
	 * This is a getter for valueGetter instance variable.
	 * @return the valueGetter instance variable.
	 */
	public IFieldValueGetter getFieldGetter() {
		return valueGetter;
	}

	/**
	 * This is a getter for getStringLiteral instance variable.
	 * @return the stringLiteral instance variable.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * This is a getter for operator instance variable.
	 * @return the operator instance variable.
	 */
	public IComparisonOperator getComparisionOperator() {
		return operator;
	}
	
}