package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class/program provides an example of using and manipulation
 * with @ObjectStack object. This program gets one string argument from a
 * command line. It recognizes the expression contained in this string and with
 * use of a stack executes it.
 * 
 * @author LeonardoKokot
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Method from which program starts its execution.
	 * 
	 * @param args
	 *            Arguments from a command line,, only one of them is used.
	 */
	public static void main(String[] args) {

		ObjectStack stack = new ObjectStack();

		String inputString = args[0].trim();

		String checker = null;

		while (true) {
			if (inputString.indexOf(' ') != -1) {
				checker = inputString.substring(0, inputString.indexOf(' '));
				if (isNumber(checker)) {
					inputString = inputString.substring(inputString.indexOf(' '), inputString.length());
					stack.push(Integer.parseInt(checker));
				} else {
					try {
						int numberTwo = (int) stack.pop();
						int numberOne = (int) stack.pop();
						int result = operation(numberOne, numberTwo, checker);
						stack.push(result);
						inputString = inputString.substring(inputString.indexOf(' '), inputString.length());
					} catch (EmptyStackException ex) {
						System.err.println("Error. No enough arguments on a stack.");
						System.exit(1);
					}
				}
				inputString = inputString.trim();
			} else {
				try {
					if (isNumber(inputString)) {
						System.err.println("On the last position there must not be a number.");
						System.exit(1);
					}
					int numberTwo = (int) stack.pop();
					int numberOne = (int) stack.pop();
					int result = operation(numberOne, numberTwo, inputString);
					stack.push(result);
					break;
				} catch (EmptyStackException ex) {
					System.err.println("Error. No enough arguments on a stack.");
					System.exit(1);
				}
			}
		}

		if (stack.size() != 1) {
			System.err.println("Error. Expression is invalid");
			System.exit(1);
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
	}

	/**
	 * Checks if a given string is Integer.
	 * 
	 * @param string
	 *            String that is checked for being an integer.
	 * @return True if string is integer, otherwise returns false.
	 */
	private static boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * This method performs arithmetic operation based on content of a given
	 * string. Operands of this arithmetic expression are, as the result of
	 * operation is, an integers.
	 * 
	 * @param numberOne
	 *            First operand of arithmetic operation.
	 * @param numberTwo
	 *            Second operand of arithmetic operation.
	 * @param expression
	 *            String representation of operator.
	 * @return The result of operation applied to numberOne and numberTwo.
	 * @throws IllegalArgumentException
	 *             if dividing by zero occurs or if operator is not legitimate
	 *             for integers, or it is not existing operator.
	 */
	private static int operation(int numberOne, int numberTwo, String expression) {
		if (expression.equals("+")) {
			return numberOne + numberTwo;
		} else if (expression.equals("-")) {
			return numberOne - numberTwo;
		} else if (expression.equals("/")) {
			if (numberTwo == 0) {
				throw new IllegalArgumentException("Dividing by zero is not legal.");
			} else {
				return numberOne / numberTwo;
			}
		} else if (expression.equals("*")) {
			return numberOne * numberTwo;
		} else if (expression.equals("%")) {
			return numberOne % numberTwo;
		} else {
			throw new IllegalArgumentException("Operacija " + expression + " nije legalna.");
		}
	}

}
