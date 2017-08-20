package hr.fer.zemris.java.tecaj.hw9.demoComplex;

import hr.fer.zemris.java.tecaj.hw9.complex.Complex;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexRootedPolynomial;

/**
 * Class used for testing Complex classes functionalities.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class DemoComplex1 {

	/**
	 * Program starts its execution from this method.
	 * @param args Commandline arguments, not used here.
	 */
	public static void main(String[] args) {
		Complex z1 = Complex.ONE;
		Complex z2 = Complex.ONE_NEG;
		Complex z3 = Complex.IM;
		Complex z4 = Complex.IM_NEG;
		ComplexRootedPolynomial polyRoot = new ComplexRootedPolynomial(z1, z2, z3, z4);
		System.out.println(polyRoot.indexOfClosestRootFor(new Complex(0.999,  0), 0.002));
	}

}
