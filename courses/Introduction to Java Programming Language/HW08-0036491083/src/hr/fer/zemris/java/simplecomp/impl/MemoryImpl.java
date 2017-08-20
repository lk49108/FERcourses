package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * This class implements {@link Memory} interface.
 * It represents computer's memory.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class MemoryImpl implements Memory{

	/**
	 * This instance variable holds number of memory addresses 
	 * in this {@link MemoryImpl} instance.
	 */
	int size;
	
	/**
	 * This instance variable holds reference to Object array 
	 * which is used as a memory in this {@link MemoryImpl} instance.
	 */
	Object[] memContainer;
	
	/**
	 * This constructor creates instance of this class and initializes
	 * instance variables.
	 * @param size Size of memory to which it will be set to.
	 */
	public MemoryImpl(int size){
		this.size = size;
		memContainer = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		if(location < 0 || location >= size){
			throw new IndexOutOfBoundsException("Location must be inside 0 - " 
					+ (this.size - 1) + ".");
		}
		memContainer[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		if(location < 0 || location >= size){
			throw new IndexOutOfBoundsException("Location must be inside 0 - " 
					+ (this.size - 1) + ".");
		}
		return memContainer[location];
	}
}
