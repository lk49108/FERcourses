package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing x^-1
 * operation to {@link Container} layout.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class ToMinusOneUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		return 1/num;
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("1/x");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("1/x", element);

		cp.add(element, "2,1");
	}

}
