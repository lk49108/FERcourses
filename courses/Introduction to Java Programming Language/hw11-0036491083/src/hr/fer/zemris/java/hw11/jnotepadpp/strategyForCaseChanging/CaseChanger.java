/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging;

import hr.fer.zemris.java.hw11.jnotepadpp.window.JNotepadPPWindow;

/**
 * Interface being used as a root for strategy
 * which is used for changing cases of selected text in {@link JNotepadPPWindow}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface CaseChanger {
	
	/**
	 * Changes cases, what kind of change it will be depends
	 * on implementation.
	 * @param text Text which letters are being changed.
	 * @return Changed text.
	 */
	String changeCase(String text);
	
}
