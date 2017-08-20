package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents table hash table.
 * It provides ability to store (key, value) pairs in it.
 * Key values cannot be null references, while value
 * can be a null reference.
 * @author Leonardo Kokot
 * @version 1.0
 * @param <K> Type of key that is stored in this table.
 * @param <V> Type of value stored in this table.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	/**
	 * Array representing table slots.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Instance variable representing how slots 
	 * this table contains.
	 */
	private int size;
	
	
	/**
	 * This private instance variable is used 
	 * for retaining number of how many modifications have been 
	 * done on this table. Initially it is set by default to 0. 
	 */
	private int modificationCount;
	
	/**
	 * Default constructor which creates table with 16 slots, i.e. it creates an TableEntry type array with
	 * a size of 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(){
		size = 16;
		table = (TableEntry<K, V>[])new TableEntry[size];
	}
	
	/**
	 * This constructor accepts (wanted) initial number of slots in a table.
	 * This constructor then creates table with number of slots equal to
	 * size = 2^n where n smallest possible is such that size is equal or 
	 * larger than wanted capacity. 
	 * @param capacity int type parameter representing minimum wanted number of slots in table.
	 * @throws IllegalArgumentException if capacity is smaller than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity){
		if(capacity < 1){
			throw new IllegalArgumentException("capacity parameter must be larger than 1");
		}
		size = (int) Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2)));
		table = (TableEntry<K, V>[])new TableEntry[size];
	}
	
	/**
	 * This method is used for storing (key, value) pair
	 * in appropriate place in this table. 
	 * If key provided is already contained in this table,
	 * its value is updated to so that it equals value parameter provided in a 
	 * call of this method. If key provided is not contained in table, pair(key, value)
	 * is placed at the end of appropriate slot in a table.
	 * @param key Key that is being stored in a table in a pair with its corresponding value.
	 * @param value Value that is being stored in a table at appropriate place.
	 * @throws IllegalArgumentException if key providedis null reference.
	 */
	public void put(K key, V value){
		if(key == null){
			throw new IllegalArgumentException("key must not be a null reference.");
		}
		if(this.size() >= 0.75 * this.size){
			reallocateTable();
		}
		int slot = slotCalculator(key);
		if(table[slot] == null){
			table[slot]= new TableEntry<>(key, value);
		}
		else{
			TableEntry<K, V> helper = table[slot];
			//Checks if a given key is already contained in a table.
			if(helper.getKey().equals(key)){
				helper.setValue(value);
				return;
			}
			while(helper.next != null){
				//Checks if a given key is already contained in a table.
				if(helper.getKey().equals(key)){
					helper.setValue(value);
					return;
				}
				helper = helper.next;
			}
			helper.next = new TableEntry<>(key, value);
		}
		modificationCount++;
	}
	
	/**
	 * This method checks if a given key exist in a table, if it
	 * does, then it returns its corresponding value.
	 * If such key do not exist in table null is returned.
	 * If key is null reference null is returned.
	 * @param key Key for which is being checked if it exist in table
	 * and for which appropriate value is returned.
	 * @return Appropriate value.
	 */
	public V get(Object key){
		if(key == null){
			return null;
		}
		int slot = slotCalculator(key);
		TableEntry<K, V> helper = table[slot];
		while(helper != null){
			if(helper.getKey().equals(key)){
				return helper.getValue();
			}
		}
		return null;
	}
	
	/**
	 * This method deletes pair(key, value) if such
	 * exist. Otherwise, it does nothing.
	 * If null is provided as key, method does nothing
	 * as null key does not exist.
	 * @param key Key for which is being checked if such exist 
	 * in a table (and its corresponding value). 
	 */
	public void remove(Object key){
		if(key == null){
			return;
		}
		int slot = slotCalculator(key);
		TableEntry<K, V> helper = table[slot];
		if(helper == null){
			return;
		}
		TableEntry<K, V> beforeHelper = null;
		do{
			if(helper.getKey().equals(key)){
				break;
			}
			beforeHelper = helper;
			helper = helper.next;
		} while (helper != null);
		if(beforeHelper == null){
			table[slot] = helper.next;
		}
		else if(helper == null){
			return;
		}
		else{
			beforeHelper.next = helper.next;
		}
		modificationCount++;
	}
	
	/**
	 * This method checks if this table is empty, i.e. it 
	 * does not contain any (key, value) pair.
	 * @return True if it is empty, false otherwise.
	 */
	public boolean isEmpty(){
		for(int i = 0; i < size; i++){
			if(table[i] != null){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method returns number of pairs (key, value) contained in this table.
	 * @return number of pairs (key, value) contained in this table.
	 */
	public int size(){
		int sizeNumber = 0;
		for(int i = 0; i < size; i++){
			if(table[i] != null){
				TableEntry<K, V> helper = table[i];
				while(helper != null){
					sizeNumber++;
					helper = helper.next;
				}
			}
		}
		return sizeNumber;
	}
	
	/**
	 * This method checks if given key is contained in this table.
	 * @param key Key for which is being checked if it is contained in this table.
	 * @return True if given key is contained in this table.
	 * Otherwise, false is returned.
	 */
	public boolean containsKey(Object key){
		if(key == null){
			return false;
		}
		int slot = slotCalculator(key);
		TableEntry<K, V> helper = table[slot];
		if(helper == null){
			return false;
		}
		while(helper != null){
			if(helper.getKey().equals(key)){
				return true;
			}
			helper = helper.next;
		}
		return false;
	}
	
	/**
	 * This method is used for checking if given value is contained 
	 * in this table. Null value can be given as parameter in this method as
	 * null value is allowed to be contained in this table as value in (key, value) pair.
	 * @param value Value for which is being checked if it is contained in this table.
	 * @return True if given value is contained in this table, false otherwise.
	 */
	public boolean containsValue(Object value){
		TableEntry<K, V> helper;
		for(int i = 0; i < size; i++){
			helper = table[i];
			if(helper == null){
				continue;
			}
			while(helper != null){
				if(helper.getValue().equals(value)){
					return true;
				}
				helper = helper.next;
			}
		}
		return false;
	}
	
	@Override
	public String toString(){
		StringBuilder returnValue = new StringBuilder().append("[");
		TableEntry<K, V> helper;
		for(int i = 0; i < size; i++){
			if(table[i] == null){
				continue;
			}
			helper = table[i];
			while(helper != null){
				returnValue = returnValue.append(helper.toString() + ", ");
				helper = helper.next;
			}
			}
		//Deleting lastly added comma and space character and adding "]" at 
		//the end of a string contained in returnValue instance of StringBuilder class.
		return returnValue.delete(returnValue.length() - 2, returnValue.length()).append("]").toString();
	}
	
	/**
	 * This is a private method which calculates 
	 * slot in which a value with a given key will be placed.
	 * Expression is as follows: abs(hashCode(k)) % size.abs stands for 
	 * absolute value.
	 * @param key Key which is being placed in table as a part of
	 *(key, value) pair.
	 * @return Ordinary number of slot in which will pair (key, value)
	 * be stored.
	 */
	private int slotCalculator(Object key){
		return Math.abs(key.hashCode()) % size;
	}
	
	
	/**
	 * This method is used for reallocation of table when 
	 * items contained in this table exceeds 75% of number of slots 
	 * currently being handled by table. New, reallocated table has double
	 * number of slots than previous one.
	 */
	@SuppressWarnings("unchecked")
	private void reallocateTable(){
		size = size * 2;
		TableEntry<K, V>[] helpTable = table;
		table = (TableEntry<K, V>[]) new TableEntry[size];
		for(int i = 0; i < size / 2; i++){
			if(helpTable[i] == null){
				continue;
			}
			TableEntry<K, V> helper = helpTable[i];
			while(helper != null){
				K keyHelp = helper.getKey();
				put(keyHelp, helper.getValue());
				helper = helper.next;
			}
		}
	}
	
	/**
	 * This method is used for clearing whole table,i.e.
	 * it erases every (key, value) pair contained in table.
	 */
	public void clear(){
		for(int i = 0; i < size; i++){
			table[i] = null;
		}
		modificationCount++;
	}
	
	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator(){
		return new IteratorImpl();
	}
	
	/**
	 * This class implements Iterator interface.
	 * It creates an iterator which is used for iterating
	 * through this table content.
	 * @author Leonardo Kokot
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>>{
		
		/**
		 * This instance variable is used for 
		 * storing current value of <code>modificationCount </code> instance variable
		 * of SimpleHashtable instance that creates this iterator.
		 * It is used for knowing if any structural changes have been 
		 * done in table since creation of this iterator.
		 */
		int modificationAtCreation;
		
		/**
		 * This instance variable is used for remembering in which slot of table is
		 * this iterator currently located.
		 */
		int currentSlot = 0;
		
		TableEntry<K, V> currentPair;
		
		/**
		 * This instance variable holds table's (key, value) pair 
		 * that was processed before currentPair.
		 */
		TableEntry<K, V> previousPair;
		
		/**
		 * This instance variable holds informations if 
		 * remove method was already called after last next method
		 * was called.
		 */
		boolean removeAfterLastNext;
		
		/**
		 * This is a default constructor which creates an instance of this class.
		 * It sets modificationAtCreation to current number of modifications made to table.
		 */
		public IteratorImpl(){
			modificationAtCreation = modificationCount;
		}
		
		@Override
		public boolean hasNext(){
			if(modificationAtCreation != modificationCount){
				throw new ConcurrentModificationException("Table has been modified outside of");
			}
			if(currentPair == null && isEmpty()){
				return false;
			}
			if(currentPair == null && !isEmpty()){
				return true;
			}
			if(currentPair.next != null){
				return true;
			}
			for(int i = currentSlot + 1; i < size; i++){
				if(table[i] != null){
					return true;
				}		
			}
			return false;
		}
		
		@Override
		public SimpleHashtable.TableEntry<K, V> next(){
			if(modificationAtCreation != modificationCount){
				throw new ConcurrentModificationException("Table has been modified outside of");
			}
			if(!hasNext()){
				throw new NoSuchElementException("No more elements.");
			}
			if(currentPair == null){
				for(int i = 0; i < size; i++){
					if(table[i] != null){
						currentSlot = i;
						currentPair = table[i];
						removeAfterLastNext = false;
						return currentPair;
					}		
				}
			}
			if(currentPair.next != null){
				previousPair = currentPair;
				currentPair = currentPair.next;
				removeAfterLastNext = false;
				return currentPair;
			}
			for(int i = currentSlot + 1; i < size; i++){
				if(table[i] != null){
					currentSlot = i;
					previousPair = currentPair;
					currentPair = table[i];
				}		
			}
			removeAfterLastNext = false;
			return currentPair;
		}
		
		@Override
		public void remove(){
			if(modificationAtCreation != modificationCount){
				throw new ConcurrentModificationException("Table has been modified outside of");
			}
			if(removeAfterLastNext){
				throw new IllegalStateException("Two removes cannot be called if there next method"
						+ "is not called in a meantime");
			}
			if(currentPair == null){
				throw new IllegalStateException("Remove method must not be called if next method was not called before it.");
			}
			SimpleHashtable.this.remove(currentPair.key);
			modificationCount--;
			removeAfterLastNext = true;
			currentPair = previousPair;
		}
	}
	
	/**
	 * Nested class in <code>SimpleHashtable</code> class.
	 * It is used for saving pairs (key, value) in it.
	 * Instance of this class is capable of storing one 
	 * above mentioned pair. This class also contains a reference to another 
	 * <code>TableEntry</code> instance. This allows us to 
	 * build a single linked list, with a nodes constructed 
	 * by this class, in every slot of a table.
	 * @author Leonardo Kokot
	 * @version 1.0
	 * @param <K> Type of key stored in instance of this class.
	 * @param <V> Type of value stored in instance of this class.
	 */
	public static class TableEntry<K, V>{
		
		/**
		 * This instance variable is used for storing key. 
		 */
		private final K key;
		
		/**
		 * This instance variable is used for storing value associated with a given key.
		 */
		private V value;
		
		/**
		 * This instance variable is a actually reference to another node in a single linked list being placed in
		 * a particular slot of table.
		 */
		private TableEntry<K, V> next;

		/**
		 * This constructor creates an instance of this class.
		 * It also initializes values of key and value instance variable.
		 * @param key Type of key that is stored in newly created instance of this class.
		 * @param value Type of value that is stored in newly created instance of this class.
		 */
		TableEntry(K key, V value){
			this.key = key;
			this.value = value;
		}
		
		/**
		 * This is a getter for key instance variable.
		 * @return key instance variable.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * This is a getter for value instance variable.
		 * @return value instance variable.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * This is a setter for value instance variable.
		 * @param value Value to which instance variable value is being set.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString(){
			return key.toString() + "=" + value.toString();
		}
		
	}
}
