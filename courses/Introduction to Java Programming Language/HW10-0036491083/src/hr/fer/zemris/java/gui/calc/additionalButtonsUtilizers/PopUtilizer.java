package hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;


import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;

/**
 * This class implements {@link CalcUtilizerAbstract} class
 * and is used for adding {@link JButton} representing instruction
 * pop (which causes displayed number on screen to be replaced with
 * the one being placed on top of the {@link #stack}, if {@link #stack}
 * is empty nothing is done)
 * to {@link Container} layout.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class PopUtilizer extends CalcUtilizerAbstract {

	/**
	 * Pop button {@link ActionListener}.
	 */
	private ActionListener operationPressed = a -> {
		if(stack.isEmpty()) return;
		screen.setText((String)stack.pop());
	};
	
	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("pop");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		cp.add(element, "4,7");
	}

	

}
