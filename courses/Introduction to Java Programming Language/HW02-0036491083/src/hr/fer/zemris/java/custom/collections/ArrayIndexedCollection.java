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
	 * Constructor which creates an instance with initial capacity set by
	 * parameter initialCapacity. It also copies elements from other collection
	 * given by parameter old. If initialCapacity parameter is smaller than size
	 * of Collection old, Constructor automatically sets initial capacity to
	 * equals size of Collection old.
	 * 
	 * @param old
	 *            Collection from which elements are copied into this
	 *            collection.
	 * @param initialCapacity
	 *            Initial capacity of this collection.
	 */
	ArrayIndexedCollection(Collection old, int initialCapacity) {
		if (initialCapacity < old.size()) {
			initialCapacity = old.size();
		}
		elements = new Object[initialCapacity];
		for (int i = 0; i < initialCapacity; i++) {
			elements[i] = old.toArray()[i];
		}
		capacity = initialCapacity;
		size = old.size();
	}

	/**
	 * Constructor which creates an instance with initial capacity set to size
	 * of a collection it gets its elements from. It also copies elements from
	 * this other collection given by parameter old.
	 * 
	 * @param old
	 *            Collection from which elements are imported into this
	 *            collection.
	 */
	ArrayIndexedCollection(Collection old) {
		this(old, old.size());
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
		elements = new Object[capacity];
		size = 0;
	}

	/**
	 * Inserts the given value at the given position in collection. Elements(if
	 * there is any of them) at position defined by parameter position and
	 * greater positions are shifted one place toward the end. Average
	 * complexity of this method is O(n).
	 * 
	 * @param value
	 *            Value that is inserted into this collection.
	 * @param position
	 *            Position into which given value is inserted. Legal values of
	 *            this parameter are from 0 to size of a collection (included).
	 * @throws IllegalArgumentException
	 *             if position is not legal.
	 */
	void insert(Object value, int position) {
		if (value == null || !(position <= size && position >= 0)) {
			throw new IllegalArgumentException();
		} else {
			if (size == capacity) {
				Object[] transferor = new Object[2 * capacity];
				for (int i = 0; i < size; i++) {
					transferor[i] = elements[i];
				}
				elements = transferor;
				capacity = capacity * 2;
			}
			if (position == size) {
				elements[position] = value;
				size++;
			} else {
				for (int i = size; i > position; i--) {
					elements[i] = elements[i - 1];
				}
				elements[position] = value;
				size++;
			}
		}
	}

	/**
	 * Returns the index of the first occurence of the given value in this
	 * collection. If there is no such an element in this collection method
	 * returns -1 instead. Equality is determined using the equals method.
	 * Average complexity of this method is O(n).
	 * 
	 * @param value
	 *            Value that is being searched in this collection.
	 * @return Index at which is given value situated in this collection.
	 */
	int indexOf(Object value) {
		for (int position = 0; position < size; position++) {
			if (elements[position].equals(value)) {
				return position;
			}
		}
		return -1;
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
	 * Returns true only if the collection contains given value, as determined
	 * by equals method and removes one occurrence of it (the first one).
	 * 
	 * @param value
	 *            Object which is intended to to be deleted from this
	 *            collection.
	 * @return True if this collection contained value before the removing has
	 *         been done. Returns false otherwise.
	 */
	boolean remove(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				for (int movingLeft = i; movingLeft < size - 1; movingLeft++) {
					elements[movingLeft] = elements[movingLeft + 1];
				}
				elements[size - 1] = new Object();
				size--;
				return true;
			}
		}
		return false;
	}

	/**
	 * @return True if this collection contains given value. Otherwise it
	 *         returns false.
	 */
	boolean contains(Object value) {
		if (this.indexOf(value) != -1) {
			return true;
		} else {
			return false;
		}
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

	/**
	 * Method calls processor.process for each element of this collection.
	 */
	void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

}
