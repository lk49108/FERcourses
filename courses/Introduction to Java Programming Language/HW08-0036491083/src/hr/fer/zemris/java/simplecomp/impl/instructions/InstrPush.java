package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This method implements CPU's method
 * push which is used to push some value to 
 * stack being placed in memory.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class InstrPush implements Instruction {

	/**
	 * This is index of register which value will be pushed to top of the stack.
	 */
	int value;
	
	/**
	 * This constructor creates instance of method push.
	 * @param arguments Arguments based on which method appropriate
	 * method push is created.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if(arguments.size() != 1){
			throw new IllegalArgumentException("One argument expected.");
		}
		if(!arguments.get(0).isRegister()){
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		if(RegisterUtil.isIndirect((Integer)arguments.get(0).getValue())){
			throw new IllegalArgumentException("Indirect addresing is not supported in"
					+ "method push.");
		}
		value = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
	}
	
	@Override
	public boolean execute(Computer computer) {
		int stackPointerRegValue = (int) computer.getRegisters().getRegisterValue(15);
		computer.getMemory().setLocation(stackPointerRegValue, 
				computer.getRegisters().getRegisterValue(value));
		computer.getRegisters().setRegisterValue(15, stackPointerRegValue - 1);
		return false;
	}

	

}
