package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program calculates and prints out n-th number 
 * of Hofstadter's Q sequence. n is obtained
 * as a command line argument.
 * Definition of Hofstadter Q sequence:
 * Q(1) = Q(2) = 1.
 * Q(n) = Q(n-Q(n-1)) + Q(n - Q(n-2)), n > 2.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class HofstadterQ {
	
	/**
	 * Method from which program starts its execution.
	 * @param args Arguments from a command line.
	 * One argument is expected: Index of a Hofstadter's Q sequence 
	 * member that is to be calculated.
	 */
	public static void main(String[] args) {
		
		if(args.length != 1){
			System.err.println("There should be one input argument.");
			System.exit(1);
		}
		
		double number = Double.parseDouble(args[0]);
		
		if(number != (long) number){
			System.err.println("Input number should be an integer.");
			System.exit(1);
		}
		
		long index = (long) number;
		
		if(index < 1){
			System.err.println("Input number should be positive.");
			System.exit(1);
		}
		
		System.out.printf("You requested calculation of %d. number of"
				+ " Hofstadter's Q-sequence. The requested number is %d.%n", index, hofstadterQ(index));
                       
	}
	
	/**
	 * Calculates n-th member of Hofstadter's Q sequence.
	 * @param n Member of Hofstadter's Q sequence which is 
	 * asked to be calculated.
	 * @return n-th member of Hofstadter's Q sequence.
	 */
	private static long hofstadterQ(long n) {
		if (n == (long) 1 || n == (long) 2)
			return 1;
		return hofstadterQ(n - hofstadterQ(n - 1)) + hofstadterQ(n - hofstadterQ(n - 2));
	}

}
