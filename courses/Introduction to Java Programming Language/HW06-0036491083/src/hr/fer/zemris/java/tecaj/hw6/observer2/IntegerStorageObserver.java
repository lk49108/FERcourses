package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * This interface is used as Observer in
 * Observer pattern implementation.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface IntegerStorageObserver {
	/**
	 * This method is called if data stored in
	 * specific Subject has been changed.
	 * It prints out appropriate message to standard output.
	 * This message contains information about changes made to
	 * Subject.
	 * @param change IntegerStorageChange reference
	 * to which holds informations about change being made on Subject. 
	 */
	public void valueChanged(IntegerStorageChange change);
}
