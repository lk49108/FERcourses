package hr.fer.zemris.java.custom.scripting.collections;

/**
 * This class represents some general collection of objects. This class does not
 * have any storage capabilities. In this collection duplicate elements are
 * allowed and storage of null references is not allowed.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Collection {

	/**
	 * This method checks if collection is empty.
	 * 
	 * @return True if size of a collection is 0. Else, returns false.
	 */
	boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 * 
	 * @return 0.
	 */
	int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value
	 *            Object which is added into this collection.
	 */
	void add(Object value) {
	}

}
