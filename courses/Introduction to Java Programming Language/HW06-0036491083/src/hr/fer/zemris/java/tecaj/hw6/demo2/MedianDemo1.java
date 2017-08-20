package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * This class is used for demonstrating functionality of
 * Likemedian class.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class MedianDemo1 {

	/**
	 * Program starts its execution from this method.
	 * @param args Not used here.
	 */
	public static void main(String[] args) {

		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
		
		Optional<Integer> result = likeMedian.get();
		System.out.println(result.get());
		
		for(Integer elem : likeMedian) {
			System.out.println(elem);
		}
		
	}

}
