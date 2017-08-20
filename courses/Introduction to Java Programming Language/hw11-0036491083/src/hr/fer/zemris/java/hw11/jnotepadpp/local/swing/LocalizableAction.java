/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * {@link LocalizableAction} instance which implements
 * {@link AbstractAction} and allows loacalization for
 * components which uses it.
 * @author Leonardo Kokot
 * @version 1.0
 */
abstract public class LocalizableAction extends AbstractAction{
	
	/**
	 * Constructor.
	 * @param lp {@link ILocalizationProvider} instance.
	 * @param key Key based on which particular String is fetched.
	 */
	public LocalizableAction(ILocalizationProvider lp, String key) {

		String translation = null;
		String descriptionTranslation = null;
		//Initial asking for translation of the key
		try{
			translation = lp.getString(key);
			descriptionTranslation = lp.getString(key + "Description");
		} catch(NullPointerException ex) {
			
		}
		putValue(NAME, translation);		
		putValue(SHORT_DESCRIPTION, descriptionTranslation);
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				putValue(NAME, translation);
				String descriptionTranslation = lp.getString(key + "Description");
				putValue(SHORT_DESCRIPTION, descriptionTranslation);
			}
		});
	}

}
