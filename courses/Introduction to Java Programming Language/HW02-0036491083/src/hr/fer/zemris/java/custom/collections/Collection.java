package hr.fer.zemris.java.custom.collections;

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

	/**
	 * Checks if the collection contains given value, as determined by equals
	 * method.
	 * 
	 * @param value
	 *            Object for which is checked if the collection contains it.
	 * @return Returns false.
	 */
	boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value, as determined
	 * by equals method and removes one occurence of it (it is not specified
	 * which one).
	 * 
	 * @param value
	 *            Object which is intended to to be deleted from this
	 *            collection.
	 * @return Always returns false.
	 */
	boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collection,
	 * fills it with collection content and returns the array.
	 * 
	 * @return Returns nothing, instead here it throws exception.
	 * @throw UnsupportedOperationException.
	 */
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process for each element of this collection. The
	 * order in which elements will be sent is undefined in this class.
	 * 
	 * @param processor
	 *            Parameter representing Processor type object.
	 */
	void forEach(Processor processor) {
	}

	/**
	 * Method adds into itself all elements from given collection. This other
	 * collection remains unchanged.
	 * 
	 * @param other
	 *            Given collection from which elements are added into this
	 *            collection.
	 */
	void addAll(Collection other) {
		class processor extends Processor {
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new processor());

	}

	/**
	 * Removes all elements from the collection.
	 */
	void clear() {
	}

}
