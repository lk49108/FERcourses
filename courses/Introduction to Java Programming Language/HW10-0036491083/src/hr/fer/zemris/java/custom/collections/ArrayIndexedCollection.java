package hr.fer.zemris.java.custom.collections;

/**
 * This class represents resizable array-backed collection of objects. It
 * extends class Collection. In this collection duplicate elements are allowed
 * and storage of null references is not allowed.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * This is instance variable which represents size of this collection, i.e.
	 * how many objects are currently being stored in it.
	 */
	private int size;

	/**
	 * This is instance variable showing capacity of this list., i.e. how many
	 * objects can be stored in it before further expansion is made.
	 */
	private int capacity;

	/**
	 * This instance variable is actually a referenc to array of objects, this
	 * is the place data in collection is actually stored.
	 */
	private Object[] elements;

	/**
	 * Constructor which creates an instance with initial capacity set by
	 * parameter.
	 * 
	 * @param initialCapacity
	 *            Initial capacity parameter.
	 */
	ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		capacity = initialCapacity;
		size = 0;
		elements = new Object[initialCapacity];
	}

	/**
	 * Constructor which creates an instance with initial capacity set to
	 * default value 16.
	 */
	ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Adds the given object into this collection at the end of this collection.
	 * Average complexity of this method is O(1).
	 * 
	 * @throws IllegalArgumentException
	 *             if null object is given as a parameter value.
	 */
	void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		} else {
			if (size == capacity) {
				Object[] transferor = new Object[2 * capacity];
				for (int i = 0; i < size; i++) {
					transferor[i] = elements[i];
				}
				transferor[size] = value;
				elements = transferor;
				capacity = 2 * capacity;
			} else {
				elements[size] = value;
			}
			size++;
		}
	}

	/**
	 * Returns the object that is stored in collection at position defined by
	 * parameter index. Average complexity of this method is O(1).
	 * 
	 * @param index
	 *            Index of object that is returned from collection. Legal values
	 *            of this parameter are from 0 to size of a collection -
	 *            1(included).
	 * @return Object that is stored in this collection at position index.
	 * @throws IndexOutOfBoundsException
	 *             if index value is not legal.
	 */
	Object get(int index) {
		if (!(index >= 0 && index <= size - 1)) {
			throw new IndexOutOfBoundsException();
		} else {
			return elements[index];
		}
	}

	void clear() {
		for (int i = 0; i < capacity; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Removes element at specified index from collection. Elements that were
	 * previously at location index + 1, index + 2 etc. are shifted one place
	 * toward the left.
	 * 
	 * @param index
	 *            Index of element that is removed from this collection. Legal
	 *            values if it are from 0 to size of a collection - 1
	 *            (included).
	 * @throws IllegalArgumentException
	 *             if index is not legal.
	 */
	void remove(int index) {
		if (!(index >= 0 && index <= size - 1)) {
			throw new IllegalArgumentException();
		} else {
			if (index != size - 1) {
				for (int i = index; i < size - 1; i++) {
					elements[i] = elements[i + 1];
				}
			}
			elements[size - 1] = new Object();
		}
		size--;
	}

	/**
	 * @return Returns Number of elements currently being placed in this
	 *         collection.
	 */
	int size() {
		return size;
	}

	/**
	 * @return Array of objects currently stored in this collection.
	 */
	Object[] toArray() {
		Object[] tank = new Object[size];
		for (int i = 0; i < size; i++) {
			tank[i] = elements[i];
		}
		return tank;
	}


}
