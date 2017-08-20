package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * This is a class which contains main method 
 * which is used for testing implementation
 * of Observe pattern.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ObserverExample {

	/**
	 * This is the method from which program starts its execution.
	 * @param args Not used here.
	 */
	public static void main(String[] args) {
		//This example is a bit different from the one provided as an 
		//example.
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		
	}

}
