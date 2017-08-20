package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program accepts a single command line 
 * argument n (positive integer), and then 
 * calculates and prints first n prime numbers.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class PrimeNumbers {
	
	/**
	 * Method from which program start its execution.
	 * @param args Arguments obtained through command line. 
	 * One argument is expected. Number of first prime 
	 * numbers that are to be calculated.
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("There should be positive integer as an input argument.");
			System.exit(1);
		}
		
		double checking = Double.parseDouble(args[0]);
		
		if(checking != (long) checking){
			System.err.println("Positive integer is excpected as an argument.");
			System.exit(1);
		}
		
		int number = (int) checking;
		
		if(number < 1){
			System.err.println("Positive integer is excpected as an argument.");
			System.exit(1);
		}
		
		System.out.println("You requested calculation of " + number + 
				" prime numbers. Here they are:");
		
		computeNPrimes(number);
	}
	
	/**
	 * Computes and prints first n prime numbers.
	 * @param n Parameter which determines how many 
	 * prime numbers will be calculated and printed out.
	 */
	private static void computeNPrimes(int n){
		int help = n;
		System.out.println((n - help-- +1) + ". 2");
		for(int i=3; help > 0; i+=2){
			if(isPrime(i)){
				System.out.println((n - help-- +1) + ". " + i);
			}
		}
	}
	
	/**
	 * Checks if a given number is prime. 
	 * @param number Parameter (positive integer) that is checked for being prime.
	 * @return True if received parameter "number" is prime and false if 
	 * it is not. 
	 */
	private static boolean isPrime(int number){
		for(int i=2; i<number/2; ++i){
			if(number % i == 0){
				return false;
			}
		}
		return true;
	}
}
