package hr.fer.zemris.java.tecaj.fractals;



import hr.fer.zemris.java.tecaj.hw9.complex.Complex;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexPolynomial;
import hr.fer.zemris.java.tecaj.hw9.complex.ComplexRootedPolynomial;

/**
 * This class holds method
 * {@link #calculate(double, double, double, double, int, int, int, int, short[])}
 * which is called by instance of {@link WorkingJob} in method call.
 * More specifically this class is used to provide work that is actually done 
 * by asynchronous thread executing call() method in {@link WorkingJob} class.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class Newton_Raphson {

	/**
	 * Convergence threshold, used in calculation of Newton-Raphson
	 * fractal.
	 */
	private static final double CONVERGENCE_TRESHOLD = 0.0001;
	
	/**
	 * Maximum number of while loop iterations, used in
	 * calculation of Newton-Raphson fractal.
	 */
	private static final int MAX_ITER = 32;
	
	/**
	 * Root threshold, used in calculation of Newton-Raphson
	 * fractal.
	 */
	private static final double ROOT_TRESHOLD = 0.002;
	
	/**
	 * Polynomial for which corresponding fractal is produced.
	 */
	private static ComplexRootedPolynomial POLYNOMIAL;
	
	/**
	 * @param reMin Defines a minimum of real part
	 * of a complex space that will be shown on 
	 * desktop.
	 * @param reMax Defines a maximum of real part
	 * of a complex space that will be shown on 
	 * desktop.
	 * @param imMin Defines a minimum of imaginary part
	 * of a complex space that will be shown on 
	 * desktop.
	 * @param imMax Defines a maximum of imaginary part
	 * of a complex space that will be shown on 
	 * desktop.
	 * @param width Represents number of pixels (in width) that
	 * will be used to render a picture.
	 * @param height Represents number of pixels (in height) which
	 * will be used to render a picture.
	 * @param yMin Determines which part of picture will be rendered by thread instance 
	 * which holds specific value of this instance variable. Works in pair with yMax.
	 * @param yMax Determines which part of picture will be rendered by thread instance 
	 * which holds specific value of this instance variable. Works in pair with  yMin.
	 * @param data  Reference to short values array which will hold data
	 * specifying what type of color will be used to color each pixel.
	 * This array is filled up in this method. 
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax,
			int width, int height, int yMin, int yMax, short[] data){
		Complex zn = null;
		Complex zn1 = null;
		int offset = yMin * height;
		for(int y = yMin; y <= yMax; y++){
			for( int x = 0; x < width; x++){
				double cre = x / (width-1.0) * (reMax - reMin) + reMin;
				double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
				zn1 = new Complex(cre, cim);
				int iter = 0;
				do {
					zn = zn1;
					zn1 = calculateExpression(zn);
					iter++;
				} while(zn1.sub(zn).module() > CONVERGENCE_TRESHOLD && iter < MAX_ITER);
				int index = POLYNOMIAL.indexOfClosestRootFor(zn1, ROOT_TRESHOLD);
				if(index == -1){
					data[offset++] = 0;
				} else {
					data[offset++] = (short) index;
				}
			}
		}
	}

	/**
	 * Calculates expression: Zn1 = Zn - f(x)/f'(x).
	 * @param zn Complex number Zn in above expression.
	 * @return Zn1 as instance of {@link Complex}.
	 */
	private static Complex calculateExpression(Complex zn) {
		Complex numerator = POLYNOMIAL.apply(zn);
		ComplexPolynomial derived = POLYNOMIAL.toComplexPolynom().derive();
		Complex denominator = derived.apply(zn);
		return zn.sub(numerator.divide(denominator));
	}

	/**
	 * Setter for {@link #POLYNOMIAL} static variable. 
	 * @param polynomial Polynomial which will be (probably) used to 
	 * calculate and render its corresponding fractal.
	 */
	public static void setPOLYNOMIAL(ComplexRootedPolynomial polynomial) {
		POLYNOMIAL = polynomial;
	}

	/**
	 * Static method used for obtaining {@link ComplexRootedPolynomial}
	 * stored in {@link #POLYNOMIAL} static variable.
	 * @return  {@link #POLYNOMIAL} static variable value.
	 */
	public static ComplexRootedPolynomial getPOLYNOMIAL() {
		return POLYNOMIAL;
	}
}
