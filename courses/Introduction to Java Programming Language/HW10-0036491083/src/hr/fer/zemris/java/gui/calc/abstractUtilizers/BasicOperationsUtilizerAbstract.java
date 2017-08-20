/**
 * 
 */
package hr.fer.zemris.java.gui.calc.abstractUtilizers;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.strategies.BasicOperations;



/**
 * This abstract class is used as a root 
 * class for all classes which utilize
 * each basic operation button placed on {@link Calculator}. This class also defines one instance variable 
 * {@link #basicOperationPressed} {@link ActionListener} instance which is added
 * as a listener to every {@link JButton} instance representing specific basic operation 
 * of {@link Calculator}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class BasicOperationsUtilizerAbstract extends CalcUtilizerAbstract implements BasicOperations{
	
	/**
	 * Action listener for basic operations.
	 */
	protected ActionListener operationPressed = a ->  {
		try{
			String numOnScreen = screen.getText();
			double numberOnScreen = Double.parseDouble(numOnScreen);
			if(basicOperationLastPressed == null){
				numberStored = numberOnScreen;
				basicOperationLastPressed = this;
				resetScreenOnNextInput = true;

			} else {
				double result = basicOperationLastPressed.doOperation(numberStored, numberOnScreen);
				basicOperationLastPressed = this;
				resetScreenOnNextInput = true;
				numberStored = result;
				screen.setText(Double.toString(result));
			}
		} catch (Exception ex){
			resetCalcNoStackClear();
		}
			
	};
}
