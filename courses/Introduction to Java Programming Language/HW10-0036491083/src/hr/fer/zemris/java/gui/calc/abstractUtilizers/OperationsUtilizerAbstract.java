package hr.fer.zemris.java.gui.calc.abstractUtilizers;

import java.awt.event.ActionListener;


import hr.fer.zemris.java.gui.calc.strategies.Operations;

/**
 * Abstract class used by specific operations on this calculator.
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class OperationsUtilizerAbstract extends CalcUtilizerAbstract implements Operations{

	/**
	 * Action listener for operations on calculator which extends this class.
	 */
	protected ActionListener operationPressed = a ->  {
		String numOnScreen = screen.getText();
		try{
			double numberOnScreen = Double.parseDouble(numOnScreen);
			double result = this.calcFunction(numberOnScreen);
			numberStored = result;
			screen.setText(Double.toString(result));
		} catch(Exception ex){
			screen.setText("Syntax error.");
			resetCalcNoStackClear();
		}
	};

}
