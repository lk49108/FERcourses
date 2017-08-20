package hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;

/**
 * This class implements {@link CalcUtilizerAbstract} class
 * and is used for adding {@link JButton} representing instruction
 * push (which causes displayed number on screen to be pushed 
 * onto the {@link #stack})
 * to {@link Container} layout.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class PushUtilizer extends CalcUtilizerAbstract {

	/**
	 * Action listener for push button.
	 */
	private ActionListener operationPressed = a -> {
		String numOnScreen = CalcUtilizerAbstract.screen.getText();
		try {
			Double.parseDouble(numOnScreen);
			stack.push(numOnScreen);
		} catch(NumberFormatException ex){
			CalcUtilizerAbstract.screen.setText("Syntax error.");
			resetCalc();
		}
		
	};
	
	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("push");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		cp.add(element, "3,7");
	}

	

}
