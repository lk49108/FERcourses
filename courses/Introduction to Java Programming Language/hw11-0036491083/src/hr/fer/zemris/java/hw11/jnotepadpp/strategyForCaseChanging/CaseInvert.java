/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging;

/**
 * Implements {@link CaseChanger}.
 * Used for inverting cases.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CaseInvert implements CaseChanger {

	@Override
	public String changeCase(String text) {
		char[] array = text.toCharArray();
		for(int i = 0; i < array.length; i++){
			if(Character.isLowerCase(array[i])){
				array[i] = Character.toUpperCase(array[i]);
			} else {
				array[i] = Character.toLowerCase(array[i]);
			}
		}
		return new String(array);
	}

}
