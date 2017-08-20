/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * This class inherits
 * IntegerStorageObserver interface and implements
 * its only method. It is used for printing out
 * double value of Integer contained in Subject, but
 * limited number of times only.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class DoubleValue implements IntegerStorageObserver{
	
	// OK,  now I saw that it is not mandatory for me to care
	// about possibility of two Subjects sharing same Observer etc. so I
	// will not pay attention on that in this class.
	/**
	 * This instance variable holds information about 
	 * number of changes on Subject that will be tracked with 
	 * this instance of {@link DoubleValue} class.
	 */
	int numberOfRemaining;

	/**
	 * This constructor initializes instance variable number to
	 * equal parameter given to this constructor.
	 * @param numberOfTrackings Number of tracking that will be
	 * made on specific Subject.  
	 * @throws IllegalArgumentException if value provided is less than a zero
	 */
	public DoubleValue(int numberOfTrackings){
		if(numberOfTrackings < 0){
			throw new IllegalArgumentException("Value provided: " + numberOfTrackings + " must be greater or"
					+ " equal to zero.");
		}
		this.numberOfRemaining = numberOfTrackings;
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(numberOfRemaining > 0){
			System.out.println("Double value: " + istorage.getValue() * 2);
		}
		numberOfRemaining--;
		if(numberOfRemaining == 0){
			istorage.removeObserver(this);
		}
	}
	
	
}
