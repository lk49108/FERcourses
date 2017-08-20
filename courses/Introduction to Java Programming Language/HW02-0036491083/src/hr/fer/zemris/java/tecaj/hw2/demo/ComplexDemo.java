package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * This is a class that is used for testing {@link ComplexNumber} class.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ComplexDemo {

	/**
	 * This is the only method of a class. Program starts its execution from it.
	 * 
	 * @param args
	 *            Command line arguments. They are not used here.
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}

}
