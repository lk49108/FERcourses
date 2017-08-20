package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class used in mechanism providing
 * ability for application to be localized.
 * It fetches specific String from file(defined with current language)
 * and returns that specific String(getString method).
 * This class extends {@link AbstractLocalizationProvider} class
 * and implements singleton design pattern.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LocalizatonProvider extends AbstractLocalizationProvider {

	/**
	 * Language currently set to be active.
	 */
	String language;
	
	/**
	 * {@link Locale} instance based on {@link #language} instance variable.
	 */
	Locale locale;
	
	/**
	 * {@link ResourceBundle} instance, specified by {@link #locale} instance
	 * variable.
	 */
	ResourceBundle bundle;
	
	/**
	 * Private instance of {@link LocalizatonProvider}, the only one ever
	 * made.
	 */
	private static LocalizatonProvider instance;
	
	/**
	 * Private constructor which sets current language to English by default.
	 */
	private LocalizatonProvider(){
		this.language = "en";
		changeLocaleAndBundle();
	}
	
	/**
	 * Fetches {@link #instance} static variable.
	 * It also initializes {@link #instance} static variable if needed.
	 * @return {@link #instance} static variable.
	 */
	public static LocalizatonProvider getInstance(){
		if(instance == null){
			instance = new LocalizatonProvider();
		}
		return instance;
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * Sets language and triggers method {@link #fire()}.
	 * @param language Language to be set.
	 */
	public void setLanguage(String language){
		this.language = language;
		changeLocaleAndBundle();
		fire();
	}

	/**
	 * Changes {@link #locale} and {@link #bundle} instance variable
	 * to match {@link #language}.
	 */
	private void changeLocaleAndBundle() {
		this.locale = Locale.forLanguageTag(this.language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.Poruke",
				this.locale);
	}

}
