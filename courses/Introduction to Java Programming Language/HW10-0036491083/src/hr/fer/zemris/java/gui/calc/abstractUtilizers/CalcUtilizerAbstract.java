package hr.fer.zemris.java.gui.calc.abstractUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.PopUtilizer;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.PushUtilizer;
import hr.fer.zemris.java.gui.calc.strategies.BasicOperations;

/**
 * This class is used as a 
 * root class for other classes, which 
 * represents groups of {@link Calculator} buttons
 * which are further implemented by appropriate
 * classes representing single {@link JButton} implementation.
 * It defines static variables used in providing right 
 * functionalities of {@link Calculator}.
 * It defines abstract
 * method {@link #add(Container, Color)} which is used by subclasses
 * for adding {@link JButton} instance  or {@link JCheckBox} or {@link JLabel}
 * (representing specific element of {@link Calculator}) instance
 * to {@link Container} layout and putting them into {@link Map} 
 * instance {@link #calcElements}. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class CalcUtilizerAbstract {
	
	/**
	 * This stack is used in implementation of {@link Calculator}
	 * It is used by {@link PushUtilizer} and {@link PopUtilizer}, i.e.
	 * buttons push and pop on calculator.
	 */
	protected static ObjectStack stack = new ObjectStack();
	
	/**
	 * Method used for creating and adding particular {@link JButton} or 
	 * {@link JCheckBox} or {@link JLabel} instance to {@link Container} layout
	 * and for putting it into {@link #calcElements} {@link Map} instance.
	 * @param cp {@link Container} instance to which layout particular button is 
	 * added to.
	 * @param color {@link Color} instance which defines with which color will this 
	 * newly created button be filled in.
	 */
	abstract public void add(Container cp, Color color);

	/**
	 * This variable is used for holding {@link BasicOperations}
	 * instance as a representation of basic operation which was pressed
	 * on {@link Calculator} last.
	 */
	protected static BasicOperationsUtilizerAbstract basicOperationLastPressed = null;
	
	/**
	 * This variable stores in itself last number which was displayed on {@link Calculator}
	 * display. I.e. if user enters 2,3,*,4,=. First double value of 23 will be stored in this variable
	 * when operation "*" will be typed in. After that when operation "=" is typed in
	 * double value of 92 will be stored in this variable.
	 */
	protected static double numberStored;

	/**
	 * Used for storing specific buttons of this {@link Calculator}. 
	 */
	protected static Map<String, JButton> calcElements = new HashMap<>();
	
	/**
	 * Helper variable used in calculator work. 
	 */
	protected static boolean resetScreenOnNextInput = true;
	
	/**
	 * {@link JLabel} instance, i.e. screen of calculator.
	 */
	protected static JLabel screen = null;
	
	/**
	 * Method which is called if any error in using calculator occurs.
	 * It resets calculator.
	 */
	protected void resetCalc(){
		basicOperationLastPressed = null;
		numberStored = 0;
		stack.clear();
		resetScreenOnNextInput = true;
	}
	
	/**
	 * Method which is called if any error in using calculator occurs.
	 * It resets calculator, but leaves stack untouched.
	 */
	protected void resetCalcNoStackClear(){
		basicOperationLastPressed = null;
		numberStored = 0;
		resetScreenOnNextInput = true;
	}
	
	/**
	 * {@link JCheckBox} instance used as button inverse on this calculator.
	 */
	protected static JCheckBox checkBox;
	
}
