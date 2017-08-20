/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class which extends JLabel, used in producing
 * status bar(part where)
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LocalizableJLabel extends JLabel {

	
	/**
	 * Additional text being displayed on instance of this class.
	 */
	private String additionalText;
	
	/**
	 * Translation of key.
	 */
	private String translation;
	
	/**
	 * Constructor.
	 * @param lp {@link ILocalizationProvider} instance.
	 * @param key Key based on which particular String is fetched.
	 * @param text Additional text being displayed on this label, it is refreshed
	 * independent of keyValue.
	 */
	public LocalizableJLabel(ILocalizationProvider lp, String key, String text){
				
		translation = lp.getString(key);
		
		additionalText = text;
		
		setText(translation + additionalText);
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				translation = lp.getString(key);
				setText(translation + additionalText);
			}
		});
	}
	
	/**
	 * Changes additional text.
	 * @param text Additional text which is being added to this instance of {@link LocalizableJLabel}.
	 */
	public void changeAdditionalText(String text){
		additionalText = text;
		setText(translation + additionalText);
	}
}
