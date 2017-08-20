package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JButton;
import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Interface used as a part of mechanism which
 * provides localization of {@link JNotepadPP} program,
 * i.e. {@link JNotepadPPWindow}. Classes, i.e. instances
 * of classes which implement this interface are used as a subjects
 * in which, when localization is changed inform all
 * of its observers ({@link ILocalizationListener} instances) about
 * the change.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface ILocalizationProvider {

	/**
	 * Adds {@link ILocalizationListener} to internal list of listeners.
	 * @param listener {@link ILocalizationListener} instance which is to be added
	 * into internal list.
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes {@link ILocalizationListener} from internal list of listeners.
	 * @param listener {@link ILocalizationListener} instance which is to removed
	 * from internal list.
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Returns appropriate String based on
	 * provided key (and on language which is currently set to be active).
	 * @param key Key for which appropriate String is returned.
	 * @return String instance, mostly text which will be displayed
	 * on {@link JButton} or {@link JLabel}.
	 */
	String getString(String key);
	
}
