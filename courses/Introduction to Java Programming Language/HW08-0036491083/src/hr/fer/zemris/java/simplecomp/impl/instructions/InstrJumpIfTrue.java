/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's jumpIf instruction.
 * This method is used for setting value stored in PC 
 * to specific value, but only if specific condition is 
 * fulfilled.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrJumpIfTrue implements Instruction {

	/**
	 * This instance variable holds value 
	 * to which PC will be changed.
	 */
	private int value;
	
	/**
	 * This constructor creates instance of CPU's jumpIf
	 * instruction based on given argument List.
	 * @param arguments List of arguments describing this 
	 * instruction.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if(arguments.size() != 1){
			throw new IllegalArgumentException("One argument excpected.");
		}
		if(!arguments.get(0).isNumber()){
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		this.value = (Integer)arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		if(computer.getRegisters().getFlag() == true){
			computer.getRegisters().setProgramCounter(value);
		}
		return false;
	}


}
