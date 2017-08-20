package hr.fer.zemris.java.tecaj.hw9.complex;


/**
 * This class is used for working with 
 * root-based complex polynomials.
 * Polynomials are immutable, and hence methods which would transform polynomial
 * in any way are implemented to return new instance of {@link ComplexRootedPolynomial}.
 * It holds its roots, i.e. complex numbers which,
 * when put into polynomial give a result equal to zero,
 * in private and final array of complex number (each is one root).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	
	/**
	 * This instance variable holds array of
	 * {@link Complex} numbers which are roots 
	 * of a  represented by instance of this class.
	 */
	private final Complex[] roots;

	/**
	 * This constructor accepts variable number of 
	 * arguments. Arguments are {@link Complex} numbers
	 * which are roots of polynomial being created.
	 * If none of {@link Complex} arguments are provided, or
	 * nnull reference is provided,
	 * special kind of {@link ComplexRootedPolynomial} is created,
	 * i.e. one with constant value 0 is created. {@link #roots}
	 * are set to null, to represent such a polynomial.
	 * @param roots {@link Complex} type parameters are put into private(and final)
	 * array of roots, {@link #roots}.
	 */
	public ComplexRootedPolynomial(Complex... roots){
		if(roots == null || roots.length == 0) {
			this.roots = null;
		} else {
			this.roots = new Complex[roots.length];
			for(int i = 0; i < roots.length; i++){
				this.roots[i] = roots[i];
			}
		}
	}
	
	/**
	 * Computes polynomial value at given point.
	 * Point is given by parameter in a form of a complex number.
	 * @param z {@link Complex} type parameter which determines 
	 * in which point(complex point) will be calculated 
	 * polynomial value.
	 * @return {@link Complex} number which is a representation
	 * of a complex value of polynomial in a point specified by parameter z.
	 */
	public Complex apply(Complex z){
		if(this.roots == null){
			return Complex.ZERO;
		}
		return calculateValue(z);
	}

	/**
	 * This method calculates polynomial value at given point.
	 * It is used as a helper method for {@link #apply(Complex)} method.
	 * For more information see {@link #apply(Complex)}.
	 * @param z Parameter which determines in which point polynomial value will be calculated.
	 * @return New instance of a {@link Complex} as a result of computation described above.
	 */
	private Complex calculateValue(Complex z) {
		Complex result = Complex.ONE;
		for(Complex root : this.roots){
			result =  result.multiply(z.sub(root)); 
		}
		return result;
	}
	
	/**
	 * Converts this {@link ComplexRootedPolynomial} representation of
	 * polynomial to {@link ComplexPolynomial} representation of
	 * complex polynomial.
	 * @return New instance of {@link ComplexPolynomial}.
	 */
	public ComplexPolynomial toComplexPolynom(){
		if(this.roots == null){
			return new ComplexPolynomial(null);
		}
		//Beginning value
		ComplexPolynomial coeffPolynomial = new ComplexPolynomial(
				Complex.ONE_NEG.multiply(roots[0]), Complex.ONE);
		for(int i = 1; i < this.roots.length; i++){
			coeffPolynomial = coeffPolynomial.multiply(
					new ComplexPolynomial(Complex.ONE_NEG.multiply(roots[i]), Complex.ONE));
		}
		return coeffPolynomial;
	}
	
	@Override
	public String toString() {
		if(this.roots == null){
			return "0+0i";
		}
		StringBuilder builder = new StringBuilder();
		for(Complex root : this.roots){
			builder.append("(Z-(" + root + "))");
		}
		return builder.toString();
	}
	
	/**
	 * This method finds index of the closest root for given complex number
	 * z that is within threshold. If there is no such root, -1 is returned. 
	 * @param z {@link Complex} number for which it is checked to be
	 * "treshold close" to any of roots of this {@link ComplexRootedPolynomial}. 
	 * @param treshold Module of the least allowed distance of provided {@link Complex}
	 * number to any root of this {@link ComplexRootedPolynomial}. 
	 * @return -1 if none of the roots is "treshold close" to given 
	 * {@link Complex} parameter z. Otherwise, index of closest 
	 * root is being returned.
	 * @throws IllegalArgumentException if given {@link Complex} number z is null reference.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold){
		if(z == null){
			throw new IllegalArgumentException("Given complex number cannot be null reference.");
		}
		//Represents minimum found distance of complex number z to any of the roots.
		double minimum = treshold;
		int index = -1;
		int i = 1;
		for(Complex root : roots){
			if(root.sub(z).module() < minimum){
				minimum = root.sub(z).module();
				index = i;
			}
			i++;
		}
		return index;
	}
}
