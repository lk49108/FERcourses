/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProviderBridge;

/**
 * Class which extends {@link LocalizationProviderBridge}.
 * In its constructor it registers itself as a WindowListener
 * to its JFrame; when frame is opened, it calls connect and when
 * frame is closed, it calls disconnect.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor.
	 * @param lp {@link ILocalizationProvider} instance.
	 * @param frame JFrame instance.
	 */
	public FormLocalizationProvider(ILocalizationProvider lp, JFrame frame) {
		super(lp);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
