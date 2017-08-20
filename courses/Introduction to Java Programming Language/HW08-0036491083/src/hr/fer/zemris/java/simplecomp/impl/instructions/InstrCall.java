package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's  
 * call instruction. It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class InstrCall implements Instruction {

	
	/**
	 * This instance variable holds index of memory where 
	 * subprogram is placed and to which will
	 * be PC be set to.
	 */
	private int value;

	/**
	 * This constructor creates instance of method call.
	 * @param arguments Arguments based on which appropriate
	 * method call is created.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if(arguments.size() != 1){
			throw new IllegalArgumentException("One argument expected.");
		}
		if(!arguments.get(0).isNumber()){
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		value = (int) arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		int location = (int) computer.getRegisters().getRegisterValue(15);
		computer.getMemory().setLocation(location, computer.getRegisters().getProgramCounter());
		computer.getRegisters().setProgramCounter(value);
		computer.getRegisters().setRegisterValue(15, location - 1);
		return false;
	}

}
