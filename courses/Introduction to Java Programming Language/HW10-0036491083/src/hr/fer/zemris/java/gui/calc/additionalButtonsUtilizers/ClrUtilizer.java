package hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;

/**
 * This class implements {@link CalcUtilizerAbstract} class
 * and is used for adding {@link JButton} representing instruction
 * clear (which clears, i.e. deletes number inputed onto screen) 
 * to {@link Container} layout.
 * @author Leonardo Kokot
 *
 */
public class ClrUtilizer extends CalcUtilizerAbstract {

	/**
	 * Action listener for clear button.
	 */
	public ActionListener operationPressed = a -> {
		screen.setText("");
	};
	
	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("clr");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		cp.add(element, "1,7");
	}

	

}
