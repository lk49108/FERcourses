/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.strategyForOrdering;

import java.text.Collator;

/**
 * Interface used as a root for ordering of lines
 * in specific document. 
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public interface ISort {

	/**
	 * Method which sort String elements in given array of Strings.
	 * @param lines String which are to be sorted based on provided Collator.
	 * @return Sorted array of Strings.
	 */
	String[] sort(String[] lines, Collator collator);
	
}
