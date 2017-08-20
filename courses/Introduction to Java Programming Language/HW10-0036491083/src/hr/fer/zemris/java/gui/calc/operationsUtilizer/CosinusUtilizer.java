/**
 * 
 */
package hr.fer.zemris.java.gui.calc.operationsUtilizer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.OperationsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing cosinus
 * operation or arcus cosinus, depends on state of inv {@link JCheckBox},
 * to {@link Container} layout. Initially it represents cosinus operation.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class CosinusUtilizer extends OperationsUtilizerAbstract {

	@Override
	public double calcFunction(double num) {
		if(checkBox.isSelected()){
			return Math.acos(num);
		}
		return Math.cos(num);
	}

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("cos");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("cos", element);

		cp.add(element, "3,2");
	}

	

}
