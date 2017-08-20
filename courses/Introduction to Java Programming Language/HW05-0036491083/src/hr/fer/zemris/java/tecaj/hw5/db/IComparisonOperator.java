package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This interface contains method satisfied.
 * This method is used for verifying if specific student's
 * record (its first name, last name or JMBAG)
 * satisfies specific condition.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface IComparisonOperator {
	
	/**
	 * This method checks if given string value1
	 * obeys specific condition based on class from which this method is called(operator)
	 * and given String value2. 
	 * @param value1 String reference.
	 * @param value2 String reference.
	 * @return True if value1 satisfies given conditions. False is returned otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
