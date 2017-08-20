package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing ln
 * operation or e^x, depends on state of inv {@link JCheckBox},
 * to {@link Container} layout. Initially it represents ln operation.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class LnUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		if(checkBox.isSelected()){
			return Math.pow(Math.E, num);
		}
		return Math.log(num);
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("ln");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("ln", element);

		cp.add(element, "4,1");
	}

}
