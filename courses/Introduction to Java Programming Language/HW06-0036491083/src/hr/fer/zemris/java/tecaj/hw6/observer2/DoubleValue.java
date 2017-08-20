/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * This class inherits
 * IntegerStorageObserver interface and implements
 * its only method. It prints out double value of integer value
 * currently being contained in a Subject (but for a limited number of times time only).
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class DoubleValue implements IntegerStorageObserver{
	
	//OK, now I read that observers objects will not be reused :D so...:D
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
	public void valueChanged(IntegerStorageChange change) {
		if(numberOfRemaining > 0){
			System.out.println("Old double value: " + change.getValueBefChange() * 2 + ". New double value " + change.getNewValue() * 2);
		}
		numberOfRemaining--;
		if(numberOfRemaining == 0){
			change.getIstorage().removeObserver(this);
		}
	}
	
	
}
