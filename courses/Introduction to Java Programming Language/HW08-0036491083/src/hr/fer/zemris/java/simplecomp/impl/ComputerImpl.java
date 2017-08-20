package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class implements {@link Computer} interface.
 * It represents computer on which programs are executed.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class ComputerImpl implements Computer{

	
	/**
	 * This instance variable holds reference to memory of this computerImpl.
	 */
	Memory memory;
	
	/**
	 * This instance variable holds reference to CPU registers of this 
	 * computerImpl.
	 */
	Registers register;
	
	/**
	 * This constructor creates computer with specific memory size and register number.
	 * @param memorySize Memory size.
	 * @param registerLength Register number.
	 */
	public ComputerImpl(int memorySize, int registerLength) {
		memory = new MemoryImpl(memorySize);
		register = new RegistersImpl(registerLength);
	}

	@Override
	public Registers getRegisters() {
		return register;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}
	
	
}
