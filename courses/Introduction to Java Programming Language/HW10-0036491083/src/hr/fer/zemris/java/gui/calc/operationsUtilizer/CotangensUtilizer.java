package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing ctg
 * operation or arcus ctg, depends on state of inv {@link JCheckBox},
 * to {@link Container} layout. Initially it represents ctg operation.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class CotangensUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		if(checkBox.isSelected()){
			return Math.PI/2 - Math.atan(num);
		}
		return 1/Math.tan(num);
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("ctg");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("ctg", element);

		cp.add(element, "5,2");
	}

}
