package hr.fer.zemris.java.gui.calc.basicOperationUtilizers;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.BasicOperationsUtilizerAbstract;
import hr.fer.zemris.java.gui.calc.strategies.BasicOperations;

/**
 * This class implements {@link BasicOperations} interface and
 * {@link BasicOperationsUtilizerAbstract} class 
 * and is used for adding {@link JButton}, which represents divide
 * operation, to {@link Container} layout and to {@link #calcElements} map.
 * It also implements {@link #doOperation(double, double)} method in
 * appropriate way.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class DivideOperationUtilizer extends  BasicOperationsUtilizerAbstract implements  BasicOperations{

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("/");
		element.setBackground(color);
		
		element.addActionListener(operationPressed);
		
		cp.add(element, "2,6");
	}

	@Override
	public double doOperation(double num1, double num2) {
		return num1/num2;
	}

}
