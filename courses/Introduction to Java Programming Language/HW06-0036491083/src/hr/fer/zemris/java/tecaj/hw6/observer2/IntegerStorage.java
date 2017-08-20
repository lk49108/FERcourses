package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents subject in 
 * Observer pattern. It is used for storing a single 
 * Integer value. It also stores list
 * of observers. Any change made on instance
 * of this class causes observers stored in internal list
 * to recognize this change and prints appropriate message to
 * standard output. All Observers added to this Subject should be added
 * as a list through the constructor. No additional changes, to internal 
 * Observer list, are allowed. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class IntegerStorage {
	
	/**
	 * This instance variable that holds Integer value of this Subject.
	 */
	private int value;
	
	
	/**
	 * This instance variable holds an array of observers which are observing
	 * instance of this class.
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * This instance variable is a reference to {@link IntegerStorageChange} 
	 * instance which holds data about last change made on
	 * this Subject.
	 */
	IntegerStorageChange change;
	
	/**
	 * This constructor initializes value instance variable.
	 * @param initialValue Integer to which value instance variable
	 * is assigned.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * This constructor initializes value instance variable and
	 * fills this Subject with observers. 
	 * @param initialValue Integer to which value instance variable
	 * is assigned.
	 * @param observers Observers being added to this Subject.
	 * Null is also allowed as this parameter(it means that zero observers will be 
	 * added to this Subject).
	 */
	public IntegerStorage(int initialValue, List<IntegerStorageObserver> observers) {
		this.value = initialValue;
		this.observers = observers;
	}
	
	//Add observers method is not allowed here. 
	
	/**
	 * This method is used for removing particular observer from this Subject.
	 * @param observer Reference to observer which is being removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		// remove the observer from observers if present ...
		if(observer == null){
			return;
		}
		if(observers == null) return;
		observers.remove(observer);
	}
	public void clearObservers() {
	// remove all observers from observers list ...
		observers = null;
	}
	public int getValue() {
	return value;
	}
	
	/**
	 * This method is used for setting new value to
	 * Integer stored in this Subject. It also informs all off the observers contained in local
	 * list about the change by making new instance of {@link IntegerStorageChange} class
	 * and sending it to all observers(i.e. its method valueChanged) currently being stored in internal list.
	 * @param value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			change = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				List<IntegerStorageObserver> helpList = new ArrayList<>(observers);
				for(IntegerStorageObserver observer : helpList) {
					observer.valueChanged(change);
				}
				System.out.println("***************************************************");
			}
		}
	}
}
