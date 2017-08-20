package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * This class is used for demonstration of primesCollection.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class PrimesDemo1 {

	/**
	 * Programs starts its execution from this method.
	 * @param args Command line arguments. They are not used here.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
		System.out.println("Got prime: "+prime);
		}
	}

}
