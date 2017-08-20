package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * This class is used for demonstration of primesCollection.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class PrimesDemo2 {

	/**
	 * Programs starts its execution from this method.
	 * @param args Command line arguments. They are not used here.
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: "+prime+", "+prime2);
			}
		}
	}

}
