package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program calculates roots of
 * a complex number that is obtained 
 * through command line parameters. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Roots {
	
	/**
	 * Method from which program starts its execution.
	 * @param args Command line arguments. First argument : Real
	 * part of a complex number. Second argument : Imaginary
	 * part of a complex number. Third argument : Root 
	 * that is to be calculated.
	 */
	public static void main(String[] args) {
		if(args.length != 3){
			System.err.println("There should be 3 input arguments.");
			System.exit(1);
		}
		
		double real = Double.parseDouble(args[0]);
		double imaginary = Double.parseDouble(args[1]);
		double root = Double.parseDouble(args[2]);
		
		if(root != (long) root || root < 1){
			System.err.println("Root should be positive integer.");
			System.exit(1);
		}
		
		long root2 = (long) root;
		
		System.out.println("You requested calculation of " + root2 + ". roots. Solutions are:");	
		rootCalculator(real, imaginary, (int) root2);
	}
	
	/**
	 * Calculates and prints n n-th roots of a complex number.
	 * @param real Real part of a complex number.
	 * @param imaginary Imaginary part of a complex number.
	 * @param n Order of a roots that are calculated.
	 */
	private static void rootCalculator(double real, double imaginary, long n){
		double abs = Math.hypot(real, imaginary);
		double angle = Math.atan2(imaginary, real);
		for(int j = 0; j < n; ++j){
			System.out.print((j+1) + ") ");
			
			double real2 = Math.pow(abs, 1. / n) * Math.cos( (angle + 2 * j * Math.PI) / n);
			
			if(Math.abs(real2 - Math.round(real2)) < 1E-6)
				System.out.printf("%d", Math.round(real2));
			else {
				real2 = (double) Math.round(real2 * 10000000) / 10000000;
				System.out.printf("%s", String.valueOf(real2));
			}
			
			double imaginary2 = Math.pow(abs, 1. / n) * Math.sin( (angle + 2 * j * Math.PI) / n);
			
			if(Math.abs(imaginary2 - Math.round(imaginary2)) < 1E-6)
				System.out.printf(" %s %di%n",  imaginary2 < 0 ? "-" : "+", Math.abs(Math.round(imaginary2)));
			else{
				imaginary2 = (double) Math.round(imaginary2 * 10000000) / 10000000;
				System.out.printf(" %s %si%n",  imaginary2 < 0 ? "-" : "+", String.valueOf(Math.abs(imaginary2)));
			}
		
		}
	}
}
