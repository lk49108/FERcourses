package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing tangens
 * operation or arcus tangens, depends on state of inv {@link JCheckBox},
 * to {@link Container} layout. Initially it represents tangens operation.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class TangensUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		if(checkBox.isSelected()){
			return Math.atan(num);
		}
		return Math.tan(num);
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("tan");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("tan", element);

		cp.add(element, "4,2");
	}

}
