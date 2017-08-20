/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging;

/**
 * Implements {@link CaseChanger}.
 * Used for setting letters to lower cases.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CaseToLower implements CaseChanger{

	@Override
	public String changeCase(String text) {
		char[] array = text.toCharArray();
		for(int i = 0; i < array.length; i++){
			if(Character.isUpperCase(array[i])){
				array[i] = Character.toLowerCase(array[i]);
			}
		}
		return new String(array);
	}

	
}
