/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's testEquals instruction.
 * This method is used for checking if values stored
 * in specific registers is same. If statement is true, flag
 * (internal, i.e. situated in processor)
 * is set to hold value true.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {
	
	/**
	 * This instance variable holds index of first
	 * register taking part in this instruction. 
	 */
	private int indexReg1;
	
	/**
	 * This instance variable holds index of second
	 * register taking part in this instruction. 
	 */
	private int indexReg2;
	
	/**
	 * This constructor creates instance of CPU's jump
	 * instruction based on given argument List.
	 * @param arguments List of arguments describing this 
	 * instruction.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if(arguments.size() != 2){
			throw new IllegalArgumentException("Two arguments excpected.");
		}
		if(!arguments.get(0).isRegister()){
			throw new IllegalArgumentException("Type mismatch for argument one.");
		}
		if(!arguments.get(1).isRegister()){
			throw new IllegalArgumentException("Type mismatch for argument two.");
		}
		this.indexReg1 = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
		this.indexReg2 = RegisterUtil.getRegisterIndex((Integer)arguments.get(1).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		if((Integer)computer.getRegisters().getRegisterValue(indexReg1) 
				== (Integer)computer.getRegisters().getRegisterValue(indexReg2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}