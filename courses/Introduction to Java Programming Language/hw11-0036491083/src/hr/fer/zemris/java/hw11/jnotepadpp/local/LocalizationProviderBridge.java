package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class used in localization mechanism. Used as a bridge, i.e.
 * it decorates some other ILocaliationProvider instance.
 * Allows garbage collector to release frame's memory.
 * This class offers two additional methods: connect() and disconnect(), and it
 * manages a connection status (so that you can not connect if you are already connected). Here is the idea: this
 * class is ILocalizationProvider which, when asked to resolve a key delegates this request to wrapped
 * (decorated) ILocalizationProvider object. When user calls connect() on it, the method will register an
 * instance of anonymous ILocalizationListener on the decorated object. When user calls disconnect(),
 * this object will be deregistered from decorated object.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Variable which helps with managing connection status.
	 */
	boolean connected;
	
	/**
	 * Decorated object.
	 */
	ILocalizationProvider lp;
	
	/**
	 * Listener which is connected and disconnected from 
	 * decorated object and which when is informed about the change triggers
	 * this class's method {@link #fire()} with consequence of
	 * all listeners observing this instance to be informed about this same change.
	 */
	ILocalizationListener listener;
	
	/**
	 * Disconnects {@link #listener} from decorated object.
	 */
	protected void disconnect(){
		if(!connected) return;
		lp.removeLocalizationListener(listener);
	}
	
	/**
	 * Connects {@link #listener} to decorated object.
	 */
	protected void connect(){
		if(connected) return;
		lp.addLocalizationListener(listener);
	}
	
	/**
	 * Constructor which initializes {@link #listener} and
	 * {@link #lp} instance variables.
	 * @param lp
	 */
	public LocalizationProviderBridge(ILocalizationProvider lp) {
		this.lp = lp;
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	@Override
	public String getString(String key) {
		return lp.getString(key);
	}

}
