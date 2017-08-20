/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw9.complex;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for working with complex numbers.
 * Complex number represented by instance of this class is immutable,
 * i.e. can not be change. Consequently methods implemented
 * in this class return new instance of this class.
 * Each complex number in this class is represented in
 * two ways First one is using "real" and "imaginary"
 * part of complex number. The other form of complex number
 * is using its module and angle to show it in 
 * polar form. Reason why both approaches are used
 * is because of simple and faster execution of methods 
 * implemented in this class. For example,
 * complex numbers are easier to be multiplied
 * in polar form. But, for example, it is easier to 
 * sum two complex numbers using its real and complex part. 
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class Complex {

	/**
	 * This instance variable holds imaginary part of a complex number 
	 * which is represented by instance of this class.
	 */
	private final double imaginary;
	
	/**
	 * This instance variable holds real part of a complex number 
	 * which is represented by instance of this class.
	 */
	private final double real;
	
	/**
	 * This instance variable holds module(sqrt(real^2 + imaginary^2)) of a complex number
	 * which is represented by instance of this class.
	 */
	private final double module;
	
	/**
	 * This instance variable holds angle[-PI, PI](radians) of a complex number
	 * which is represented by instance of this class.
	 */
	private final double angle;
	
	/**
	 * This static instance variable holds complex number
	 * which is actually instance of {@link Complex}.
	 * Its value equals to z = 0 + 0*i. 
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * This static instance variable holds complex number
	 * which is actually instance of {@link Complex}.
	 * Its value equals to z = 1 + 0*i. 
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * This static instance variable holds complex number
	 * which is actually instance of {@link Complex}.
	 * Its value equals to z = -1 + 0*i. 
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * This static instance variable holds complex number
	 * which is actually instance of {@link Complex}.
	 * Its value equals to z = 0 + 1*i. 
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * This static instance variable holds complex number
	 * which is actually instance of {@link Complex}.
	 * Its value equals to z = 0 + (-1)*i. 
	 */
	public static final Complex IM_NEG = new Complex(0,-1);

	/**
	 * This constructor creates instance of this class and initializes 
	 * instance variables of this class to appropriate values.
	 * @param imaginary Imaginary part of complex number which is being created.
	 * @param real Real part of complex number which is being created.
	 */
	public Complex(double real, double imaginary) {
		super();
		this.imaginary = imaginary;
		this.real = real;
		this.angle = Math.atan2(imaginary, real);
		this.module = Math.sqrt(real * real + imaginary * imaginary);
	}
	
	/**
	 * This private constructor is used for creating
	 * instance of this class when it is more suitable to
	 * create complex number providing its parameters in 
	 * polar form, i.e. when we are multiplying two complex numbers it is easier
	 * to create resulting complex number by providing its polar coordinates.
	 * This constructor delegates its work to {@link #Complex(double, double)}. 
	 * @param module
	 * @param angle
	 * @param difference <code>null</code> is always provided as this argument.
	 * It is only used to distinguish this constructor from {@link #Complex(double, double)}
	 */
	private Complex(double module, double angle, Object difference){
		this(module * Math.cos(angle), module * Math.sin(angle));
	}
	
	/**
	 * This is default constructor which, when called delegates construction of
	 * new instance of this class to another constructor with parameters
	 * real = 0, imaginary = 0.
	 */
	public Complex(){
		this(0, 0);
	}
	
	/**
	 * Returns module of a complex number represented by instance of this class.  
	 * @return {@link #module}
	 */
	public double module(){
		return this.module;
	}
	
	/**
	 * Multiplies two complex numbers and returns result of operation
	 * as a new instance of {@link Complex}. First complex number used
	 * in multiplying is the one on which this method is called, other one 
	 * is provided as argument.
	 * @param c Complex number which multiplies complex number on which
	 * this method is called.
	 * @return Resulting complex number as instance of {@link Complex}.
	 */
	public Complex multiply(Complex c){
		return new Complex(this.module * c.module, this.angle + c.angle, null);
	}
	
	/**
	 * Divides two complex numbers and returns result of operation
	 * as a new instance of {@link Complex}. First complex number used
	 * in division is the one on which this method is called, other one 
	 * is provided as argument.
	 * @param c Complex number which divides complex number on which
	 * this method is called.
	 * @return Resulting complex number as instance of {@link Complex}.
	 */
	public Complex divide(Complex c){
		return new Complex(this.module / c.module, this.angle - c.angle, null);
	}
	
	/**
	 * Sums two complex numbers and returns result of operation
	 * as a new instance of {@link Complex}. First complex number used
	 * in summation is the one on which this method is called, other one 
	 * is provided as argument.
	 * @param c Complex number which is added to complex number on which
	 * this method is called.
	 * @return Resulting complex numbers as instance of {@link Complex}.
	 */
	public Complex add(Complex c){
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Subtracts two complex numbers and returns result of operation
	 * as a new instance of {@link Complex}. First complex number used
	 * in subtraction is the one on which this method is called, other one 
	 * is provided as argument.
	 * @param c Complex number which is subtracted from complex number on which
	 * this method is called.
	 * @return Resulting complex number as instance of {@link Complex}.
	 */
	public Complex sub(Complex c){
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Negates complex number on which this method is called.
	 * @return Resulting complex number as instance of {@link Complex}.
	 */
	public Complex negate(){
		return new Complex(-this.real, -this.imaginary);
	}
	
	/**
	 * Calculates z^n, where z is a complex number on which this method is
	 * called, and n is a non-negative integer provided as argument.
	 * @param n Non-negative integer which is used in above described operation.
	 * @return Resulting complex number as instance of {@link Complex}.
	 * @throws IllegalArgumentException if provided argument is negative.
	 */
	public Complex negate(int n){
		if(n < 0){
			throw new IllegalArgumentException("Expected non-negative "
					+ "integer but was given: " + n + ".");
		}
		return new Complex(Math.pow(this.angle, n), this.angle * n, null);
	}

	/**
	 * Calculates n-th root of a complex number and returns
	 * all of those (n calculated n-th roots) organized 
	 * ordinarily in a {@link List}.
	 * @param n Positive integer which determine which 
	 * root of a complex number will be calculated and returned.
	 * @return Resulting complex number as instance of {@link Complex}.
	 * @throws IllegalArgumentException if provided argument is not positive.
	 */
	public List<Complex> root(int n){
		if(n < 1){
			throw new IllegalArgumentException("Expected positive "
					+ "integer but was given: " + n + ".");
		}
		List<Complex> roots = new ArrayList<>();
		for(int i = 0; i < n; i++){
			roots.add(new Complex(Math.pow(this.module, 1.0/n), (this.angle + 2 * i * Math.PI) / n, null));
		}
		return roots;
	}
	
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.###");
		return df.format(this.real) + ((this.imaginary >= 0) ? "+" : "") + df.format(this.imaginary) + "i";
	}
}
