/**
 * 
 */
package hr.fer.zemris.java.gui.calc.abstractUtilizers;


import java.awt.event.ActionListener;

import javax.swing.JButton;


import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * This abstract class is used as a root 
 * class for all classes which utilize
 * each digit button placed on {@link Calculator}. This class also defines one instance variable 
 * {@link #digitPressed} {@link ActionListener} instance which is added
 * as a listener to every {@link JButton} instance representing specific digit 
 * on {@link Calculator}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class DigitsUtilizerAbstract extends CalcUtilizerAbstract{
		
	/**
	 * This {@link ActionListener} instance variable is added to
	 * all {@link JButton} instances which represents specific
	 * digit on {@link Calculator} and which is implemented in class
	 * that which extends this one, i.e. {@link #ButtonOneUtilizer}.
	 */
	protected ActionListener digitPressed = a -> {
		JButton b = (JButton)a.getSource();
		if(resetScreenOnNextInput){
			screen.setText(b.getText());
			resetScreenOnNextInput = false;
		} else {
			screen.setText(screen.getText() + b.getText());
		}
	};
	
}
