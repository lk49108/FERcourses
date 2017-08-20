package hr.fer.zemris.java.custom.collections;

/**
 * This class represents list-backed collection of objects. It extends class
 * Collection. In this collection duplicate elements are allowed and storage of
 * null references is not allowed.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * This is a private static class nested in LinkedListIndexedCollection
	 * class. It is used as a node of a list in list-backed implementation of a
	 * collection. It manages with three instance variables. Pointers to
	 * previous and next list node and additional reference for value storage.
	 * 
	 * @author Leonardo Kokot
	 */
	private static class ListNode {

		/**
		 * This is the reference to the next node of a list.
		 */
		ListNode next;

		/**
		 * This is the reference to the previous node of a list.
		 */
		ListNode previous;

		/**
		 * This is the reference to Object type object, it serves for storing
		 * actual data in list-backed class.
		 */
		Object element;
	}

	/**
	 * This instance variable represents the size of this collection, i.e. how
	 * many objects/elements are currently being stored in it.
	 */
	private int size;

	/**
	 * This instance variable is a reference to the first node of a list.
	 */
	private ListNode first;

	/**
	 * This instance variable is a reference to the last node of a list.
	 */
	private ListNode last;

	/**
	 * Constructor which creates an empty collection.
	 */
	LinkedListIndexedCollection() {
		first = last = null;
	}

	/**
	 * Constructor Which creates collection and also imports the content from
	 * another collection into this newly created collection.
	 * 
	 * @param old
	 *            Reference to other collection from which elements are
	 *            imported.
	 */
	LinkedListIndexedCollection(Collection old) {
		for (int i = 0; i < old.size(); i++) {
			add(old.toArray()[i]);
		}
	}

	/**
	 * Adds the given object into this collection at the end of this collection.
	 * 
	 * @throws IllegalArgumentException
	 *             if null object is given as a parameter value.
	 */
	void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		} else {
			if (first == null) {
				first = new ListNode();
				first.element = value;
				last = first;
			} else {
				last.next = new ListNode();
				last.next.previous = last;
				last = last.next;
				last.element = value;
			}
			size++;
		}
	}

	/**
	 * Returns the object that is stored in collection at position defined by
	 * parameter index.
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
			if (index >= size - index) {
				int traveler = size - index;
				ListNode help = last;
				while (traveler > 1) {
					help = help.previous;
					traveler--;
				}
				return help.element;
			} else {
				ListNode help = first;
				while (index > 0) {
					help = help.next;
					index--;
				}
				return help.element;
			}
		}
	}

	void clear() {
		first = null;
		last = null;
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
		if (!(position >= 0 && position <= size)) {
			throw new IllegalArgumentException();
		} else {
			if (position == 0) {
				ListNode help = new ListNode();
				help.element = value;
				help.next = first;
				help.previous = null;
				first = help;
			} else if (position == size) {
				add(value);
			} else {
				ListNode help = first;
				while (position > 0) {
					help = help.next;
					position--;
				}
				ListNode inserted = new ListNode();
				help.previous.next = inserted;
				inserted.previous = help.previous;
				inserted.next = help;
				help.previous = inserted;
				inserted.element = value;
			}
			size++;
		}
	}

	/**
	 * Returns the index of the first occurrence of the given value in this
	 * collection. If there is no such an element in this collection method
	 * returns -1 instead. Equality is determined using the equals method.
	 * Average complexity of this method is O(n).
	 * 
	 * @param value
	 *            Value that is being searched in this collection.
	 * @return Index at which is given value situated in this collection.
	 */
	int indexOf(Object value) {
		if (size == 0) {
			throw new IllegalAccessError("No items in list.");
		} else {
			ListNode searcher = first;
			int index = 0;
			while (!(searcher.element.equals(last.element))) {
				if (searcher.element.equals(value)) {
					return index;
				}
				index++;
				searcher = searcher.next;
			}
			if (searcher.element.equals(value)) {
				return index;
			}
			return -1;
		}
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
			if (index == 0) {
				first = first.next;
				first.previous = null;
			} else if (index == size - 1) {
				last = last.previous;
				last.next = null;
			} else {
				ListNode searcher = first;
				while (index > 0) {
					searcher = searcher.next;
					index--;
				}
				searcher.previous.next = searcher.next;
				searcher.next.previous = searcher.previous;
			}
			size--;
		}
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
		ListNode searcher = first;
		while (searcher != null) {
			if (searcher.element.equals(value)) {
				if (searcher == first) {
					first = first.next;
				}
				searcher.previous.next = searcher.next;
				searcher.next.previous = searcher.previous;
				size--;
				return true;
			}
			searcher = searcher.next;
		}
		return false;
	}

	/**
	 * @return Size of a collection.
	 */
	int size() {
		return size;
	}

	/**
	 * @return True if value is contained in this collection, otherwise returns
	 *         false.
	 */
	boolean contains(Object value) {
		if (this.indexOf(value) != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return Array of objects currently stored in this collection.
	 */
	Object[] toArray() {
		Object[] tank = new Object[size];
		ListNode searcher = first;
		for (int i = 0; i < size; i++) {
			tank[i] = searcher.element;
			searcher = searcher.next;
		}
		return tank;
	}

	/**
	 * Method calls processor.process for each element of this collection.
	 */
	void forEach(Processor processor) {
		ListNode walker = first;
		for (int i = 0; i < size; i++) {
			processor.process(walker.element);
			walker = walker.next;
		}
	}

}
