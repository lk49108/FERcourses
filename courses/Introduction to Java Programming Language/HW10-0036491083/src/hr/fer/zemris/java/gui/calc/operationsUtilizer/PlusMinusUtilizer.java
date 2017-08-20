package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing 
 * operation of changing sign
 * to {@link Container} layout.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class PlusMinusUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		return -num;
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("+/-");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		cp.add(element, "5,4");
	}

}
