package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing sinus
 * operation or arcus sinus, depends on state of inv {@link JCheckBox},
 * to {@link Container} layout. Initially it represents sinus operation.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class SinusUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		if(checkBox.isSelected()){
			return Math.asin(num);
		}
		return Math.sin(num);
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("sin");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("sin", element);

		cp.add(element, "2,2");
	}

}
