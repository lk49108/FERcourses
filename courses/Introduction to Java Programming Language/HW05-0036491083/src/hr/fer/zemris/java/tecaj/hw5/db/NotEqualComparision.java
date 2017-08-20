package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This class implements IComparisionOperator interface.
 * It is used for checking if given String is different from the other String.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class NotEqualComparision implements IComparisonOperator{

	@Override
	public boolean satisfied(String value1, String value2){
		if(value1.indexOf(' ') != -1){
			return !(satisfied(value1.substring(0, value1.indexOf(' ')), value2) || satisfied(value1.substring(value1.indexOf(' ') + 1).trim(),value2));
		}
		if(value1.equals(value2)){
			return false;
		}
		return true;
	}
}
