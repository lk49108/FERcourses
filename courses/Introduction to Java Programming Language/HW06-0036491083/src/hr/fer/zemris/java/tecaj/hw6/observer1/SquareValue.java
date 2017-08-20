package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * This class inherits
 * IntegerStorageObserver interface and implements
 * its only method. It is used for printing out 
 * square value of Integer contained in Subject this 
 * 
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class SquareValue implements IntegerStorageObserver{

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value: " + istorage.getValue() 
				+ " square is " + istorage.getValue() * istorage.getValue());
	}

}
