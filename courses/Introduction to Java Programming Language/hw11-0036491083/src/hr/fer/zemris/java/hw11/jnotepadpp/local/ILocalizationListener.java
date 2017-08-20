package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Interface used as a part of mechanism which
 * provides localization of {@link JNotepadPP} program,
 * i.e. {@link JNotepadPPWindow}, all listeners used in this mechanism 
 * implement this interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface ILocalizationListener {

	/**
	 * Method which is called when localization changes,
	 * it does some specific job to adjust specific {@link JComponent}
	 * to this new {@link Locale}.
	 */
	void localizationChanged();
}
