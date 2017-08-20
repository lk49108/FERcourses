package hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;

/**
 * This class implements {@link CalcUtilizerAbstract} class
 * and is used for adding {@link JButton} representing instruction
 * inv (which specific function of this calculator to change its meaning) 
 * to {@link Container}.
 * @author Leonardo Kokot
 * @version 1.0
 */
/**
 * @author Leonardo
 *
 */
public class InvUtilizer extends CalcUtilizerAbstract {

	/**
	 * Inverse button {@link ActionListener}.
	 */
	private ActionListener operationPressed = a -> {
		changeMeaningOfOperations();
	};
	
	@Override
	public void add(Container cp, Color color) {
		JCheckBox element = new JCheckBox("inv");
		element.setBackground(color);
			
		element.addActionListener(operationPressed);
		
		checkBox = element;
		
		cp.add(element, "5,7");
	}

	/**
	 * Changes meaning of specific buttons on {@link Calculator}.
	 */
	private void changeMeaningOfOperations() {
		if(checkBox.isSelected()){
			changeMeaningOfSin("arc sin");
			changeMeaningOfCos("arc cos");
			changeMeaningOfTan("arc tan");
			changeMeaningOfCtg("arc ctg");
			changeMeaningOfLog("10^x");
			changeMeaningOfLn("e^x");
			changeMeaningOfXn("x^(1/n)");
		} else {
			changeMeaningOfSin("sin");
			changeMeaningOfCos("cos");
			changeMeaningOfTan("tan");
			changeMeaningOfCtg("ctg");
			changeMeaningOfLog("log");
			changeMeaningOfLn("ln");
			changeMeaningOfXn("x^n");
		}
	}

	/**
	 * Changes meaning of Xn button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfXn(String string) {
		JButton xn = (JButton)calcElements.get("x^n");
		xn.setText(string);
	}

	/**
	 * Changes meaning of Ln button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfLn(String string) {
		JButton ln = (JButton)calcElements.get("ln");
		ln.setText(string);
	}

	/**
	 * Changes meaning of Log button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfLog(String string) {
		JButton log = (JButton)calcElements.get("log");
		log.setText(string);		
	}

	/**
	 * Changes meaning of Ctg button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfCtg(String string) {
		JButton ctg = (JButton)calcElements.get("ctg");
		ctg.setText(string);		
	}

	/**
	 * Changes meaning of Tan button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfTan(String string) {
		JButton tan = (JButton)calcElements.get("tan");
		tan.setText(string);		
	}

	/**
	 * Changes meaning of Cos button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfCos(String string) {
		JButton cos = (JButton)calcElements.get("cos");
		cos.setText(string);		
	}

	/**
	 * Changes meaning of Sin button.
	 * @param string New String which is displayed on this specific button.
	 */
	private void changeMeaningOfSin(String string) {
		JButton sin = (JButton)calcElements.get("sin");
		sin.setText(string);		
	}

	

}
