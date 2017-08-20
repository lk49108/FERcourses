package hr.fer.zemris.java.tecaj.hw6.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents subject in 
 * Observer pattern. It is used for storing a single 
 * int value. It also stores list
 * of observers. Any change made on instance
 * of this class causes observers stored in internal list
 * to recognize(Subject notifies them about change) this change and prints appropriate message to
 * standard output.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class IntegerStorage {
	
	/**
	 * This instance variable holds Integer value contained in this Subject.
	 */
	private int value;
	
	/**
	 * This instance variable holds an array of observers which are currently observing
	 * instance of this class.
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * This constructor initializes value instance variable.
	 * @param initialValue Integer to which value instance variable
	 * is assigned.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * This method adds observer to this
	 * Subject.
	 * @param observer Reference to observer which is currently being added to this 
	 * Subject.
	 * @throws IllegalArgumentException if given value is null.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		// add the observer in observers if not already there ...
		if(observer == null){
			throw new IllegalArgumentException("Observer given as argument to method "
					+ "addObserver must not be null.");
		}
		if(observers == null){
			observers = new ArrayList<>();
		}
		for(IntegerStorageObserver temp : observers){
			if(temp.equals(observer)){
				System.out.println("Such observer already exist in internal list of observers.");
				return;
			}
		}
		observers.add(observer);
	}
	
	/**
	 * This method is used for removing particular observer from this Subject.
	 * @param observer Reference to observer which is being removed. If null is provided
	 * as parameter, return statement is called.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		// remove the observer from observers if present ...
		if(observer == null){
			return;
		}
		if(observers == null) return;
		observers.remove(observer);
	}
	
	/**
	 * This method is used for clearing all observers
	 * currently contained in this Subject.
	 */
	public void clearObservers() {
	// remove all observers from observers list ...
		observers = null;
	}
	
	/**
	 * This method is used for obtaining Integer value being
	 * contained in this Subject.
	 * @return value instance variable.
	 */
	public int getValue() {
	return value;
	}
	
	/**
	 * This method is used for setting new Integer 
	 * value stored in this Subject, it also informs all off the observers, contained in local
	 * list of observers, about the change.
	 * @param value New Integer that is stored in this Subject.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				List<IntegerStorageObserver> helpList = new ArrayList<>(observers);
				for(IntegerStorageObserver observer : helpList) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
