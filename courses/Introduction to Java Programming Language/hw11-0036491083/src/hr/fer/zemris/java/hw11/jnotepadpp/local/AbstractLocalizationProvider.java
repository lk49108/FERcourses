package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class used in localization
 * mechanism. This class is extended by final version of class
 * used in this mechanism, i.e. {@link LocalizatonProvider}.
 * @author Leonardo Kokot
 * @version 1.0
 */
abstract public class AbstractLocalizationProvider implements ILocalizationProvider{

	/**
	 * List of listeners which observe instance of this class.
	 */
	List<ILocalizationListener> listeners = new ArrayList<>();

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Fires event in a way that it informs all listeners about localization change.
	 * Based on this change, listeners perform appropriate job.
	 */
	protected void fire(){
		for(ILocalizationListener listener : listeners){
			listener.localizationChanged();
		}
	}
}
