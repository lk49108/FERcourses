package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * This class implements CPU's halt instruction.
 * It also implements {@link Instruction} interface.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class InstrHalt implements Instruction {

	/**
	 * This constructor creates instance of this class.
	 * @param arguments Arguments list which should be (in this case)
	 * null reference, or empty.
	 * @throws IllegalArgumentException if illegal parameters are provided.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(!(arguments == null || arguments.isEmpty())){
			throw new IllegalArgumentException("Argument halt should be given with no arguments.");		}
	}
	
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
