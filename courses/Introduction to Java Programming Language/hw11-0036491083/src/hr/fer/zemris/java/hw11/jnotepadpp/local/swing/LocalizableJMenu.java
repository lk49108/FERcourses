/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Localizable type of {@link JMenu}. Consequently
 * this class extends {@link JMenu}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LocalizableJMenu extends JMenu {

	/**
	 * Constructor.
	 * @param lp {@link ILocalizationProvider} instance.
	 * @param key Key based on which particular String is fetched.
	 */
	public LocalizableJMenu(ILocalizationProvider lp, String key){
				
		String translation = lp.getString(key);
		setText(translation);
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				setText(translation);
			}
		});
	}
}
