package hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;

/**
 * This class implements {@link CalcUtilizerAbstract} class
 * and is used for adding {@link JButton} representing instruction
 * equals (which causes result of specific operation
 * be inputed onto the screen) 
 * to {@link Container} layout and to {@link calcElements} map.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class EqualsUtilizer extends CalcUtilizerAbstract {

	/**
	 * {@link ActionListener} for equals button.
	 */
	private ActionListener operationPressed = a -> {
		try {
			double num2 = Double.parseDouble(screen.getText());
			double num1 = numberStored;
			double result = basicOperationLastPressed.doOperation(num1, num2);
			screen.setText(Double.toString(result));
			resetScreenOnNextInput = true;
			numberStored = result;
			basicOperationLastPressed = null;
		} catch (Exception ex){
			screen.setText("Syntax error.");
			resetCalcNoStackClear();
		}
	};
	
	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("=");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		cp.add(element, "1,6");
	}

	

}
