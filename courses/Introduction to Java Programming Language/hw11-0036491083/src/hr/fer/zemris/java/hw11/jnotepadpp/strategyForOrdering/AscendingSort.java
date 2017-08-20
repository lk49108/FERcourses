/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.strategyForOrdering;

import java.text.Collator;

/**
 * This class implements {@link ISort} interface
 * and is used for ascending order sorting of provided Strings.
 * These sorting is based on provided {@link Collator} instance.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class AscendingSort implements ISort {

	 
	@Override
	public String[] sort(String[] lines, Collator collator) {
		int j = -1;
		for(int i = 1; i < lines.length; i++){
			String max = lines[i];
			for(j = i; j > 0 && collator.compare(lines[j - 1], max) > 0; j--){
				lines[j] = lines[j - 1];
			}
			lines[j] = max;
		}
		return lines;
	}

}
