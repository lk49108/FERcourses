package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;


import javax.swing.JFrame;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.ClrUtilizer;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.EqualsUtilizer;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.InvUtilizer;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.PopUtilizer;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.PushUtilizer;
import hr.fer.zemris.java.gui.calc.additionalButtonsUtilizers.ResetUtilizer;
import hr.fer.zemris.java.gui.calc.basicOperationUtilizers.AddOperationUtilizer;

import hr.fer.zemris.java.gui.calc.basicOperationUtilizers.DivideOperationUtilizer;
import hr.fer.zemris.java.gui.calc.basicOperationUtilizers.MultiplyOperationUtilizer;
import hr.fer.zemris.java.gui.calc.basicOperationUtilizers.SubtractOperationUtilizer;
import hr.fer.zemris.java.gui.calc.basicOperationUtilizers.XnOperationUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonEightUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonFiveUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonFourUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonNineUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonOneUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonSevenUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonSixUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonThreeUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonTwoUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.ButtonZeroUtilizer;
import hr.fer.zemris.java.gui.calc.digitsUtilizers.PointUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.CosinusUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.CotangensUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.LnUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.LogUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.PlusMinusUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.SinusUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.TangensUtilizer;
import hr.fer.zemris.java.gui.calc.operationsUtilizer.ToMinusOneUtilizer;
import hr.fer.zemris.java.gui.calc.screenUtilizer.ScreenUtilizer;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * This class(program) displays (through) GUI calculator 
 * which user can use to calculate expressions.
 * User provides input by clicking with mouse
 * on button he wants. For example if user wants to 
 * calculate 15*3, he will click in 3,2,*,2,=. 
 * This calculator do not
 * provide any advanced features, e.g. if user enters
 * "3+2*5", expression is interpreted as "(3+2)*5".
 * Button "clr" erases only current number, e.g.
 * if user enters operations: 3,2,*,5,1,clr,2,=
 * calculator will calculate result equal to 64,
 * i.e. it will erase number 51 and multiply number 32
 * with 2. Button "res" resets calculator.
 * Button "push" pushes currently displayed number onto the stack.
 * Button "Pop" pops number from top of the stack and 
 * switches currently displayed number with the popped one.
 * Checkbox "inv" inverts meaning of operations, e.g. 
 * "log" into "10^", "sin" into "cos" etc. Numbers inputed into calculator
 * are treated as being of type double. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Calculator extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Vertical gap between button display used in rendering of calculator.
	 */
	private static final int VER_GAP = 3;
	
	/**
	 * Horizontal gap between button display used in rendering of calculator.
	 */
	private static final int HOR_GAP = 3;
	
	/**
	 * This static private variable holds color used in displaying {@link Calculator}
	 * screen.
	 */
	private static final Color SCREEN_COLOR = Color.orange;
	
	/**
	 * This static private variable holds color used in displaying {@link Calculator}
	 * buttons.
	 */
	private static final Color BUTTON_COLOR = Color.cyan;
	
	/**
	 * Public constructor used for creating instance of {@link Calculator}
	 * setting GUI's properties and initializing
	 * {@link calculatorComponents} instance variable.
	 */
	public Calculator(){
		super();
		setDefaultCloseOperation(
			WindowConstants.DISPOSE_ON_CLOSE		
			);
		setLocation(20, 20);
		setTitle("Calculator_1.0");
		setSize(500, 200);
				
		runCalculator();
	}
	
	
	
	/**
	 * Initializes GUI({@link Calculator}) and runs it.
	 */
	private void runCalculator() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(VER_GAP, HOR_GAP));
		
		addBasicOperations(cp);
		addAdvancedOperations(cp);
		addScreen(cp);
		addNumbers(cp);
		addAdditionalButtons(cp);
	}



	/**
	 * Adds additional buttons onto calculator and enables them for work.
	 * @param cp {@link Container} instance onto which buttons are added.
	 */
	private void addAdditionalButtons(Container cp) {
		new ClrUtilizer().add(cp, BUTTON_COLOR);
		new EqualsUtilizer().add(cp, BUTTON_COLOR);
		new InvUtilizer().add(cp, BUTTON_COLOR);
		new PopUtilizer().add(cp, BUTTON_COLOR);
		new PushUtilizer().add(cp, BUTTON_COLOR);
		new ResetUtilizer().add(cp, BUTTON_COLOR);
	}



	/**
	 * Adds basic operations buttons onto calculator and enables them for work.
	 * @param cp {@link Container} instance onto which buttons are added.
	 */
	private void addBasicOperations(Container cp) {
		new AddOperationUtilizer().add(cp, BUTTON_COLOR);
		new SubtractOperationUtilizer().add(cp, BUTTON_COLOR);
		new MultiplyOperationUtilizer().add(cp, BUTTON_COLOR);
		new DivideOperationUtilizer().add(cp, BUTTON_COLOR);
		new PlusMinusUtilizer().add(cp, BUTTON_COLOR);
	}

	/**
	 * Method used for adding numbers and "." buttons
	 * on this {@link Calculator} instance layout. This method also enables, through
	 * method add those buttons for work.
	 * @param cp {@link Container} instance, i.e. this {@link Calculator}.
	 */
	private void addNumbers(Container cp) {
		new ButtonOneUtilizer().add(cp, BUTTON_COLOR);
		new ButtonTwoUtilizer().add(cp, BUTTON_COLOR);
		new ButtonThreeUtilizer().add(cp, BUTTON_COLOR);
		new ButtonFourUtilizer().add(cp, BUTTON_COLOR);
		new ButtonFiveUtilizer().add(cp, BUTTON_COLOR);
		new ButtonSixUtilizer().add(cp, BUTTON_COLOR);
		new ButtonSevenUtilizer().add(cp, BUTTON_COLOR);
		new ButtonEightUtilizer().add(cp, BUTTON_COLOR);
		new ButtonNineUtilizer().add(cp, BUTTON_COLOR);
		new ButtonZeroUtilizer().add(cp, BUTTON_COLOR);
		new PointUtilizer().add(cp, BUTTON_COLOR);
	}



	/** Method used for adding advanced operation buttons like "tan", "cos", "sin" etc.
	 * on this {@link Calculator} instance  layout.
	 * @param cp {@link Container} instance, i.e. this {@link Calculator}.
	 */
	private void addAdvancedOperations(Container cp) {
		new XnOperationUtilizer().add(cp, BUTTON_COLOR);
		new CosinusUtilizer().add(cp, BUTTON_COLOR);
		new SinusUtilizer().add(cp, BUTTON_COLOR);
		new TangensUtilizer().add(cp, BUTTON_COLOR);
		new CotangensUtilizer().add(cp, BUTTON_COLOR);
		new LnUtilizer().add(cp, BUTTON_COLOR);
		new LogUtilizer().add(cp, BUTTON_COLOR);
		new ToMinusOneUtilizer().add(cp, BUTTON_COLOR);
	}



	/**
	 * Initializes {@link Calculator}'s screen, adds screen to {@link calculatorComponents} and 
	 * displays it.
	 * @param cp {@link Container} instance, i.e. this {@link Calculator}.
	 */
	private void addScreen(Container cp) {
		new ScreenUtilizer().add(cp, SCREEN_COLOR);
	}

	/**
	 * Main method from which program starts its execution.
	 * @param args Command-line arguments, not used in this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Calculator calc = new Calculator();
				calc.setVisible(true);
			}
			
		});
	}
}
