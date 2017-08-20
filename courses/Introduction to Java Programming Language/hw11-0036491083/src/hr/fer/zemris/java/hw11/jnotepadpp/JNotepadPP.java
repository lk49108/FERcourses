package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.window.JNotepadPPWindow;

/**
 * Class which has only one(main) method from which JNotepad++ is runned.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class JNotepadPP {
	
	/**
	 * Main method, method from which program starts its execution.
	 * @param args Command line arguments, not used here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPPWindow().setVisible(true);
		});
	}
}
