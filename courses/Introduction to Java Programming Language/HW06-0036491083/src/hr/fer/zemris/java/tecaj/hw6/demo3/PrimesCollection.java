package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is used for storing prime numbers.
 * It does not store them as objects but calculates them and 
 * returns them afterward (if necessary).
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class PrimesCollection implements Iterable<Integer>{
	
	/**
	 * This instance variable determines how many
	 * first prime numbers are contained in this collection.
	 * It is initialized through constructor.
	 */
	int numOfPrimes;
	
	/**
	 * This constructor creates instance of this class and 
	 * determines how many primes will be hold
	 * in this collection of prime numbers.
	 * @param number Number of first prime numbers that will be hold in 
	 * this collection.
	 */
	public PrimesCollection(int number) {
		if(number < 1){
			throw new IllegalArgumentException("Number of primes must be at least 1. Requested"
					+ "number of prime numbers is " + number);
		}
		this.numOfPrimes = number;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}
	
	/**
	 * This nested class is used as iterator through
	 * prime numbers. It implements Iterator 
	 * interface.
	 * @author Leonardo Kokot
	 * @version 1.0
	 */
	private class PrimeIterator implements Iterator<Integer>{

		/**
		 * This instance variable holds information how many more primes are still
		 * to be returned from this iterator.
		 */
		int remained = numOfPrimes; 
		
		/**
		 * This instance variable determines which
		 * prime will be returned next.
		 */
		int prime = 2;
		
		@Override
		public boolean hasNext() {
			return remained > 0;
		}

		@Override
		public Integer next() {
			if(!(hasNext())){
				throw new NoSuchElementException("There is no more elements in collection this iterator"
						+ "iterates through.");
			}
			remained--;	
			int vrati = prime;
			prime = nextPrime(prime);
			return vrati;	
		}
		
		/**
		 * This method calculates next prime number
		 * starting from the one currently being stored in prime instance variable.
		 * @param current Prime number currently being stored in prime instance variable.
		 * @return Next prime number.
		 */
		private int nextPrime(int current){
			current++;
			if(isPrime(current)){
				return current;
			}
			return nextPrime(current);
		}
		
		/**
		 * This method checks if a given number is prime.
		 * @param number Number to be checked for being prime.
		 * @return True if given number is prime. Otherwise, false is returned.
		 */
		private boolean isPrime(int number){
			for(int i = 2; i <= Math.sqrt(number); i++){
				if(number % i == 0) return false;
			}
			return true;
		}
	}
	
}
