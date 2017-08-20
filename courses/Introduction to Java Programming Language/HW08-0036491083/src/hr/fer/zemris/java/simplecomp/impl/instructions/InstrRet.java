package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements CPU's  
 * return instruction. This instruction is used
 * for returning back from subprogram.
 * This class also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class InstrRet implements Instruction {

	/**
	 * This constructor creates instance of method return.
	 * @param arguments Arguments based on which appropriate
	 * method call is created.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if(!(arguments == null || arguments.isEmpty())){
			throw new IllegalArgumentException("One argument expected.");
		}
	}
	
	@Override
	public boolean execute(Computer computer) {
		int stackPointerRegValue = (int) computer.getRegisters().getRegisterValue(15);
		Integer newValue = (Integer) computer.getMemory().getLocation(stackPointerRegValue + 1);
		computer.getRegisters().setProgramCounter(newValue);
		computer.getRegisters().setRegisterValue(15, stackPointerRegValue + 1);
		return false;
	}

}
