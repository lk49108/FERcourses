package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * This class inherits
 * IntegerStorageObserver interface and implements
 * all its only method. It prints out square value of
 * Integer currently being stored in a Subject.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class SquareValue implements IntegerStorageObserver{

	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Value changed from: " + change.getValueBefChange()
			+ " => " + change.getNewValue()  
			+ " square of new value is " + change.getNewValue() * change.getNewValue());
	}

}
