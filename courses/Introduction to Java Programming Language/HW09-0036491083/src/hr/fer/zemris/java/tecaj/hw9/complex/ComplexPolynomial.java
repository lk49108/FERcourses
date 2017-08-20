package hr.fer.zemris.java.tecaj.hw9.complex;


/**
 * This class is used for working with polynomials 
 * defined through their {@link Complex} coefficients.
 * Polynomials are immutable, and hence methods which would transform polynomial
 * in any way are implemented to return new instance of {@link ComplexPolynomial}. 
 * Instance of this class holds private(and final) array of
 * {@link Complex} numbers which coefficients of 
 * specific polynomials ordered by its weight.
 * For instance, if polynomial would look like : 2*z^3 + (-2 + 3i)*z + 1
 * -> array would look like [Complex(1), Complex(-2+3i), Complex(0), 
 * Complex(2)].
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ComplexPolynomial {

	/**
	 * This instance variable holds array of coefficients which
	 * determines belonging polynomial. Coefficients are
	 * ordered by its weight, for more details see {@link ComplexPolynomial}.
	 */
	private final Complex[] coefficients;
	
	/**
	 * This constructor creates instance of this {@link ComplexPolynomial}
	 * in a way that it gets coefficients (sorted by its weight) as parameters
	 * and then it stores them in that same  way into {@link #coefficients}.
	 * If zero coefficient are provided or null reference is provided
	 * constant polynomial with its value equal to 0 in every complex point will be created, 
	 * i.e. coefficients array will be set to size of 1, and this one coefficient will be set to 0.
	 * @param factors {@link Complex} coefficients of polynomial being created.
	 */
	public ComplexPolynomial(Complex... factors){
		if(factors == null || factors.length == 0){
			coefficients = new Complex[1];
			coefficients[0] = Complex.ZERO;
		} else {
			coefficients = new Complex[factors.length];
			for(int i = 0; i < factors.length; i++){
				coefficients[i] = factors[i];
			}
		}
	}
	
	/**
	 * Returns order of this {@link ComplexPolynomial}, e.g. for 
	 * (7+2i)z^3+2z^2+5z+1 returns 3.
	 * @return Short integer which represents order of this {@link ComplexPolynomial}.
	 */
	public short order(){
		return  (short)(coefficients.length - 1);
	}
	
	/**
	 * Computes a new {@link ComplexPolynomial} by multiplying
	 * the one on which this method is being called and the one 
	 * provided as an argument.
	 * @param p {@link ComplexPolynomial} type argument, polynomial 
	 * by which this polynomial is multiplied.
	 * @return New instance of {@link ComplexPolynomial} as a result
	 * of multiplication.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p){
		return calculateProduct(p);
	}
	
	
	
	/**
	 * This method is used by {@link #multiply(ComplexPolynomial)} method
	 * to do its job. For more details see {@link #multiply(ComplexPolynomial)}.
	 * @param p {@link ComplexPolynomial} parameter with witch {@link ComplexPolynomial},
	 * on which method {@link #multiply(ComplexPolynomial)} is called, is multiplied by. 
	 * @return Resulting {@link ComplexPolynomial} as a result of multiplying.
	 */
	private ComplexPolynomial calculateProduct(ComplexPolynomial p) {
		int orderOfFirst = this.coefficients.length - 1;
		int orderOfSecond = p.coefficients.length - 1;
		Complex[] coefficientsResult = new Complex[orderOfFirst + orderOfSecond + 1]; 
		//Filling it with starting values equal to 0 + 0i
		for (int i = 0; i < coefficientsResult.length; i++) {
			coefficientsResult[i] = Complex.ZERO;
		}
		for(int i = 0; i < orderOfFirst + 1; i++){
			for(int j = 0; j < orderOfSecond + 1; j++){
				coefficientsResult[i + j] = 
						coefficientsResult[i + j].add(
								this.coefficients[i].multiply(p.coefficients[j]));
			}
		}
		return new ComplexPolynomial(coefficientsResult);
	}
	
	/**
	 * Derives {@link ComplexPolynomial} on which method is called
	 * and returns resulting {@link ComplexPolynomial} as a new instance
	 * of {@link ComplexPolynomial} class.
	 * If {@link ComplexPolynomial} has only free coefficient, i.e. it looks
	 * something like : 2 - 3i, 5...
	 * New ComplexPolynomial with null reference provided is constructed,
	 * i.e. constant {@link ComplexPolynomial} with value equal to zero in
	 * all of its points is created, for more see {@link #ComplexPolynomial(Complex...)}.
	 * @return New {@link ComplexPolynomial} instance as a result of 
	 * deriving starting polynomial. 
	 */
	public ComplexPolynomial derive(){
		if(this.coefficients.length == 1){
			return new ComplexPolynomial(null);
		}
		Complex[] derivative = new Complex[this.coefficients.length - 1];
		for(int i = 0; i < derivative.length; i++){
			derivative[i] = this.coefficients[i + 1].multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(derivative);
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * @param z Point in a complex coordinate system where
	 * the value of this {@link ComplexPolynomial} is to be calculated.
	 * @return {@link Complex} number as a result of calculation, i.e. value 
	 * of a {@link ComplexPolynomial} at this point specified by parameter z.
	 */
	public Complex apply(Complex z){
		return calculateValueAtPoint(z);
	}
	
	/**
	 * This method is used as a helper method for public {@link #apply(Complex)} method,
	 * i.e. {@link #apply(Complex)} delegates its job to this method.
	 * To see more informations check {@link #apply(Complex)}.
	 * @param z Parameter which describes in which point value of {@link ComplexPolynomial} will
	 * be calculated. 
	 * @return Value of {@link ComplexPolynomial} at a point specified by parameter z.
	 */
	private Complex calculateValueAtPoint(Complex z) {
		Complex base = z;
		//Initialize result
		Complex result = coefficients[0];
		for(int i = 1; i < coefficients.length; i++){
			result = result.add(z.multiply(coefficients[i]));
			z = z.multiply(base);
		}
		return result;
	}

	@Override
	public String toString() {
		if(this.coefficients == null){
			return "0+0i";
		}
		StringBuilder builder = new StringBuilder();
		for(int i = coefficients.length - 1; i > 0; i--){
			builder.append("(" + this.coefficients[i] + ")" + "Z^" + i + "+");
		}
		builder.append(this.coefficients[0]);
		return builder.toString();
	}
}
