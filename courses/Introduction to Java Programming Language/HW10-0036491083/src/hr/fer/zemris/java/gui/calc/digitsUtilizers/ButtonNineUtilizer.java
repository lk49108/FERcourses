package hr.fer.zemris.java.gui.calc.digitsUtilizers;


import java.awt.Color;
import java.awt.Container;


import javax.swing.JButton;


import hr.fer.zemris.java.gui.calc.abstractUtilizers.DigitsUtilizerAbstract;

/**
 * This class implements {@link ContainerAdder} interface
 * and is used for adding {@link JButton} representing digit nine
 * to {@link Container} layout.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ButtonNineUtilizer extends  DigitsUtilizerAbstract {

	@Override
	public void add(Container cp, Color color) {
		JButton element = new JButton("9");
		element.setBackground(color);
				
		element.addActionListener(digitPressed);
		

		cp.add(element, "2,5");
	}

}