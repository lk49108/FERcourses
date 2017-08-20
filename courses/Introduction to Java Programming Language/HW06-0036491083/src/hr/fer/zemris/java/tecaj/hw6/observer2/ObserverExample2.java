package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class which contains main method 
 * which is used for testing implementation
 * of Observe pattern (number 2).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ObserverExample2 {

	/**
	 * This is the method from which program starts its execution.
	 * @param args Not used here.
	 */
	public static void main(String[] args) {
		
		List<IntegerStorageObserver> observers = new ArrayList<>(); 
		IntegerStorageObserver observer = new SquareValue();
		observers.add(observer);
		observers.add(new DoubleValue(3));
		observers.add(new DoubleValue(2));
		observers.add(new DoubleValue(1));
		observers.add(new ChangeCounter());
		observers.add(new DoubleValue(0));
		IntegerStorage istorage = new IntegerStorage(5, observers);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		istorage.setValue(73);
	}

}
