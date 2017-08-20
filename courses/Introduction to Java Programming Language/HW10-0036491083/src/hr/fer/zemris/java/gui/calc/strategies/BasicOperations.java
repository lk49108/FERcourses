/**
 * 
 */
package hr.fer.zemris.java.gui.calc.strategies;

/**
 * This method implements all basic
 * operations. They implement provided {@link #doOperation(double, double)}
 * method in particular way.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface BasicOperations {

	/**
	 * Method which gets two double values through parameters
	 * and returns result of some operation
	 * processed on those two values.
	 * @param num1 First value.
	 * @param num2 Second value.
	 * @return Reult of operation.
	 */
	double doOperation(double num1, double num2);
}
