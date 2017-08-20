package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * This is a class/program for testing collection classes.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class TestCollections {

	/**
	 * This is the only method in this program. It is also method from which
	 * program starts its execution.
	 * 
	 * @param args
	 *            Not used in this particular program.
	 */
	public static void main(String[] args) {

		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position
						// 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);

		/**
		 * This is local class that extends class Processor. It contains only
		 * one method.
		 * 
		 * @author Leonardo Kokot
		 */
		class P extends Processor {

			/**
			 * This method prints out string representation of object o.
			 * 
			 * @param o
			 *            Object type parameter.
			 */
			public void process(Object o) {
				System.out.println(o);
			}
		}
		;
		System.out.println("col1 elements:");
		col.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(new Integer(20)); // removes 20 from collection (at position
										// 0).
	}

}
