package hr.fer.zemris.java.tecaj.hw2;

/**
 * Class that provides support for working with complex numbers. This class
 * represents unmodifiable complex number, if some changes are made upon this
 * complex number, new complex number is returned from method, and this "old"
 * complex number remains untouched.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ComplexNumber {

	/**
	 * This instance variable represents imaginary part of a complex number.
	 */
	double imaginary;
	/**
	 * This instance variable represents real part of a complex number.
	 */
	double real;

	/**
	 * This is a public constructor which accepts real and imaginary part of a
	 * complex numbers. It makes one instance of this class with appropriate
	 * imaginary and real instance variables.
	 * 
	 * @param real
	 *            Real part of complex number.
	 * @param imaginary
	 *            Imaginary part of complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * This method creates an instance of ComplexNumber with real part set to
	 * value defined by parameter real, and imaginary part set to 0.
	 * 
	 * @param real
	 *            Real part of a complex number which is being created.
	 * @return Instance of a ComplexNumber.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}

	/**
	 * This method creates an instance of ComplexNumber with real part set to 0,
	 * and imaginary part set to value of given double type parameter imaginary.
	 * 
	 * @param imaginary
	 *            Imaginary part of a complex number which is being created.
	 * @return Instance of a ComplexNumber.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * This method creates an instance of ComplexNumber with its real and
	 * imaginary part set to correspond with values of a given magnitude and
	 * angle parameters.
	 * 
	 * @param magnitude
	 *            Magnitude of a complex number being created.
	 * @param angle
	 *            Angle of a complex number being created.
	 * @return Instance of a complex number.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * This method creates a new complex number parsing string to its
	 * corresponding complex number.
	 * 
	 * @param s
	 *            String representing a complex number that is being created.
	 * @return Instance of a complex number.
	 */
	public static ComplexNumber parse(String s) {
		return new ComplexNumber(decodeReal(s), decodeImaginary(s));
	}

	/**
	 * @return Imaginary part of a complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * @return Real part of a complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @return Magnitude of a complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(imaginary * imaginary + real * real);
	}

	/**
	 * @return Angle of a complex number. Value of angle is between 0 and 2*pi.
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * This method adds another complex number to this one.
	 * 
	 * @param c
	 *            Value of a complex number that is being added.
	 * @return New instance of a {@link ComplexNumber} that represents the
	 *         result of summing two complex numbers.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}

	/**
	 * This method subtracts another complex number from this one.
	 * 
	 * @param c
	 *            Complex number that is being subtracted.
	 * @return Result of subtraction as a new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}

	/**
	 * This method multiplies another complex number with this one.
	 * 
	 * @param c
	 *            Complex number that with which is this complex number being
	 *            multiplied.
	 * @return Result of multiplying as a new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() * c.getMagnitude(),
				(this.getAngle() + c.getAngle()) % (2 * Math.PI));
	}

	/**
	 * This method divides this complex number with another one.
	 * 
	 * @param c
	 *            Complex number with whom this complex number is divided..
	 * @return Result of a division as a new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() / c.getMagnitude(),
				(this.getAngle() - c.getAngle()) % (2 * Math.PI));
	}

	/**
	 * This method creates a new complex number as a result of the base(this
	 * complex number) to the exponent(parameter n) power.
	 * 
	 * @param n
	 *            Complex number representing power.
	 * @return Result of powering the complex number as a new instance of
	 *         {@link ComplexNumber}.
	 */
	public ComplexNumber power(int n) {
		return fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), (double) n), (this.getAngle() * n) % (2 * Math.PI));
	}

	/**
	 * This method calculates n roots(of order n) of this complex number.
	 * 
	 * @param n
	 *            Order of roots which are being calculated.
	 * @return Array of n n-th roots that are calculated in this method.
	 */
	public ComplexNumber[] root(int n) {
		ComplexNumber[] complex = new ComplexNumber[n];
		double angle = this.getAngle() / n;
		for (int i = 0; i < n; i++) {
			complex[i] = fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), 1f / n), angle + (2 * i * Math.PI) / n);
		}
		return complex;
	}

	/**
	 * This method converts this complex number into String and returns that
	 * same String.
	 * 
	 * @return Returns string representation of this complex number value.
	 */
	public String toString() {
		return this.real + (this.imaginary < 0 ? "-" : "+") + Math.abs(this.imaginary) + "i";
	}

	/**
	 * This method decodes imaginary part of a complex number from its string
	 * representation.
	 * 
	 * @param string
	 *            String representation of a complex number.
	 * @return Imaginary part of a complex number contained in string parameter
	 *         as double value.
	 */
	private static double decodeImaginary(String string) {
		string = string.trim();
		int index;
		if ((index = string.indexOf('i')) == -1) {
			return 0.0;
		} else {

			if (index == 0) {
				return 1.0;
			}

			int indexOfI = index--;

			while ((index != 0) && (Character.isDigit(string.charAt(index)) || string.charAt(index) == '.')) {
				index--;
			}

			return Double.parseDouble(string.substring(index, indexOfI));
		}
	}

	/**
	 * This method decodes real part of a complex number from its string
	 * representation.
	 * 
	 * @param string
	 *            String representation of a complex number.
	 * @return Real part of a complex number contained in string parameter as
	 *         double value.
	 */
	private static double decodeReal(String string) {
		string = string.trim();
		int checker = 0;
		while ((Character.isDigit(string.charAt(checker)) || string.charAt(checker) == '.'
				|| (string.charAt(checker) == '-' && checker == 0)) && checker < (string.length() - 1)) {
			checker++;
		}
		if (string.charAt(checker) == 'i') {
			return 0.0;
		} else {
			if (checker == string.length() - 1) {
				return Double.parseDouble(string.substring(0, checker + 1));
			} else {
				return Double.parseDouble(string.substring(0, checker));
			}
		}
	}
}
