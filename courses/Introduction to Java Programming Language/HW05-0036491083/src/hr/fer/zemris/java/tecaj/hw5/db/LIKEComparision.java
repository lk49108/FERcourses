package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This class implements IComparisionOperator interface.
 * It is used for checking if given String similar to other String.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LIKEComparision implements IComparisonOperator{

	@Override
	public boolean satisfied(String value2, String value1){
		if(value2.contains(" ")){
			return satisfied(value2.substring(0, value2.indexOf(" ")), value1) || satisfied(value2.substring(value2.indexOf(" ") + 1), value1);
		}
		if(value1.indexOf('*') == value1.length() - 1){
			return value2.startsWith(value1.substring(0, value1.indexOf('*')));
		}
		if(value1.indexOf('*') == 0){
			return value2.endsWith(value1.substring(value1.indexOf('*') + 1));
		}
		return value2.startsWith(value1.substring(0, value1.indexOf('*'))) && value2.endsWith(value1.substring(value1.indexOf('*') + 1));
	}
}
