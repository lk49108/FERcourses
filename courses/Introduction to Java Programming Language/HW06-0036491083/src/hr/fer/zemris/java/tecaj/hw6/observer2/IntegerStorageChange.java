package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class IntegerStorageChange {

	/**
	 * This read only property is a reference to 
	 * IntegerStorage instance, i.e. to Subject in 
	 * Observer pattern.
	 */
	private IntegerStorage istorage;
	
	/**
	 * This read only property holds 
	 * information what was value of integer before the change has occurred.
	 */
	private int valueBefChange;
	
	/**
	 * This read only property holds new value 
	 * of currently stored integer.
	 */
	private int newValue;

	/**
	 * This constructor initializes all properties contained in instance 
	 * of this class which constructor is creating.
	 * @param istorage Reference to a Subject on which change is
	 * being triggered.
	 * @param valueBefChange Value contained in Subject before change.
	 * @param newValue New value contained in Subject.
	 */
	public IntegerStorageChange(IntegerStorage istorage, int valueBefChange, int newValue) {
		super();
		this.istorage = istorage;
		this.valueBefChange = valueBefChange;
		this.newValue = newValue;
	}

	
	/**
	 * This is a getter for istorage property.
	 * @return the istorage property.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * This is a getter for valueBefChange property.
	 * @return the valueBefChange property.
	 */
	public int getValueBefChange() {
		return valueBefChange;
	}

	/**
	 * This is a getter for newValue property.
	 * @return the newValue property.
	 */
	public int getNewValue() {
		return newValue;
	}

		
	
}
