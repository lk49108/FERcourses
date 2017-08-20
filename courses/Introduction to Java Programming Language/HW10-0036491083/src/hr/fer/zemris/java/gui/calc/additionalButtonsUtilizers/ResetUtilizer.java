package hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;


import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;

/**
 * This class implements {@link CalcUtilizerAbstract} class
 * and is used for adding {@link JButton} representing instruction
 * reset (which causes {@link Calculator} to reset itself) 
 * to {@link Container} layout and to {@link calcElements} map.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ResetUtilizer extends CalcUtilizerAbstract {

	/**
	 * Action listener for reset button.
	 */
	public ActionListener operationPressed = a -> {
		resetCalc();
	};
	
	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("res");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
				
		cp.add(element, "2,7");
	}

	

}
