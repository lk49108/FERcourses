package hr.fer.zemris.java.gui.calc.basicOperationUtilizers;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.BasicOperationsUtilizerAbstract;
import hr.fer.zemris.java.gui.calc.strategies.BasicOperations;

/**
 * This class implements {@link BasicOperations} interface and
 * {@link BasicOperationsUtilizerAbstract} class 
 * and is used for adding {@link JButton}, which represents potentionate
 * operation, to {@link Container} layout and to {@link #calcElements} map.
 * It also implements {@link #doOperation(double, double)} method in
 * appropriate way.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class XnOperationUtilizer extends  BasicOperationsUtilizerAbstract implements  BasicOperations{

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("x^n");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		calcElements.put("x^n", element);

		cp.add(element, "5,1");
	}

	@Override
	public double doOperation(double num1, double num2) {
		if(checkBox.isSelected()){
			return Math.pow(num1, 1/num2);
		}
		return Math.pow(num1, num2);
	}

}
