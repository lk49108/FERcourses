package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing log
 * operation or 10^x, depends on state of inv {@link JCheckBox},
 * to {@link Container} layout. Initially it represents log operation.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class LogUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		if(checkBox.isSelected()){
			return Math.pow(10, num);
		}
		return Math.log10(num);
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("log");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("log", element);

		cp.add(element, "3,1");
	}

}
