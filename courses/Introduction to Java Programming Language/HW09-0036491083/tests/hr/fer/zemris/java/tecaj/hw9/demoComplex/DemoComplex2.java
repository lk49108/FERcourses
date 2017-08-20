package hr.fer.zemris.java.tecaj.hw9.demoComplex;

import hr.fer.zemris.java.tecaj.hw9.complex.Complex;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexPolynomial;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexRootedPolynomial;

/**
 * Class used for testing Complex classes functionalities.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DemoComplex2 {

	
	/**
	 * Program starts its execution from this method.
	 * @param args Commandline arguments, not used here.
	 */
	public static void main(String[] args) {
		Complex c = new Complex(2.0, -3);
		Complex c2 = new Complex(1, 1);
		ComplexRootedPolynomial polinomKorijen = new ComplexRootedPolynomial(c, c2);
		System.out.println(c);
		System.out.println(c2);
		System.out.println(polinomKorijen);
		System.out.println(polinomKorijen.toComplexPolynom());
		ComplexPolynomial poly = new ComplexPolynomial(null);
		System.out.println(poly);
		System.out.println(poly.derive());
		ComplexRootedPolynomial polyRoot = new ComplexRootedPolynomial(null);
		System.out.println(polyRoot);
		System.out.println(polyRoot.toComplexPolynom());
	}
	
	
}
