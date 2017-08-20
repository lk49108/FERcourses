package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program accepts a single command line argument
 * (a natural number greater than 1). The program calculates
 * and prints the decomposition of this number 
 * onto its prime factors.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class NumberDecomposition {
	
	/**
	 * Method from which program starts its execution.
	 * @param args Arguments from command line.
	 * One argument expected: Natural number greater
	 * than 1 which is decomposed onto its prime factors.
	 */
	public static void main(String[] args) {
		
		if(args.length != 1){
			System.err.println("One argument is expected: natural number x.");
			System.exit(1);
		}
		
		double y = Double.parseDouble(args[0]);
		
		if(y != (long) y){
			System.err.println("Number must be positive integer.");
			System.exit(1);
		}
		
		int x = (int) y;

		if(x < 1){
			System.err.println("Number must be positive integer.");
			System.exit(1);
		}
		else if(x == 1){
			System.err.println("You requested decomposition of a positive number 1. He has"
					+ " no prime factors and is not prime by himself.");
			System.exit(1);
		}
		else
			System.out.println("You requested decomposition of number " + x + " onto prime factors. Here they are:");
			primFaktori(x);

	}
	
	/**
	 * Computes and prints first x number of
	 * prime numbers. 
	 * @param x Determines which number of prime numbers 
	 * will be calculated and then printed.
	 */
	private static void primFaktori(int x){
		int ispis = 1;
		for(int k=2; x >= 1 && k <= x; k++){
			if(jeProstiBroj(k) && (x%k == 0)){
				System.out.printf("%d. %d%n", ispis, k);
				x=x/k;
				ispis++;
				k--;
			}
		}
		
	}
	
	/**
	 * Checks if a given number is prime. 
	 * @param number Parameter that is checked for being prime.
	 * @return True if received parameter is prime and false if 
	 * it is not. 
	 */
	private static boolean jeProstiBroj(int broj){
		
		for(int i=2; i<broj/2; ++i){
			if(broj%i == 0){
				return false;
			}
		}
		return true;
	}
}
