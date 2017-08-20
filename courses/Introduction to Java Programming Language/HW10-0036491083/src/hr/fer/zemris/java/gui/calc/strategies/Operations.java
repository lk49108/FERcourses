/**
 * 
 */
package hr.fer.zemris.java.gui.calc.strategies;

/**
 * This interface defines one method which all
 * advanced functions implement. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface Operations {

	/**
	 * Function which gets one double value and returns
	 * result of some operation processed on this value.
	 * @param num Value provided.
	 * @return Result of operation on provided num.
	 */
	double calcFunction(double num);
}
