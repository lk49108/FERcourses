package hr.fer.zemris.java.custom.collections;

/**
 * This is class that contains stack-like collection. 
 * It contains methods that are suitable for stack, like 
 * push, pop and peek. This class provides only one 
 * (default) constructor.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ObjectStack {
	
	/**
	 * This is the only instance variable of this stack-like collection.
	 * It is actually a reference to the ArrayIndexedCollection object.
	 * It's used for storing objects.
	 */
	ArrayIndexedCollection storage = new ArrayIndexedCollection();
	
	/**
	 * This method checks if this stack-like collection is empty.
	 * @return True if it is empty, otherwise it returns false.
	 */
	public boolean isEmpty(){
		if(this.size() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * This method returns size of a stack-like collection.
	 * @return Size of this stack-like collection.
	 */
	public int size(){
		return storage.size();
	}
	
	/**
	 * Pushes an object on top of the stack.
	 * @param value Object that is pushed onto this stack.
	 * @throws IllegalArgumentException if null is provided
	 * as value.
	 */
	public void push(Object value){
		if(value == null){
			throw new IllegalArgumentException("Null object is not allowed to be added onto stack");
		}
		else{
			storage.add(value);
		}
	}
	
	/**
	 * Pops(takes away) an object from top of the stack.
	 * @return Returns an element(object) that is taken from top
	 * of the stack.
	 */
	public Object pop(){
			Object returnObject = storage.get(this.size() - 1);
			storage.remove(this.size() - 1);
			return returnObject;
	}
	
	/**
	 * Erases elements from the stack.
	 */
	public void clear(){
		storage.clear();
	}
	
}
