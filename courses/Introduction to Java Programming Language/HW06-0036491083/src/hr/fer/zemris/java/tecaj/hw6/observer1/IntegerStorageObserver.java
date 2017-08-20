package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * This interface is used as Observer in
 * Observer pattern implementation.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface IntegerStorageObserver {
	/**
	 * This method is called if data stored in
	 * specific subject has been changed.
	 * It prints out appropriate message to standard output.
	 * This message contains information about changes made to
	 * Subject.
	 * @param istorage IntegerStorage reference, this parameter is
	 * reference to Subject on which change has been triggered. 
	 */
	public void valueChanged(IntegerStorage istorage);
}
