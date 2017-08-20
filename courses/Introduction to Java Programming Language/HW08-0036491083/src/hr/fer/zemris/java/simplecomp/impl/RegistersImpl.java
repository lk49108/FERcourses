package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class implements {@link Registers}.
 * It is used for representing computer registers.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class RegistersImpl implements Registers{

	/**
	 * This instance variable holds number of memory addresses 
	 * in this {@link RegistersImpl} instance.
	 */
	int size;
	
	/**
	 * This instance variable holds reference to Object array 
	 * which is used as registers in this {@link RegistersImpl} instance.
	 */
	Object[] regContainer;
	
	/**
	 * This instance variable holds number stored in program counter
	 * register, it is initially set to zero.
	 */
	private int programCounter;

	/**
	 * This instance variable represents data stored in CPUs
	 * internal flag.
	 */
	private boolean flag;
	
	/**
	 * This instance variable holds information what is the minimum number of 
	 * registers predescribed.
	 */
	private static final int MIMIMUMREG = 16;
	
	/**
	 * This constructor creates instance of {@link RegistersImpl}
	 * and initializes instance variables.
	 * @param size Number of registers which will be hold in CPU.
	 * @throws IllegalArgumentException if regsLen is less than 16.
	 */
	public RegistersImpl(int regsLen) {
		if(regsLen < 16){
			throw new IllegalArgumentException("Minimum number of registers should be: " 
					+ RegistersImpl.MIMIMUMREG + ".");
		}
		this.size = regsLen;
		regContainer = new Object[size];
	}
	
	@Override
	public Object getRegisterValue(int index) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Index must be inside 0 - " 
					+ (this.size - 1) + ".");
		}
		return regContainer[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Index must be inside 0 - " 
					+ (this.size - 1) + ".");
		}
		regContainer[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		this.programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		this.programCounter++;
	}

	@Override
	public boolean getFlag() {
		return this.flag;
	}

	@Override
	public void setFlag(boolean value) {
		this.flag = value;
	}
	
	
}
