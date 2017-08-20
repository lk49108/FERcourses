package hr.fer.zemris.java.gui.calc.screenUtilizer;


import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.abstractUtilizers.CalcUtilizerAbstract;


/**
 * Class which initializes screen of {@link Calculator}.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class ScreenUtilizer extends CalcUtilizerAbstract {

	
	/**
	 * This static private variable holds message which will be displayed on calculator
	 * initially, when {@link Calculator} program will start.
	 */
	private static final String HELLO_MESSAGE = "Welcome to Calculator_1.0";
	
	/**
	 * This static private variable holds alignment of text 
	 * which is displayed on {@link Calculator} instance
	 * screen.
	 */
	private final static int ALINGMENT_OF_TEXT_ON_SCREEN = SwingConstants.RIGHT; 
	
	/**
	 * This static private variable holds {@link Calculator} screen border width.
	 */
	private final static int SCREEN_BORDER = 5;
	

	@Override
	public void add(Container cp, Color color) {
		JLabel screen = new JLabel(HELLO_MESSAGE);
		screen.setBackground(color);
		screen.setOpaque(true);
		screen.setHorizontalAlignment(ALINGMENT_OF_TEXT_ON_SCREEN);
		screen.setBorder(BorderFactory.createLineBorder(color, SCREEN_BORDER));
		
		CalcUtilizerAbstract.screen = screen;

		cp.add(screen, "1,1");	
		
	}

}
